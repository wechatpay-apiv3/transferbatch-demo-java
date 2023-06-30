let batchData = {
  batchName: '',
  batchRemark: '',
  outBatchNo: '',
  totalNum: 0,
  totalAmount: 0,
};

const BatchStatus = {
  INIT: '系统转账校验中',
  WAIT_PAY: '待商户确认',
  PROCESSING: '转账中',
  SUCCESS: '转账成功',
  FAIL: '转账失败',
  WAITING: '等待处理',
}

const BatchStatusColor = {
  FAIL: 'red',
  WAITING: 'yellow',
}

const FailReason = {
  ACCOUNT_FROZEN: '该用户账户被冻结',
  REAL_NAME_CHECK_FAIL: '收款人未实名认证，需要用户完成微信实名认证',
  NAME_NOT_CORRECT: '收款人姓名校验不通过，请核实信息',
  OPENID_INVALID: 'Openid格式错误或者不属于商家公众账号',
  TRANSFER_QUOTA_EXCEED: '超过用户单笔收款额度，核实产品设置是否准确',
  DAY_RECEIVED_QUOTA_EXCEED: '超过用户单日收款额度，核实产品设置是否准确',
  MONTH_RECEIVED_QUOTA_EXCEED: '超过用户单月收款额度，核实产品设置是否准确',
  DAY_RECEIVED_COUNT_EXCEED: '超过用户单日收款次数，核实产品设置是否准确',
  PRODUCT_AUTH_CHECK_FAIL: '未开通该权限或权限被冻结，请核实产品权限状态',
  OVERDUE_CLOSE: '超过系统重试期，系统自动关闭',
  ID_CARD_NOT_CORRECT: '收款人身份证校验不通过，请核实信息',
  ACCOUNT_NOT_EXIST: '该用户账户不存在',
  TRANSFER_RISK: '该笔转账可能存在风险，已被微信拦截',
  OTHER_FAIL_REASON_TYPE: '其它失败原因',
  REALNAME_ACCOUNT_RECEIVED_QUOTA_EXCEED: '用户账户收款受限，请引导用户在微信支付查看详情',
  RECEIVE_ACCOUNT_NOT_PERMMIT: '未配置该用户为转账收款人，请在产品设置中调整，添加该用户为收款人',
  PAYEE_ACCOUNT_ABNORMAL: '用户账户收款异常，请联系用户完善其在微信支付的身份信息以继续收款',
  PAYER_ACCOUNT_ABNORMAL: '商户账户付款受限，可前往商户平台获取解除功能限制指引',
  TRANSFER_SCENE_UNAVAILABLE: '该转账场景暂不可用，请确认转账场景ID是否正确',
  TRANSFER_SCENE_INVALID: '你尚未获取该转账场景，请确认转账场景ID是否正确',
  TRANSFER_REMARK_SET_FAIL: '转账备注设置失败，请调整后重新再试',
  RECEIVE_ACCOUNT_NOT_CONFIGURE: '请前往商户平台-商家转账到零钱-前往功能-转账场景中添加',
  BLOCK_B2C_USERLIMITAMOUNT_BSRULE_MONTH: '超出用户单月转账收款20w限额，本月不支持继续向该用户付款',
  BLOCK_B2C_USERLIMITAMOUNT_MONTH: '用户账户存在风险收款受限，本月不支持继续向该用户付款',
  MERCHANT_REJECT: '商户员工（转账验密人）已驳回转账',
  MERCHANT_NOT_CONFIRM: ' 商户员工（转账验密人）超时未验密',
}

init();

function init() {
  initData();
}

function initData() {
  batchData.outBatchNo = getQueryVariable('outBatchNo');
  getTransferBatchData();
}

async function getTransferBatchData() {
  // 发送HTTP请求，查询批次单
  try {
    const response = await fetch(
        `/query-batch?outBatchNo=${batchData.outBatchNo}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

    if (!response.ok) {
      alert('查询批次详情失败');
      return;
    }

    console.log('getTransferBatchData', response);
    const resp = await response.json()
    batchData = resp.data
    if (batchData.batchStatus === 'FINISHED') { // 状态为finish再调用明细查询状态
      await Promise.all(batchData.transferDetailOrders.map(
          async (item, index) => {
            await getTransferDetailData(item.outDetailNo, index);
          })
      );
    } else {// 若批次还没有返回，明细状态为等待处理
      batchData.transferDetailOrders.forEach(item => {
        item.detailStatus = 'WAITING';
      });
    }
    renderPage();
  } catch (error) {
    console.log('查询批次详情失败', error);
    alert(`查询批次详情失败${error.message}`);
  }
}

async function getTransferDetailData(outDetailNo, index) {
  try {
    // 发送HTTP请求，查询批次单
    const response = await fetch(
        `/query-detail?outBatchNo=${batchData.outBatchNo}&outDetailNo=${outDetailNo}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

    if (!response.ok) {
      alert('查询订单情失败');
      return;
    }

    console.log('getTransferDetailData', index, response);
    const resp = await response.json()
    batchData.transferDetailOrders[index] = resp.data;
  } catch (error) {
    console.log('查询订单详情失败', error);
    alert(`查询订单详情失败${error.message}`);
  }
}

function renderPage() {
  document.getElementById('outBatchNo').innerText = batchData.outBatchNo;
  document.getElementById('batchName').innerText = batchData.batchName;
  document.getElementById('batchRemark').innerText = batchData.batchRemark;
  document.getElementById('totalAmount').innerText = batchData.totalAmount;
  document.getElementById('totalNum').innerText = batchData.totalNum;
  batchData.transferDetailOrders.forEach((transferDetailOrder, index) => {
    renderTransferDetail(transferDetailOrder, index)
  });
  document.getElementById('loadingBox').classList.add('hide');
  document.getElementById('dataBox').classList.remove('hide');
}

function renderTransferDetail(transferDetail, index) {
  const transferDetailListEl = document.getElementById('transferDetailList');
  const detailElTemplate = document.getElementById('transferDetailTemplate');
  const detailEl = detailElTemplate.cloneNode(true);
  detailEl.id = 'transferDetail';
  detailEl.style.display = 'block';
  detailEl.querySelector(
      '#transferDetailIndex').innerHTML = `转账明细列表${(index + 1)}`;
  detailEl.querySelector('#outDetailNo').innerText = transferDetail.outDetailNo;
  detailEl.querySelector(
      '#transferAmount').innerText = transferDetail.transferAmount;
  detailEl.querySelector(
      '#transferRemark').innerText = transferDetail.transferRemark;
  detailEl.querySelector('#openid').innerText = transferDetail.openid;
  detailEl.querySelector('#userName').innerText = transferDetail.userName || '';
  if (!transferDetail.userName || transferDetail.userName.length === 0) {
    detailEl.querySelector('#userNameItem').classList.add('hide');
  }
  detailEl.querySelector(
      '#detailStatus').innerText = BatchStatus[transferDetail.detailStatus]
      || '';
  detailEl.querySelector('#detailStatus').classList.add(
      BatchStatusColor[transferDetail.detailStatus] || 'black');
  detailEl.querySelector(
      '#failReason').innerText = FailReason[transferDetail.failReason] || '';
  if (!transferDetail.failReason || transferDetail.failReason.length === 0) {
    detailEl.querySelector('#failReasonItem').classList.add('hide');
  }
  transferDetailListEl.append(detailEl)
}

function getQueryVariable(variable) {
  const query = window.location.search.substring(1);
  const vars = query.split('&');
  for (let i = 0; i < vars.length; i++) {
    const pair = vars[i].split('=');
    if (pair[0] == variable) {
      return pair[1];
    }
  }
  return null;
}
