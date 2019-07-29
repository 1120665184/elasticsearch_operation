package cn.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configurable
@Component
@Slf4j
public class ElasticSearchConfig {

    public ElasticSearchConfig(){
        log.info("es初始化...");
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder());
        return client;
    }

    @Bean
    public RestClientBuilder restClientBuilder(){
        RestClientBuilder builder = RestClient.builder(getHttpHost());
      //  builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setConnectTimeout(5000).setSocketTimeout(10000);
            }
        });
        return builder;
    }


    @Bean
    public HttpHost[] getHttpHost(){
        List<HttpHost> hosts = new ArrayList<HttpHost>(){{
            add(new HttpHost("localhost",9200,"http"));
        }};
        return hosts.toArray(new HttpHost[hosts.size()]);
    }

}
