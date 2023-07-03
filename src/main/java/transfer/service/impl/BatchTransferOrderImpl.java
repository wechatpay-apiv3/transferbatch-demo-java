package transfer.service.impl;

import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.WechatPayException;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.GetTransferBatchByOutNoRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.TransferBatchEntity;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailInput;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transfer.dto.BatchTransferOrderEntity;
import transfer.dto.BusinessException;
import transfer.dto.TransferDetailOrderEntity;
import transfer.infrastructure.Comm;
import transfer.infrastructure.MerchantConfig;
import transfer.mapper.BatchTransferOrderMapper;
import transfer.mapper.TransferDetailOrderMapper;
import transfer.service.BatchTransferOrder;

@Service
public class BatchTransferOrderImpl implements BatchTransferOrder {
  @Autowired BatchTransferOrderMapper batchTransferOrderMapper;
  @Autowired TransferDetailOrderMapper transferDetailOrderMapper;

  /**
   * 创建批次转账单
   *
   * @param batchTransferOrderEntity 批次转账单实体属性
   */
  @Override
  public void createBatchTransferOrder(BatchTransferOrderEntity batchTransferOrderEntity) {
    // 1、调用批次单仓储保存批次单
    try {
      batchTransferOrderMapper.create(batchTransferOrderEntity);
    } catch (Exception e) {
      // 注意，这里不支持重入，发生错误需要换单重试，如果要支持重入，判断主键冲突后需要对参数进行判断，保证重入参数前后一致
      throw new BusinessException("CREATE_BATCH_ERR", "请更换批次单号重新发起批次转账");
    }
    // 2、调用明细单仓储保存明细单
    try {
      for (int i = 0; i < batchTransferOrderEntity.getTransferDetailOrders().size(); i++) {
        transferDetailOrderMapper.create(batchTransferOrderEntity.getTransferDetailOrders().get(i));
      }
    } catch (Exception e) {
      // 注意，这里不支持重入，发生错误需要换单重试，如果要支持重入，判断主键冲突后需要对参数进行判断，保证重入参数前后一致
      throw new BusinessException("CREATE_DETAIL_ERR", "请更换单号重新发起批次转账");
    }

    // 3、调用微信支付批次转账接口
    processBatchTransfer(batchTransferOrderEntity);
  }

  /**
   * 查询批次转账单
   *
   * @param outBatchNo 商户批次单号
   * @return
   */
  @Override
  public BatchTransferOrderEntity queryBatchTransferOrder(String outBatchNo) {
    // 1、从DB中查询批次单
    BatchTransferOrderEntity batchTransferOrder =
        batchTransferOrderMapper.query(MerchantConfig.MCHID, outBatchNo);
    // 2、DB中批次单如果不是终态，则调用微信支付查询批次单最新状态并更新DB
    if (!batchTransferOrder.getBatchStatus().equals("FINISHED")
        || !batchTransferOrder.getBatchStatus().equals("CLOSED")) {
      refreshTransferBatchOrderStatus(batchTransferOrder);
    }
    // 3、调用明细单仓储查询明细单
    ArrayList<TransferDetailOrderEntity> detailOrders =
        transferDetailOrderMapper.find(MerchantConfig.MCHID, outBatchNo);
    batchTransferOrder.setTransferDetailOrders(detailOrders);
    return batchTransferOrder;
  }

  /**
   * 发起批次转账
   *
   * @param batchTransferOrderEntity 批次转账实体信息
   */
  private void processBatchTransfer(BatchTransferOrderEntity batchTransferOrderEntity) {
    TransferBatchService service =
        new TransferBatchService.Builder().config(MerchantConfig.getRSAConfig()).build();
    InitiateBatchTransferRequest request = new InitiateBatchTransferRequest();
    // 构造请求微信支付发起转账的参数
    request.setBatchName(batchTransferOrderEntity.getBatchName());
    request.setAppid(batchTransferOrderEntity.getAppid());
    request.setOutBatchNo(batchTransferOrderEntity.getOutBatchNo());
    request.setBatchRemark(batchTransferOrderEntity.getBatchRemark());
    request.setTotalAmount(batchTransferOrderEntity.getTotalAmount());
    request.setTotalNum(batchTransferOrderEntity.getTransferDetailOrders().size());
    List<TransferDetailInput> transferDetailInputs = new ArrayList<>();
    for (int i = 0; i < batchTransferOrderEntity.getTransferDetailOrders().size(); i++) {
      TransferDetailInput transferDetailInput = new TransferDetailInput();
      transferDetailInput.setOpenid(
          batchTransferOrderEntity.getTransferDetailOrders().get(i).getOpenid());
      transferDetailInput.setOutDetailNo(
          batchTransferOrderEntity.getTransferDetailOrders().get(i).getOutDetailNo());
      transferDetailInput.setTransferAmount(
          batchTransferOrderEntity.getTransferDetailOrders().get(i).getTransferAmount());
      transferDetailInput.setTransferRemark(
          batchTransferOrderEntity.getTransferDetailOrders().get(i).getTransferRemark());
      transferDetailInputs.add(transferDetailInput);
    }
    request.setTransferDetailList(transferDetailInputs);
    try {
      service.initiateBatchTransfer(request);
    } catch (ServiceException e) {
      // http请求成功，但是接口失败，这里需要根据实际需求处理错误码
      throw new BusinessException(e.getErrorCode(), e.getErrorMessage());
    } catch (WechatPayException e) {
      // ...上报监控和打印日志
      throw new BusinessException(Comm.SYSTEM_ERROR, e.getMessage());
    }
  }

  /**
   * 刷新批次单状态
   *
   * @param batchTransferOrder 批次单信息
   */
  private void refreshTransferBatchOrderStatus(BatchTransferOrderEntity batchTransferOrder) {
    TransferBatchService service =
        new TransferBatchService.Builder().config(MerchantConfig.getRSAConfig()).build();
    GetTransferBatchByOutNoRequest request = new GetTransferBatchByOutNoRequest();
    request.setOutBatchNo(batchTransferOrder.getOutBatchNo());
    request.setNeedQueryDetail(Boolean.FALSE);
    TransferBatchEntity response;
    try {
      response = service.getTransferBatchByOutNo(request);
    } catch (ServiceException e) {
      // http请求成功，但是接口失败，这里需要根据实际需求处理错误码
      throw new BusinessException(e.getErrorCode(), e.getErrorMessage());
    } catch (WechatPayException e) {
      // ...上报监控和打印日志
      throw new BusinessException(Comm.SYSTEM_ERROR, e.getMessage());
    }
    // 如果状态有更新则更新到DB
    if (!response.getTransferBatch().getBatchStatus().equals(batchTransferOrder.getBatchStatus())) {
      batchTransferOrder.setBatchStatus(response.getTransferBatch().getBatchStatus());
    }
    batchTransferOrderMapper.update(batchTransferOrder);
  }
}
