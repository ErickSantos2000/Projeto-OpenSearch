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


// diz ao Spring que essa classe cria beans
// bean é um objeto gerenciado pelo Spring (ele cria, injeta e destroi quando necessario)
// aqui sera criado o OpenSearchClient, que é usado em varias classes

@Configuration
public class OpenSearchConfig {

    // @Value injeta uma configuração externa (do application.properties) na variável host
    // ${opensearch.host} = chave que o Spring procura no application.properties
    // o mesmo vale para porta e esquema
    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.porta}")
    private int porta;

    @Value("${opensearch.esquema}")
    private String esquema;

    // é onde o objeto sera criado e usado em toda a aplicação
    // sempre que o spring precisa de OpenSearch, o Spring injenta automaticamente
    @Bean
    public OpenSearchClient OpenSearchClient(){
        // essa parte sera responsavel por gerenciar as requisições HTTP
        RestClient restClient = RestClient.builder( // cria o cliente HTTP que vai se comunicar com o servidor
                new HttpHost(host, porta,esquema)  // ele conecta na URL configurada: http://localhost:9200 (host + port + scheme).
                                                  // não é Spring, é cliente oficial do OpenSearch
        ).build();

        // restClientTransport é o transporte que converte objetos Java para JSON e vice versa
        // jacksonJsonpMapper é o mapeador que faz a serialização JSON
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new OpenSearchClient(transport);

    }
}
