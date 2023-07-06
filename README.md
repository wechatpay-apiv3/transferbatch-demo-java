# 发起商家转账演示Demo
## 简介
[商家转账](https://pay.weixin.qq.com/docs/merchant/apis/batch-transfer-to-balance/transfer-batch/initiate-batch-transfer.html)是商户同时向多个用户微信零钱进行转账的操作，本项目是为便于商户和开发者快速理解业务、降低接入门槛而开发的演示Demo，运行后界面如下：
![demo.gif](src%2Fmain%2Fresources%2Fstatic%2Fimage%2Fdemo.gif)
### 通过该Demo你可以：<br>
1）直观的了解商家转账功能；<br>
2）商户的产品人员设计商家转账功能时可以参考演示Demo的界面设计；<br>
3）商户的研发人员实现商家转账功能时可以参考Demo的源码实现；<br>

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
复制.env.example文件为.env，按照注释说明补充信息。
### 3、运行项目
推荐使用IDE打开，以Intellij IDEA为例，导入项目后：<br>
1）点击右键菜单构建模块；<br>
2）右键点击src/main/java/transfer/BatchTransferApplication.java运行服务。
### 4、体验Demo
在浏览器输入：http://localhost:8080/create.html

## 特别说明
1）为了便于Demo快速部署，该项目选用了sqlite作为存储，在生产环境中开发者需要结合项目实际替换成合适的存储；<br>
2）在发起转账后，一定要确认该笔转账单**明确失败**才能重新发起转账，在转账单状态不明确的情况就换单号重新发起转账可能导致重复转账，造成资金损失；<br>
3）在实际业务中，当明细转账金额超过2000元时，发起批量转账时需传入加密后的用户姓名。用户姓名属于敏感信息，受到合规限制，商户应将其加密后存储在数据库中；<br>
4）为了实现简单，该Demo发起转账不支持重入，商户可以根据实际需求，支持原单号重试；<br>
5）本项目没有体现对错误码的处理，在实际项目中商户需要按照接口文档处理对应的错误码；<br>
6）该Demo是通过前端界面触发**通过商家批次单号查询批次单**操作，进而更新存储中的转账单状态，商户可以结合自己的实际情况，针对状态不明确的转账单通过定时脚本触发查询操作。