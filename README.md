# 商家转账到零钱 Demo
## 简介
[商家转账到零钱](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-batch/initiate-batch-transfer.html)为商户提供向一个或者同时向多个用户微信零钱转账的能力，本项目是为便于商户和开发者快速理解业务、降低接入门槛而开发的演示Demo，运行后界面如下：
![demo.gif](src%2Fmain%2Fresources%2Fstatic%2Fimage%2Fdemo.gif)
### 通过该Demo你可以：<br>
1）直观地了解商家转账功能；<br>
2）参考演示Demo的界面设计，帮助商户的产品人员设计商家转账功能；<br>
3）参考Demo的源码实现，帮助商户的研发人员实现商家转账功能；<br>

### 该Demo覆盖的APIv3接口：<br>
1）[发起商家转账](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-batch/initiate-batch-transfer.html)；<br>
2）[通过商家批次单号查询批次单](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-batch/get-transfer-batch-by-out-no.html)；<br>
3）[通过商家明细单号查询明细单](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-detail/get-transfer-detail-by-out-no.html)；<br>

## 运行环境
- java 17.0.5 2022-10-18 LTS
- gradle-8.0
## 使用入门
### 1、下载代码
`git clone https://github.com/wujunjiesd/transferbatch-demo-java.git`
### 2、补充信息
复制.env.example文件为[.env](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.external-config)，按照注释说明补充信息。
### 3、运行项目
`gradle bootRun`
### 4、体验Demo
在浏览器输入：http://localhost:8080/create.html

## 常见问题
### Demo是否可以直接应用于生产环境？
不可以。例如为了便于快速部署而选用了SQLite作为存储、缺少鉴权相关逻辑。开发者可以参考Demo实现，并根据项目实际情况进行修改。
### 发起商家转账返回失败或超时，我应该如何处理？
应该查单确认转账单状态。返回失败或超时并不表示转账失败，只有查单后确认该笔转账单**处于终态且明确失败**的情况下才可以更换单号重新发起转账，在转账单状态不明确的情况下可以原单号重试。未经过查单确认就更换单号重新转账可能导致资金损失。
### 什么情况需要传入收款人姓名？
当明细转账金额超过2000元时，发起批量转账时需传入加密后的用户姓名。用户姓名属于敏感信息，受到合规限制，商户应将其**加密**后存储在数据库中。
### 商户私钥等敏感信息怎么托管？
为了便于Demo快速部署该项目直接将敏感信息配置在.env文件中，但我们推荐敏感信息**加密**后配置在.env文件中，在代码中解密使用，密文和解密的密钥不要放在一起，注意不要将.env文件提交到代码仓库。
### 发起转账后，在什么时机查询转账单状态？
该Demo是通过前端界面触发**通过商家批次单号查询批次单**操作从而更新存储中的转账单状态，商户可以结合自己的实际情况，针对状态不明确的转账单通过**定时任务**触发查询操作。