package test;


import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.apache.http.HttpHost;

import java.util.Collections;

public class ElasticTest {


    public static void main(String[] args) {

        try {


            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http"),
                            new HttpHost("localhost", 9201, "http")));


            client.close();



            System.out.println("finished  1.");


            // https://qbox.io/blog/rest-calls-new-java-elasticsearch-client-tutorial


            RestClient restClient = RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9205, "http")).build();


            HttpEntity entity = new NStringEntity(
                    "{\n" +
                            "    \"company\" : \"qbox\",\n" +
                            "    \"title\" : \"Elasticsearch rest client\"\n" +
                            "}", ContentType.APPLICATION_JSON);

            System.out.println("making request ");

            Response indexResponse = restClient.performRequest(
                    "PUT",
                    "/blog/post/1",
                    Collections.<String, String>emptyMap(),
                    entity);

            System.out.println("writing response ");
            System.out.println(EntityUtils.toString(indexResponse.getEntity()));


            System.out.println("finished  2");


        } catch (Exception x) {

            x.printStackTrace();

            System.out.println(x.getMessage());
        }
    }


}
