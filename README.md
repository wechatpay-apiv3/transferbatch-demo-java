# 发起商家转账演示Demo
## 简介
[商家转账](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-batch/initiate-batch-transfer.html)：商户可以通过该功能同时向多个用户微信零钱进行转账操作。<br>
本项目为商家转账演示Demo，便于商户和开发者快速理解业务，降低接入门槛。
## 运行环境
- java 17.0.5 2022-10-18 LTS
- gradle-8.0
## 使用入门
### 1、下载代码
`git clone https://github.com/wechatpay-apiv3/transferbatch-demo-java.git`
### 2、补充信息
打开src/main/java/transfer/infrastructure/MerchantConfig.java文件，按照注释说明补充MCHID、APPID、PRIVATE_KEY、SERIAL_NUMBER、CERTIFICATES的值。
### 3、运行项目
推荐使用IDE打开，以Intellij IDEA为例，导入项目后：
1）点击右键菜单构建模块；
2）右键点击src/main/java/transfer/BatchTransferApplication.java，运行服务。
### 4、体验Demo
在浏览器输入：http://localhost:8080/create.html
