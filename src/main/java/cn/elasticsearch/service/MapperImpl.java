package cn.elasticsearch.service;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapperImpl implements IndexMapper ,GetMapper,ExistsMapper
        ,DeleteMapper,UpdateMapper,TermVectorsMapper{

    @Autowired
    private RestHighLevelClient client;


    @Override
    public IndexResponse indexRequest(Map<String, Object> val, String index, String id) throws Exception {

        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(val);
        return client.index(request, RequestOptions.DEFAULT);
    }

    @Override
    public IndexResponse indexRequest(XContentBuilder builder, String index, String id) throws Exception {
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(builder);

        return client.index(request,RequestOptions.DEFAULT);
    }

    @Override
    public IndexResponse indexRequest(String val, String index, String id) throws Exception {
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(val);

        return client.index(request,RequestOptions.DEFAULT.DEFAULT);
    }

    @Override
    public GetResponse getRequest(String index, String id) throws Exception {
        GetRequest request = new GetRequest(index,id);

        return client.get(request,RequestOptions.DEFAULT);
    }

    @Override
    public GetResponse getRequest(String index, String id, String[] includes, String[] excludes) throws Exception {
        GetRequest request = new GetRequest(index, id);
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true,includes,excludes);
        request.fetchSourceContext(fetchSourceContext);

        return client.get(request,RequestOptions.DEFAULT);
    }

    @Override
    public GetResponse getFieldByRequest(String index, String id, String... field) throws Exception {
        GetRequest request = new GetRequest(index, id);
        request.storedFields(field);

        return client.get(request,RequestOptions.DEFAULT);
    }

    @Override
    public boolean documentExists(String index, String id) throws Exception {
        GetRequest request = new GetRequest(index, id)
                .fetchSourceContext(new FetchSourceContext(false))
                .storedFields("_none_");


        return client.exists(request,RequestOptions.DEFAULT);
    }

    @Override
    public DeleteResponse deleteRequest(String index, String id) throws Exception {
        DeleteRequest request = new DeleteRequest(index, id);

        return client.delete(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequest(String index, String id, String valJson) throws Exception {

        UpdateRequest request = new UpdateRequest(index,id)
                .doc(valJson, XContentType.JSON);

        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequest(String index, String id, XContentBuilder builder) throws Exception {
        UpdateRequest request = new UpdateRequest(index, id)
                .doc(builder);
        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequest(String index, String id, Map<String, Object> val) throws Exception {
        UpdateRequest request = new UpdateRequest(index, id)
                .doc(val);
        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequest(String index, String id, Object... kv) throws Exception {
        UpdateRequest request = new UpdateRequest(index, id)
                .doc(kv);
        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequest(String index, String id, Script script) throws Exception {
        UpdateRequest request = new UpdateRequest(index, id)
                .script(script)
                //更新成功后返回数据
                .fetchSource(true);

        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public UpdateResponse updateRequestUpserts(String index, String id, Map<String,Object> val,
                                               Map<String,Object> Upserts) throws Exception {
        UpdateRequest request = new UpdateRequest(index, id)
                .doc(val)
                .upsert(Upserts);

        return client.update(request,RequestOptions.DEFAULT);
    }

    @Override
    public TermVectorsResponse termVectorsRequest(String index, String id, String... fields) throws Exception {
        TermVectorsRequest request = new TermVectorsRequest(index, id);
        request.setFields(fields);
        request.setTermStatistics(true);

        return client.termvectors(request,RequestOptions.DEFAULT);
    }
}
