package transfer.dto;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;
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

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getApiV3Key() {
    return apiV3Key;
  }

  public void setApiV3Key(String apiV3Key) {
    this.apiV3Key = apiV3Key;
  }

  @Value("${mchid}")
  private String mchid;

  @Value("${appid}")
  private String appid;

  @Value("${private-key-path}")
  private String privateKeyPath;

  @Value("${serial-number}")
  private String serialNumber;

  @Value("${apiv3-key}")
  private String apiV3Key;

  private static volatile RSAAutoCertificateConfig config = null;

  public RSAAutoCertificateConfig getRSAConfig() {
    if (config == null) {
      // 同一个商户号构造多个实例，会抛出IllegalStateException异常
      synchronized (MerchantConfig.class) {
        if (config == null) {
          config =
              new RSAAutoCertificateConfig.Builder()
                  .merchantId(mchid)
                  .privateKeyFromPath(privateKeyPath)
                  .merchantSerialNumber(serialNumber)
                  .apiV3Key(apiV3Key)
                  .build();
        }
      }
    }
    return config;
  }
}
