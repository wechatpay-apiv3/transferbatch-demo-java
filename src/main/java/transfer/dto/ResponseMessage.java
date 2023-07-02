package transfer.dto;

// 接口返回结果
public class ResponseMessage {

  // 错误码
  private String code;
  // 错误信息
  private String errMsg;
  // 不同接口返回不同的Response
  private Object data;

  public ResponseMessage(String code, String errMsg) {
    this.code = code;
    this.errMsg = errMsg;
  }

  public ResponseMessage(String code, String errMsg, Object data) {
    this.code = code;
    this.errMsg = errMsg;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
