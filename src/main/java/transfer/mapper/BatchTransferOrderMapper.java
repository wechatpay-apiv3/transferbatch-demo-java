package transfer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import transfer.dto.BatchTransferOrderEntity;

/** 批次单表数据库访问层 */
@Mapper
@Repository
public interface BatchTransferOrderMapper {

  BatchTransferOrderEntity query(String mchid, String outBatchNo);

  void create(BatchTransferOrderEntity entity);

  void update(BatchTransferOrderEntity entity);
}
