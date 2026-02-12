package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Livros")
                        .description("API para gerenciamento de livros com OpenSearch")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seu@email.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Load Balancer"),
                        new Server()
                                .url("http://localhost:8081")
                                .description("Instância 1"),
                        new Server()
                                .url("http://localhost:8082")
                                .description("Instância 2"),
                        new Server()
                                .url("http://localhost:8083")
                                .description("Instância 3")
                ));
    }
}
