package transfer.dto;

import java.util.List;

// 创建批次单入参
public class CreateBatchRequest {

  private String outBatchNo;
  private String batchName;
  private String batchRemark;
  private Long totalAmount;
  private Integer totalNum;
  private List<TransferDetailInput> transferDetailOrders;
  private String transferSceneId;

  public String getOutBatchNo() {
    return outBatchNo;
  }

  public void setOutBatchNo(String outBatchNo) {
    this.outBatchNo = outBatchNo;
  }

  public String getBatchName() {
    return batchName;
  }

  public void setBatchName(String batchName) {
    this.batchName = batchName;
  }

  public String getBatchRemark() {
    return batchRemark;
  }

  public void setBatchRemark(String batchRemark) {
    this.batchRemark = batchRemark;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Integer getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(Integer totalNum) {
    this.totalNum = totalNum;
  }

  public List<TransferDetailInput> getTransferDetailList() {
    return transferDetailOrders;
  }

  public void setTransferDetailList(List<TransferDetailInput> transferDetailList) {
    this.transferDetailOrders = transferDetailList;
  }

  public String getTransferSceneId() {
    return transferSceneId;
  }

  public void setTransferSceneId(String transferSceneId) {
    this.transferSceneId = transferSceneId;
  }
}
