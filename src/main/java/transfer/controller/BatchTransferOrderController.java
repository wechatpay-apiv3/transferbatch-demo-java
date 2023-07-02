package transfer.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import transfer.dto.BatchTransferOrderEntity;
import transfer.dto.BusinessException;
import transfer.dto.CreateBatchRequest;
import transfer.dto.QueryBatchResponse;
import transfer.dto.QueryDetailResponse;
import transfer.dto.ResponseMessage;
import transfer.dto.TransferDetailInput;
import transfer.dto.TransferDetailOrderEntity;
import transfer.infrastructure.Comm;
import transfer.infrastructure.MerchantConfig;
import transfer.service.BatchTransferOrder;
import transfer.service.TransferDetailOrder;

@RestController
@ComponentScan({"transfer.service"})
public class BatchTransferOrderController {

  @Autowired private BatchTransferOrder batchTransferOrder;
  @Autowired private TransferDetailOrder transferDetailOrder;

  /**
   * 发起批次转账
   *
   * @param batchInfo 批次转账信息
   * @return
   */
  @PostMapping("/create-batch")
  public ResponseMessage createBatch(@RequestBody CreateBatchRequest batchInfo) {
    // 1、构造请求参数
    BatchTransferOrderEntity batchTransferOrderEntity = new BatchTransferOrderEntity();
    batchTransferOrderEntity.setMchid(MerchantConfig.MCHID);
    batchTransferOrderEntity.setOutBatchNo(batchInfo.getOutBatchNo());
    batchTransferOrderEntity.setAppid(MerchantConfig.APPID);
    batchTransferOrderEntity.setBatchName(batchInfo.getBatchName());
    batchTransferOrderEntity.setBatchRemark(batchInfo.getBatchRemark());
    batchTransferOrderEntity.setTransferSceneId(batchInfo.getTransferSceneId());
    batchTransferOrderEntity.setTotalAmount(batchInfo.getTotalAmount());
    batchTransferOrderEntity.setTotalNum(batchInfo.getTotalNum());
    ArrayList<TransferDetailOrderEntity> transferDetailOrderEntities = new ArrayList<>();
    for (TransferDetailInput item : batchInfo.getTransferDetailList()) {
      TransferDetailOrderEntity detail = new TransferDetailOrderEntity();
      detail.setMchid(MerchantConfig.MCHID);
      detail.setOutBatchNo(batchInfo.getOutBatchNo());
      detail.setAppid(MerchantConfig.APPID);
      detail.setOutDetailNo(item.getOutDetailNo());
      detail.setTransferAmount(item.getTransferAmount());
      detail.setTransferRemark(item.getTransferRemark());
      detail.setOpenid(item.getOpenid());
      detail.setUserName(item.getUserName());
      transferDetailOrderEntities.add(detail);
    }
    batchTransferOrderEntity.setTransferDetailOrders(transferDetailOrderEntities);
    // 2、创建批次转账
    try {
      batchTransferOrder.createBatchTransferOrder(batchTransferOrderEntity);
    } catch (BusinessException e) {
      return new ResponseMessage(e.getCode(), e.getErrMsg());
    }
    return new ResponseMessage(Comm.SUCCESS, "发起转账成功");
  }

  /**
   * 根据商户批次单号查询批量转账单
   *
   * @param outBatchNo 商户批次单号
   * @return
   */
  @GetMapping("/query-batch")
  public ResponseMessage queryBatch(@RequestParam String outBatchNo) {
    // 1、查询批次单详细信息
    BatchTransferOrderEntity batchTransferOrderEntity;
    try {
      batchTransferOrderEntity = batchTransferOrder.queryBatchTransferOrder(outBatchNo);
    } catch (BusinessException e) {
      return new ResponseMessage(e.getCode(), e.getErrMsg());
    }
    // 2、构造返回参数
    QueryBatchResponse resp = new QueryBatchResponse();
    resp.setBatchStatus(batchTransferOrderEntity.getBatchStatus());
    resp.setBatchName(batchTransferOrderEntity.getBatchName());
    resp.setBatchRemark(batchTransferOrderEntity.getBatchRemark());
    resp.setOutBatchNo(batchTransferOrderEntity.getOutBatchNo());
    resp.setCloseReason(batchTransferOrderEntity.getCloseReason());
    resp.setTotalAmount(batchTransferOrderEntity.getTotalAmount());
    resp.setTotalNum(batchTransferOrderEntity.getTotalNum());
    ArrayList<QueryDetailResponse> detailResponses = new ArrayList<>();
    for (TransferDetailOrderEntity item : batchTransferOrderEntity.getTransferDetailOrders()) {
      QueryDetailResponse detailResponse = new QueryDetailResponse();
      detailResponse.setDetailStatus(item.getDetailStatus());
      detailResponse.setOpenid(item.getOpenid());
      detailResponse.setOutDetailNo(item.getOutDetailNo());
      detailResponse.setTransferRemark(item.getTransferRemark());
      detailResponse.setFailReason(item.getFailReason());
      detailResponse.setTransferAmount(item.getTransferAmount());
      detailResponse.setUserName(item.getUserName());
      detailResponses.add(detailResponse);
    }
    resp.setTransferDetailOrders(detailResponses);
    return new ResponseMessage(Comm.SUCCESS, "", resp);
  }

  /**
   * 查询转账明细单
   *
   * @param outBatchNo 商户批次转账单号
   * @param outDetailNo 商户批次转账明细单号
   * @return
   */
  @GetMapping("/query-detail")
  public ResponseMessage queryDetail(
      @RequestParam String outBatchNo, @RequestParam String outDetailNo) {
    // 1、查询转账明细单
    TransferDetailOrderEntity transferDetailOrderEntity;
    try {
      transferDetailOrderEntity =
          transferDetailOrder.queryTransferDetailOrder(outBatchNo, outDetailNo);
    } catch (BusinessException e) {
      return new ResponseMessage(e.getCode(), e.getErrMsg());
    }

    // 2、构造返回参数
    QueryDetailResponse response = new QueryDetailResponse();
    response.setTransferAmount(transferDetailOrderEntity.getTransferAmount());
    response.setTransferRemark(transferDetailOrderEntity.getTransferRemark());
    response.setOutDetailNo(transferDetailOrderEntity.getOutDetailNo());
    response.setDetailStatus(transferDetailOrderEntity.getDetailStatus());
    response.setOpenid(transferDetailOrderEntity.getOpenid());
    response.setFailReason(transferDetailOrderEntity.getFailReason());
    response.setUserName(transferDetailOrderEntity.getUserName());
    return new ResponseMessage(Comm.SUCCESS, "", response);
  }
}
