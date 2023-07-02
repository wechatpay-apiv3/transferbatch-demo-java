package transfer.service;

import transfer.dto.TransferDetailOrderEntity;

public interface TransferDetailOrder {

  /**
   * 查询明细单
   *
   * @param outBatchNo 商户转账批次单
   * @param outDetailNo 商户转账明细单
   * @return
   */
  TransferDetailOrderEntity queryTransferDetailOrder(String outBatchNo, String outDetailNo);
}
