package transfer.dto;

// 创建批次单返回参数
public class CreateBatchResponse {

  private String outBatchNo; // 商家批次单号
  private String batchId; // 微信批次单号
  private String createTime; // 创建时间

  // Getter 和 Setter 方法
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
