package transfer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import transfer.dto.BatchTransferOrderEntity;

/** 批次单表数据库访问层 */
@Mapper
@Repository
public interface BatchTransferOrderMapper {

  BatchTransferOrderEntity query(
      @Param("mchid") String mchid, @Param("outBatchNo") String outBatchNo);

  void create(BatchTransferOrderEntity entity);

  void update(BatchTransferOrderEntity entity);
}
