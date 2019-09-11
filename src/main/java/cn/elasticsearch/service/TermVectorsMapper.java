package cn.elasticsearch.service;

import org.elasticsearch.client.core.TermVectorsResponse;

public interface TermVectorsMapper {

    TermVectorsResponse termVectorsRequest(String index,String id,String... fields) throws Exception;
}
