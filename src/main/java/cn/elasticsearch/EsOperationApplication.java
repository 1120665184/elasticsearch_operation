package cn.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class EsOperationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsOperationApplication.class, args);
    }

}
