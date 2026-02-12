package com.example.demo.config;

// imports relacionado oa Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// imports para configuração do OpenSearch
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.opensearch.client.RestClient;
import org.apache.http.HttpHost;

// import para conversão do JSON
import org.opensearch.client.json.jackson.JacksonJsonpMapper;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.port}")
    private Integer port;

    @Value("${opensearch.scheme}")
    private String scheme;
    
    @Bean
    public OpenSearchClient OpenSearchClient(){
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port, scheme)
        ).build();

        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new OpenSearchClient(transport);
    }
}