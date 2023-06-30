package transfer.dto;

// 业务报错抛出该异常
public class BusinessException extends RuntimeException {

    private String code;
    private String errMsg;

    public BusinessException(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
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
}
