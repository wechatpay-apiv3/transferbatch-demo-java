package transfer.dto;

// 明细单实体信息
public class TransferDetailOrderEntity {

  private Integer id;
  private String mchid;
  private String outBatchNo;
  private String appid;
  private String outDetailNo;
  private String detailId;
  private String detailStatus;
  private Long transferAmount;
  private String transferRemark;
  private String failReason;
  private String openid;
  private String userName;

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

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getOutDetailNo() {
    return outDetailNo;
  }

  public void setOutDetailNo(String outDetailNo) {
    this.outDetailNo = outDetailNo;
  }

  public String getDetailId() {
    return detailId;
  }

  public void setDetailId(String detailId) {
    this.detailId = detailId;
  }

  public String getDetailStatus() {
    return detailStatus;
  }

  public void setDetailStatus(String detailStatus) {
    this.detailStatus = detailStatus;
  }

  public Long getTransferAmount() {
    return transferAmount;
  }

  public void setTransferAmount(Long transferAmount) {
    this.transferAmount = transferAmount;
  }

  public String getTransferRemark() {
    return transferRemark;
  }

  public void setTransferRemark(String transferRemark) {
    this.transferRemark = transferRemark;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
