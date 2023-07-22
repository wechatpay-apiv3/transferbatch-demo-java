package transfer.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import transfer.dto.TransferDetailOrderEntity;

/** 明细单表数据库访问层 */
@Mapper
@Repository
public interface TransferDetailOrderMapper {

  TransferDetailOrderEntity query(
      @Param("mchid") String mchid,
      @Param("outBatchNo") String outBatchNo,
      @Param("outDetailNo") String outDetailNo);

  ArrayList<TransferDetailOrderEntity> find(
      @Param("mchid") String mchid, @Param("outBatchNo") String outBatchNo);

  void create(TransferDetailOrderEntity entity);

  void update(TransferDetailOrderEntity entity);
}
