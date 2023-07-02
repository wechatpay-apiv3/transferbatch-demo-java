package transfer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("transfer.mapper")
public class BatchTransferApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchTransferApplication.class, args);
  }
}
