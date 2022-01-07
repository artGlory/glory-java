package com.glory.gloryStorageEs.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @return
 * @Description 工具类
 * @Param
 * @Author hyy
 * @Date 2021-12-31 11:16
 **/
@Configuration
@PropertySource(value = "classpath:application-es.properties")
public class EsConfig {
    @Value("${elasticsearch.httpHosts}")
    private String httpHosts;

    /**
     * @return org.elasticsearch.client.RestClient
     * @Description Create the low-level client
     * @Param []
     * @Author hyy
     * @Date 2021-12-31 11:35
     **/
    @Bean
    public RestClient restClient() {
        List<HttpHost> httpHostList = new ArrayList<>();
        for (String httpHost : httpHosts.split(",")) {
            httpHostList.add(new HttpHost(httpHost.split(":")[0], Integer.parseInt(httpHost.split(":")[1])));
        }
        RestClient restClient = RestClient.builder(
                httpHostList.toArray(new HttpHost[httpHostList.size()])
        ).build();
        return restClient;
    }

    /**
     * @return co.elastic.clients.transport.ElasticsearchTransport
     * @Description Create the transport with a Jackson mapper
     * @Param [restClient]
     * @Author hyy
     * @Date 2021-12-31 11:35
     **/
    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return transport;
    }

    /**
     * @return co.elastic.clients.elasticsearch.ElasticsearchClient
     * @Description And create the API client
     * @Param [elasticsearchTransport]
     * @Author hyy
     * @Date 2021-12-31 11:34
     **/
    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport elasticsearchTransport) {
        ElasticsearchClient client = new ElasticsearchClient(elasticsearchTransport);
        return client;
    }
}
