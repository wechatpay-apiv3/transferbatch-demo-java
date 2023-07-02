package transfer.dto;

import java.util.ArrayList;
import java.util.List;

// 查询批次单返回参数
public class QueryBatchResponse {

  private String outBatchNo;
  private String batchStatus;
  private String batchName;
  private String batchRemark;
  private String closeReason;
  private Long totalAmount;
  private Integer totalNum;
  private ArrayList<QueryDetailResponse> transferDetailOrders;

  public String getOutBatchNo() {
    return outBatchNo;
  }

  public void setOutBatchNo(String outBatchNo) {
    this.outBatchNo = outBatchNo;
  }

  public String getBatchStatus() {
    return batchStatus;
  }

  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
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

  public String getCloseReason() {
    return closeReason;
  }

  public void setCloseReason(String closeReason) {
    this.closeReason = closeReason;
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

  public List<QueryDetailResponse> getTransferDetailOrders() {
    return transferDetailOrders;
  }

  public void setTransferDetailOrders(List<QueryDetailResponse> transferDetailOrders) {
    this.transferDetailOrders = new ArrayList<>(transferDetailOrders);
  }
}
