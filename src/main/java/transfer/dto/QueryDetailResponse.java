package transfer.dto;

// 查询明细单返回参数
public class QueryDetailResponse {

  private String outDetailNo;
  private String detailStatus;
  private Long transferAmount;
  private String transferRemark;
  private String failReason;
  private String openid;
  private String userName;

  public String getOutDetailNo() {
    return outDetailNo;
  }

  public void setOutDetailNo(String outDetailNo) {
    this.outDetailNo = outDetailNo;
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
