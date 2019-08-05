package cn.elasticsearch.service;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.script.Script;

import java.util.Map;

public interface UpdateMapper {

    UpdateResponse updateRequest(String index,String id,String valJson) throws Exception;

    UpdateResponse updateRequest(String index, String id, XContentBuilder builder) throws Exception;

    UpdateResponse updateRequest(String index, String id, Map<String,Object> val) throws Exception;

    UpdateResponse updateRequest(String index,String id,Object... kv) throws Exception;

    UpdateResponse updateRequest(String index, String id, Script script) throws Exception;

    /**
     * 更新数据是如果没有数据则创建数据
     * @param index
     * @param id
     * @param val
     * @param Upserts
     * @return
     * @throws Exception
     */
    UpdateResponse updateRequestUpserts(String index,String id,Map<String,Object> val,
                                        Map<String,Object> Upserts) throws Exception;

}
