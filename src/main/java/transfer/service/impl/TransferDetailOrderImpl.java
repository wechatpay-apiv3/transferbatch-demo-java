package transfer.service.impl;

import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.WechatPayException;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.GetTransferDetailByOutNoRequest;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transfer.dto.BusinessException;
import transfer.dto.TransferDetailOrderEntity;
import transfer.infrastructure.Comm;
import transfer.infrastructure.MerchantConfig;
import transfer.mapper.TransferDetailOrderMapper;
import transfer.service.TransferDetailOrder;

@Service
public class TransferDetailOrderImpl implements TransferDetailOrder {

  @Autowired TransferDetailOrderMapper transferDetailOrderMapper;

  /**
   * 查询明细单
   *
   * @param outBatchNo 商户转账批次单
   * @param outDetailNo 商户转账明细单
   * @return
   */
  @Override
  public TransferDetailOrderEntity queryTransferDetailOrder(String outBatchNo, String outDetailNo) {
    // 1、调用明细单仓储查询明细单状态
    TransferDetailOrderEntity transferDetailOrderEntity =
        transferDetailOrderMapper.query(MerchantConfig.MCHID, outBatchNo, outDetailNo);
    if (transferDetailOrderEntity.getDetailStatus().equals(Comm.SUCCESS)
        || transferDetailOrderEntity.getDetailStatus().equals(Comm.FAIL)) {
      return transferDetailOrderEntity;
    }
    // 2、如果状态不是终态或者明细单失败原因缺失，则调用微信支付查询明细单
    refreshTransferDetailOrder(transferDetailOrderEntity);
    return transferDetailOrderEntity;
  }

  /**
   * 刷新明细单信息
   *
   * @param transferDetailOrderEntity 明细单实体信息
   */
  private void refreshTransferDetailOrder(TransferDetailOrderEntity transferDetailOrderEntity) {
    TransferBatchService service =
        new TransferBatchService.Builder().config(MerchantConfig.getRSAConfig()).build();
    GetTransferDetailByOutNoRequest request = new GetTransferDetailByOutNoRequest();
    request.setOutBatchNo(transferDetailOrderEntity.getOutBatchNo());
    request.setOutDetailNo(transferDetailOrderEntity.getOutDetailNo());
    TransferDetailEntity response;
    try {
      response = service.getTransferDetailByOutNo(request);
    } catch (ServiceException e) {
      // http请求成功，但是接口失败，这里需要根据实际需求处理错误码
      throw new BusinessException(e.getErrorCode(), e.getErrorMessage());
    } catch (WechatPayException e) {
      // ...上报监控和打印日志
      throw new BusinessException(Comm.SYSTEM_ERROR, e.getMessage());
    }
    // 如果状态为终态则更新到DB
    if (response.getDetailStatus().equals(Comm.SUCCESS)
        || response.getDetailStatus().equals(Comm.FAIL)) {
      transferDetailOrderEntity.setDetailStatus(response.getDetailStatus());
      if (response.getDetailStatus().equals(Comm.FAIL)) { // 转账失败时把原因记录下
        transferDetailOrderEntity.setFailReason(response.getFailReason().toString());
      }
      transferDetailOrderMapper.update(transferDetailOrderEntity);
    }
  }
}
