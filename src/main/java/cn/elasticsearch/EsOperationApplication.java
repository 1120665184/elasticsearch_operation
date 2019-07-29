package cn.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class EsOperationApplication {
    //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.2/java-rest-high-document-get.html
    public static void main(String[] args) {
        SpringApplication.run(EsOperationApplication.class, args);
    }

}
