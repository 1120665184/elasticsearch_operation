package cn.elasticsearch.service;

import org.elasticsearch.action.delete.DeleteResponse;

public interface DeleteMapper {

    DeleteResponse deleteRequest(String index,String id) throws Exception;
}
