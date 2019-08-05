package cn.elasticsearch.service;

import org.elasticsearch.action.get.GetResponse;

public interface GetMapper {

    GetResponse getRequest(String index,String id) throws Exception;

    GetResponse getRequest(String index,String id,String [] includes,String [] excludes) throws Exception;

    /**
     * 获取指定的field值
     * @param index
     * @param id
     * @param field
     * @return
     * @throws Exception
     */
    GetResponse getFieldByRequest(String index,String id,String... field) throws Exception;

}
