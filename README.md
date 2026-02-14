# Projeto CRUD de Livros com Spring Boot, OpenSearch e Nginx Load Balancer

Este projeto demonstra uma aplicação CRUD (Create, Read, Update, Delete) de livros, desenvolvida com Spring Boot, utilizando OpenSearch como banco de dados NoSQL e Nginx para balanceamento de carga entre múltiplas instâncias da API. A documentação da API é gerada automaticamente com Swagger/OpenAPI.

## Tecnologias Utilizadas

*   **Java 17**: Linguagem de programação.
*   **Spring Boot**: Framework para construção de aplicações Java.
*   **Maven**: Ferramenta de gerenciamento de dependências e build do projeto.
*   **OpenSearch 2.11.1**: Banco de dados distribuído de busca e análise, compatível com Elasticsearch.
*   **Lombok**: Biblioteca que ajuda a reduzir o código boilerplate Java.
*   **Swagger/OpenAPI (springdoc-openapi-starter-webmvc-ui)**: Para documentação e teste interativo da API.
*   **Docker & Docker Compose**: Para orquestração e gerenciamento dos serviços em contêineres.
*   **Nginx**: Servidor web utilizado como balanceador de carga para as instâncias da API.

## Estrutura do Projeto

O projeto segue uma estrutura padrão de uma aplicação Spring Boot:

```
.
├── src/main/java/com/example/demo/
│   ├── config/             # Classes de configuração (OpenAPI, OpenSearch)
│   ├── controller/         # Endpoints da API (LivroController)
│   ├── model/              # Modelos de dados (Livro)
│   ├── repository/         # Interfaces de repositório (LivroRepository)
│   └── service/            # Lógica de negócio (LivroService)
├── src/main/resources/
│   └── application.properties # Configurações da aplicação
├── Dockerfile              # Definição da imagem Docker da aplicação Spring Boot
├── docker-compose.yml      # Definição dos serviços Docker (OpenSearch, Spring APIs, Nginx)
├── nginx.conf              # Configuração do Nginx para balanceamento de carga
└── pom.xml                 # Configurações do projeto Maven
```

## Como Iniciar o Projeto com Docker Compose

Para colocar o projeto em funcionamento usando Docker Compose, siga os passos abaixo:

1.  **Pré-requisitos:** Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina.

2.  **Construa o Projeto Maven:**
    Navegue até a raiz do projeto e execute o seguinte comando Maven para compilar a aplicação e gerar o arquivo `.jar`:
    ```bash
    ./mvnw clean install -DskipTests
    ```
    Este comando irá gerar o arquivo `demo-0.0.1-SNAPSHOT.jar` dentro do diretório `target/`.

3.  **Inicie os Serviços com Docker Compose:**
    Após a compilação, você pode iniciar todos os serviços (OpenSearch, três instâncias da API Spring Boot e Nginx) com o Docker Compose:
    ```bash
    docker-compose up --build -d
    ```
    *   `--build`: Garante que as imagens Docker das APIs Spring Boot sejam reconstruídas.
    *   `-d`: Inicia os contêineres em modo detached (em segundo plano).

    Aguarde alguns minutos para que todos os serviços sejam inicializados, especialmente o OpenSearch e as APIs Spring Boot, que dependem do OpenSearch. Você pode verificar o status dos contêineres com `docker-compose ps`.

## Acessando o Swagger UI

Uma vez que todos os serviços estejam em execução, você pode acessar a documentação interativa da API (Swagger UI) através do Nginx Load Balancer ou diretamente em cada instância da API:

*   **Via Nginx Load Balancer (recomendado):**
    ```
    http://localhost:8080/swagger-ui.html
    ```

    ```
    Ao entrar no Swagger, vá em "Exprole" e digite:/api-docs
    ```

*   **Via Instância da API 1:**
    ```
    http://localhost:8081/swagger-ui.html
    ```

*   **Via Instância da API 2:**
    ```
    http://localhost:8082/swagger-ui.html
    ```

*   **Via Instância da API 3:**
    ```
    http://localhost:8083/swagger-ui.html
    ```

O Swagger UI fornecerá uma interface para explorar os endpoints da API, ver os modelos de dados e realizar requisições de teste.

## Endpoints da API (Exemplos)

A API de livros provavelmente terá os seguintes endpoints (ajuste conforme a implementação exata):

*   `POST /livros`: Criar um novo livro.
*   `GET /livros`: Listar todos os livros.
*   `GET /livros/{id}`: Obter um livro pelo ID.
*   `PUT /livros/{id}`: Atualizar um livro existente.
*   `DELETE /livros/{id}`: Excluir um livro.

Consulte o Swagger UI para a lista completa e detalhes de cada endpoint.

## Desligando os Serviços

Para parar e remover todos os contêineres e redes criadas pelo Docker Compose, execute:

```bash
docker-compose down
```
