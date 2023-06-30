const batchData = {
  batchName: '补贴发放',
  batchRemark: '2023第一季度补贴发放',
  transferSceneId: 101, // 行政补贴
  outBatchNo: '',
  totalNum: 0,
  totalAmount: 0,
};

const TransferScene = {
  101: '行政补贴'
}

const submitBtn = document.getElementById('submitBtn');
const addBtn = document.getElementById('addBtn');
const resultDialogBtn = document.getElementById('resultDialogBtn');
const loadingDialog = document.getElementById('loadingDialog');

init();

function init() {
  initData();
  renderPage();
  bindEvent();
}

function initData() {
  batchData.outBatchNo = generateOutBatchNo();
  addTransferDetail();
}

function renderPage() {
  document.getElementById('outBatchNo').innerText = batchData.outBatchNo;
  document.getElementById('batchName').innerText = batchData.batchName;
  document.getElementById('batchRemark').innerText = batchData.batchRemark;
  document.getElementById(
      'transferSceneId').innerText = TransferScene[batchData.transferSceneId];
}

function bindEvent() {
  addBtn.addEventListener('click', addTransferDetail);
  submitBtn.addEventListener('click', submitTransfer);
  resultDialogBtn.addEventListener('click', gotoTransferResult);
}

function generateOutBatchNo() {  // 简单实现随机生成批次单号
  return `sdkdemobatchno${Date.now()}`;
}

function generateOutDetailNo() {  // 简单实现随机生成明细单号
  return `sdkdemooutdetailno${Date.now()}`;
}

function randomInRange(from, to) {
  var r = Math.random();
  return Math.floor(r * (to - from) + from);
}

function addTransferDetail() {
  const transferDetail = {
    outDetailNo: generateOutDetailNo(),
    transferAmount: '',
    transferRemark: '',
    openid: '',
    userName: ''
  }
  if (batchData.totalNum >= 1000) {
    alert('一个转账批次单最多发起一千笔转账');
    return;
  }
  renderTransferDetail(transferDetail, batchData.totalNum);
  batchData.totalNum++;
  renderDeleteBtn();
  checkInput();
}

function renderTransferDetail(transferDetail, index) {
  const transferDetailListEl = document.getElementById('transferDetailList');
  const detailElTemplate = document.getElementById('transferDetailTemplate');
  const detailEl = detailElTemplate.cloneNode(true);
  detailEl.id = 'transferDetail';
  detailEl.style.display = 'block';
  detailEl.querySelector('#transferDetailIndex').innerHTML = '转账明细列表'
      + (index + 1);
  detailEl.querySelector('#outDetailNo').innerText = transferDetail.outDetailNo;
  detailEl.querySelector(
      '#transferAmount').value = transferDetail.transferAmount;
  detailEl.querySelector(
      '#transferRemark').value = transferDetail.transferRemark;
  detailEl.querySelector('#openid').value = transferDetail.openid;
  detailEl.querySelector('#userName').value = transferDetail.userName;
  detailEl.querySelector('#outDetailNo').innerText = transferDetail.outDetailNo;
  detailEl.querySelector('#transferAmount').addEventListener('change',
      checkInput);
  detailEl.querySelector('#transferRemark').addEventListener('change',
      checkInput);
  detailEl.querySelector('#openid').addEventListener('change', checkInput);
  detailEl.querySelector('#deleteBtn').addEventListener('click', () => {
    if (batchData.totalNum <= 1) {
      alert('至少保留一项转账明细');
      return;
    }

    if (confirm('确定要删除转账明细？')) {
      detailEl.remove();
      batchData.totalNum--;
      renderDeleteBtn();
    }
  });
  transferDetailListEl.append(detailEl);
}

function renderDeleteBtn() {
  document.querySelectorAll('#transferDetailIndex').forEach(
      (indexEl, index) => {
        indexEl.innerHTML = '转账明细列表' + (index + 1);
      });
  if (batchData.totalNum <= 1) {
    document.querySelectorAll('#deleteBtn').forEach(deleteBtnEl => {
      deleteBtnEl.classList.add('hide');
    });
  } else {
    document.querySelectorAll('#deleteBtn').forEach(deleteBtnEl => {
      deleteBtnEl.classList.remove('hide');
    });
  }
}

async function submitTransfer() {
  if (!isValidForm()) { // 点击提交的时候提示输入不规范
    return;
  }
  const formData = getFormData();
  if (!formData) {
    return;
  }
  loadingDialog.classList.remove('hide');
  submitBtn.disabled = true;
  try {
    // 发送HTTP请求
    const response = await fetch('/create-batch', {
      method: 'POST',
      body: JSON.stringify(formData),
      headers: {
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const resp = await response.json();
      if (resp.code == 'SUCCESS') {
        showTranferResultDialog();
      } else {
        alert('创建批次失败:' + resp.errMsg);
      }
    } else {
      alert('创建批次失败');
    }
    submitBtn.disabled = false;
    loadingDialog.classList.add('hide');
  } catch (error) {
    alert('创建批次失败：' + error.message);
    console.log('创建批次失败', error);
    submitBtn.disabled = false;
    loadingDialog.classList.add('hide');
  }
}

function getFormData() {
  const formData = batchData;
  formData.transferDetailList = [];
  formData.totalAmount = 0;
  const detailElList = document.querySelectorAll('#transferDetail');
  detailElList.forEach(detailEl => {
    const detail = {
      outDetailNo: detailEl.querySelector('#outDetailNo').innerText,
      transferAmount: Number(detailEl.querySelector('#transferAmount').value),
      transferRemark: detailEl.querySelector('#transferRemark').value,
      openid: detailEl.querySelector('#openid').value,
      userName: detailEl.querySelector('#userName').value,
    };
    formData.transferDetailList.push(detail);
    formData.totalAmount += detail.transferAmount;
  });
  formData.totalNum = formData.transferDetailList.length;
  return formData;
}

function isValidForm() {
  let isValid = true;
  const detailElList = document.querySelectorAll('#transferDetail');
  detailElList.forEach(detailEl => {
    if (!(detailEl.querySelector('#transferAmount').checkValidity() &&
        detailEl.querySelector('#transferRemark').checkValidity() &&
        detailEl.querySelector('#openid').checkValidity() &&
        detailEl.querySelector('#userName').checkValidity)) {
      isValid = false;
      const formBoxEl = detailEl.querySelector('#formBox');
      formBoxEl.classList.add('check-box');
    }
  });
  return isValid;
}

function isRequireInputValid() {
  let isValid = true;
  const detailElList = document.querySelectorAll('#transferDetail');
  detailElList.forEach(detailEl => {
    if (!(detailEl.querySelector('#transferAmount').value.length &&
        detailEl.querySelector('#transferRemark').value.length &&
        detailEl.querySelector('#openid').value.length)) {
      isValid = false;
    }
  });
  return isValid;
}

function checkInput() {
  if (isRequireInputValid()) {
    submitBtn.disabled = false;
  } else {
    submitBtn.disabled = true;
  }
}

function showTranferResultDialog() {
  document.getElementById('resultDialog').className = '';
}

function gotoTransferResult() {
  window.location.replace('/result.html?outBatchNo=' + batchData.outBatchNo);
}
