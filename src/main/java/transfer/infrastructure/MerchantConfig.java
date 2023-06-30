package transfer.infrastructure;

import com.wechat.pay.java.core.RSAConfig;

public class MerchantConfig {

    // 待补充：发起批量转账的商户号
    private static final String merchantId = "";
    // 待补充：发起批量转账的appid
    private static final String appid = "";
    // 待补充：发起批量转账的私钥
    private static final String privateKey = "";
    // 待补充：对应的密钥序列号
    private static final String merchantSerialNumber = "";
    // 待补充：微信支付证书
    private static final String wechatPayCertificate = "";
    private static final RSAConfig config = new RSAConfig.Builder()
            .merchantId(merchantId)
            // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
            .privateKey(privateKey)
            .merchantSerialNumber(merchantSerialNumber)
            .wechatPayCertificates(wechatPayCertificate)
            .build();

    public static RSAConfig getRSAConfig() {
        return config;
    }

    public static String getMerchantId() {
        return merchantId;
    }

    public static String getAppid() {
        return appid;
    }


}
