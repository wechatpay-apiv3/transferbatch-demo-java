package transfer.dto;

// 业务报错抛出该异常
public class BusinessException extends RuntimeException {

  private final String code;
  private final String errMsg;

  public BusinessException(String code, String errMsg) {
    this.code = code;
    this.errMsg = errMsg;
  }

  public String getCode() {
    return code;
  }

  public String getErrMsg() {
    return errMsg;
  }
}
