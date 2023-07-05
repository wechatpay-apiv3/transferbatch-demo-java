package transfer.dto;

// 创建批次单入餐
public class TransferDetailInput {

  private String outDetailNo;
  private Long transferAmount;
  private String transferRemark;
  private String openid;

  public String getOutDetailNo() {
    return outDetailNo;
  }

  public void setOutDetailNo(String outDetailNo) {
    this.outDetailNo = outDetailNo;
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

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }
}
