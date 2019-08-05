package cn.elasticsearch.service;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Map;

public interface IndexMapper {

    /**
     * 插入数据
     * @param val
     * @param index
     * @param id
     * @return
     * @throws Exception
     */
    IndexResponse indexRequest(Map<String,Object> val,String index,String id) throws Exception;

    IndexResponse indexRequest(XContentBuilder builder,String index,String id) throws Exception;

    IndexResponse indexRequest(String val,String index,String id) throws Exception;

}
