package cn.elasticsearch.service;

public interface ExistsMapper {

    /**
     * 检验文档是否存在
     * @param index
     * @param id
     * @return
     * @throws Exception
     */
    boolean documentExists(String index,String id) throws Exception;
}
