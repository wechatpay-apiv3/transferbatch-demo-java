package transfer.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import transfer.dto.TransferDetailOrderEntity;

/** 明细单表数据库访问层 */
@Mapper
@Repository
public interface TransferDetailOrderMapper {

  TransferDetailOrderEntity query(String mchid, String outBatchNo, String outDetailNo);

  ArrayList<TransferDetailOrderEntity> find(String mchid, String outBatchNo);

  void create(TransferDetailOrderEntity entity);

  void update(TransferDetailOrderEntity entity);
}
