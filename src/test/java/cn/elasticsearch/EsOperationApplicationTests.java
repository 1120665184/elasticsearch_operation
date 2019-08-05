package cn.elasticsearch;

import cn.elasticsearch.service.MapperImpl;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsOperationApplicationTests {

    @Autowired
    private MapperImpl elasticSearchMapper;


    @Test
    public void getTest(){
        try {
            GetResponse val = elasticSearchMapper.getRequest("posts", "1");
            System.out.println(val.getField("message"));
            System.out.println(val.getSourceAsMap().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRequestTest() throws Exception{
        //返回包含的制定字段
        String[] includes = new String[] {"message","*Data","user"};
        //过滤指定字段
        String[] excludes = Strings.EMPTY_ARRAY;
        GetResponse val = elasticSearchMapper.getRequest("posts", "2", includes, excludes);
        System.out.println(val.getSourceAsString());
    }

    @Test
    public void getFieldRequestTest() throws Exception {
        GetResponse val = elasticSearchMapper.getFieldByRequest("posts", "2", "user");
        System.out.println(val.getSource());
        System.out.println(val.getField("user"));
    }

    @Test
    public void getDocumentExistsTest() throws Exception {
        boolean val = elasticSearchMapper.documentExists("posts", "2");
        System.out.println(val);
    }


    @Test
    public void indexMapTest() {
        Map<String,Object> val = new HashMap<String,Object>(){
            {
                put("user","test");
                put("postData", LocalDateTime.now());
                put("message","try out elasticSearch");
                put("updated",LocalDateTime.now());
            }
        };

        try {
            IndexResponse posts = elasticSearchMapper.indexRequest(val, "posts", "1");
            System.out.println(posts.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void indexBuildTest(){
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user","test1");
                builder.timeField("postData",LocalDateTime.now());
                builder.field("message","try out elasticSearch2");
            }
            builder.endObject();
            IndexResponse posts = elasticSearchMapper.indexRequest(builder, "posts", "2");
            System.out.println(posts.status());
            System.out.println(posts.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteDocumentTest() throws Exception{
        DeleteResponse posts = elasticSearchMapper.deleteRequest("posts", "2");
        System.out.println(posts.status());
        System.out.println(posts.toString());
    }

    @Test
    public void updateDocumentTest() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(){
            {
                put("message","333333");
                put("count",0);
                put("updated",LocalDateTime.now());
                put("reason","daily update");
            }
        };

        UpdateResponse val = elasticSearchMapper.updateRequest("posts", "2", map);
        System.out.println(val.status());
        System.out.println(val.toString());

    }

    @Test
    public void updateDocumentScriptTest() throws Exception {
        Map<String,Object> parameters = Collections.singletonMap("count",3);

        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.count += params.count", parameters);
        UpdateResponse val = elasticSearchMapper.updateRequest("posts", "2", inline);
        System.out.println(val.status());
        System.out.println(val.toString());
        System.out.println(val.getGetResult().getSource()   );

    }

    @Test
    public void updateUpsertTest() throws Exception {
        Map<String,Object> update = new HashMap<String,Object>(){
            {
                put("message","333333");
                put("count",0);
                put("updated",LocalDateTime.now());
                put("reason","daily update");
            }
        };
        HashMap<String, Object> insert = new HashMap<String, Object>() {
            {
                put("created",LocalDateTime.now());
            }
        };
        UpdateResponse val = elasticSearchMapper.updateRequestUpserts("posts", "3", update, insert);
        System.out.println(val.status());
        System.out.println(val.toString());
    }

}
