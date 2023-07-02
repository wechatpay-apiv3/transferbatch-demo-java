package transfer.service;

import transfer.dto.BatchTransferOrderEntity;

public interface BatchTransferOrder {

  /**
   * 创建批次转账单
   *
   * @param batchTransferOrderEntity 批次转账单实体属性
   */
  void createBatchTransferOrder(BatchTransferOrderEntity batchTransferOrderEntity);

  /**
   * 查询批次转账单
   *
   * @param outBatchNo 商户批次单号
   * @return
   */
  BatchTransferOrderEntity queryBatchTransferOrder(String outBatchNo);
}
