package transfer.dto;

import java.util.ArrayList;
import java.util.List;

// 批次单信息
public class BatchTransferOrderEntity {

  private Integer id;
  private String mchid;
  private String outBatchNo;
  private String batchId;
  private String batchStatus;
  private String appid;
  private String batchName;
  private String batchRemark;
  private String closeReason;
  private Long totalAmount;
  private Integer totalNum;
  private String transferSceneId;
  private ArrayList<TransferDetailOrderEntity> transferDetailOrders;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMchid() {
    return mchid;
  }

  public void setMchid(String mchid) {
    this.mchid = mchid;
  }

  public String getOutBatchNo() {
    return outBatchNo;
  }

  public void setOutBatchNo(String outBatchNo) {
    this.outBatchNo = outBatchNo;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getBatchStatus() {
    return batchStatus;
  }

  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
  }

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
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

  public String getTransferSceneId() {
    return transferSceneId;
  }

  public void setTransferSceneId(String transferSceneId) {
    this.transferSceneId = transferSceneId;
  }

  public List<TransferDetailOrderEntity> getTransferDetailOrders() {
    return transferDetailOrders;
  }

  public void setTransferDetailOrders(List<TransferDetailOrderEntity> transferDetailOrders) {
    this.transferDetailOrders = new ArrayList<>(transferDetailOrders);
  }
}
