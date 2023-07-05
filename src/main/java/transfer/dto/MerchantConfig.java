package transfer.dto;

import com.wechat.pay.java.core.RSAConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// 商户号配置信息
public class MerchantConfig {

  public String getMchid() {
    return mchid;
  }

  public void setMchid(String mchid) {
    this.mchid = mchid;
  }

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getWechatPayCertificates() {
    return wechatPayCertificates;
  }

  public void setWechatPayCertificates(String wechatPayCertificates) {
    this.wechatPayCertificates = wechatPayCertificates;
  }

  @Value("${mchid}")
  private String mchid;

  @Value("${appid}")
  private String appid;

  @Value("${private-key}")
  private String privateKey;

  @Value("${serial-number}")
  private String serialNumber;

  @Value("${wechat-pay-certificates}")
  private String wechatPayCertificates;

  public RSAConfig getRSAConfig() {
    return new RSAConfig.Builder()
        .merchantId(mchid)
        .privateKey(privateKey)
        .merchantSerialNumber(serialNumber)
        .wechatPayCertificates(wechatPayCertificates)
        .build();
  }
}
