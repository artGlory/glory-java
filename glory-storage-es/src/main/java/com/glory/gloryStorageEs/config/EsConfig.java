package com.glory.gloryStorageEs.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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

    /**
     * @return org.elasticsearch.client.RestClient
     * @Description Create the low-level client
     * @Param []
     * @Author hyy
     * @Date 2021-12-31 11:35
     **/
    @Bean
    public RestClient restClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("192.168.44.109", 9200)
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
