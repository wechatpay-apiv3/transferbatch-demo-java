package transfer.infrastructure;

import com.wechat.pay.java.core.RSAConfig;

public class MerchantConfig {

  // 待补充：发起批量转账的商户号
  public static final String MCHID = "";
  // 待补充：发起批量转账的appid
  public static final String APPID = "";
  // 待补充：发起批量转账的私钥
  public static final String PRIVATE_KEY = "";
  // 待补充：商户公钥序列号
  private static final String SERIAL_NUMBER = "";
  // 待补充：微信支付证书
  private static final String CERTIFICATES = "";
  private static final RSAConfig config =
      new RSAConfig.Builder()
          .merchantId(MCHID)
          .privateKey(PRIVATE_KEY)
          .merchantSerialNumber(SERIAL_NUMBER)
          .wechatPayCertificates(CERTIFICATES)
          .build();

  public static RSAConfig getRSAConfig() {
    return config;
  }

  private MerchantConfig() {}
}
