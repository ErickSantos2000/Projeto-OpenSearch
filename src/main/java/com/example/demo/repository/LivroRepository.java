package com.example.demo.repository;

import com.example.demo.model.Livro;

// spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// OpenSearch
import org.opensearch.client.opensearch.OpenSearchClient; // cliente do OpenSearch
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.query_dsl.MatchQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TermQuery;
import org.opensearch.client.opensearch.core.IndexRequest; // indexar/Salvar
import org.opensearch.client.opensearch.core.DeleteRequest; // deletar
import org.opensearch.client.opensearch.core.SearchRequest; // buscar múltiplos
import org.opensearch.client.opensearch.core.SearchResponse; // resposta de busca
import org.opensearch.client.opensearch.core.UpdateRequest;
import org.opensearch.client.opensearch.core.UpdateResponse;
import org.opensearch.client.opensearch.core.search.Hit;

import java.io.IOException;  // para tratar exceções de I/O
import java.util.ArrayList;   // para lista de resultados
import java.util.List;

// diz ao Spring que essa classe é um componente de acesso a dados
// permite que Spring faça injeção de dependência e trate exceções de persistência
@Repository
public class LivroRepository {

    // injeta o bean do OpenSearchClient que foi criado na classe de configuraçao
    // spring gerencia o ciclo de vida dele
    @Autowired
    private OpenSearchClient client;

    // cada documento no OpenSearch precisa estar dentro de um índice, como se fosse uma tabela
    // aqui é definido que todos os livros vão para o indice "livros"
    private final String INDEX = "livros";

    public List<Livro> buscaPorTitulo(String titulo) throws IOException {

        // cria a query do tipo match para o campo "titulo"
        MatchQuery matchTitulo = new MatchQuery.Builder()
                .field("titulo")
                .query(FieldValue.of(titulo))
                .build();

        // encapsula o MatchQuery dentro de um Query
        Query query = new Query.Builder()
                .match(matchTitulo)
                .build();

        // cria a requisição de busca
        SearchRequest request = new SearchRequest.Builder()
                .index(INDEX)
                .query(query)
                .size(100)
                .build();

        // executa a busca no OpenSearch
        SearchResponse<Livro> resposta = client.search(request, Livro.class);

        // cria uma lista vazia para armazenar os livros encontrados
        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source()); // converte o JSON retornado em objeto Livro
        }

        return resultado;
    }

    public List<Livro> buscaPorAutor(String autor) throws IOException {

        // cria a query do tipo match para o campo "autor"
        MatchQuery matchAutor = new MatchQuery.Builder()
                .field("autor")
                .query(FieldValue.of(autor))
                .build();

        // encapsula o MatchQuery dentro de um Query
        Query query = new Query.Builder()
                .match(matchAutor)
                .build();

        // cria a requisição de busca
        SearchRequest request = new SearchRequest.Builder()
                .index(INDEX)
                .query(query)
                .size(100)
                .build();

        // executa a busca no OpenSearch
        SearchResponse<Livro> resposta = client.search(request, Livro.class);

        // cria uma lista vazia para armazenar os livros encontrados
        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source());
        }

        return resultado;
    }

    public List<Livro> buscaPorAno(int ano) throws IOException {

        // cria a query do tipo term (ideal para campos numéricos)
        TermQuery termAno = new TermQuery.Builder()
                .field("anoPublicacao")
                .value(FieldValue.of(ano))
                .build();

        // encapsula o TermQuery dentro de um Query
        Query query = new Query.Builder()
                .term(termAno)
                .build();

        // cria a requisição de busca
        SearchRequest request = new SearchRequest.Builder()
                .index(INDEX)
                .query(query)
                .size(100)
                .build();

        // executa a busca no OpenSearch
        SearchResponse<Livro> resposta = client.search(request, Livro.class);

        // cria uma lista vazia para armazenar os livros encontrados
        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source());
        }

        return resultado;
    }

    // ------------------ OPERAÇÕES BASICAS DO CRUD -----------------------------
    public void salvar(Livro livro) throws IOException {
        // criando a requisição de indexação manualmente
        IndexRequest<Livro> request = new IndexRequest.Builder<Livro>() // cria o builder para o tipo Livro
                .index(INDEX)                                           // define onde o documento sera salvo
                .id(livro.getId())                                      // define a chave unica do documento
                .document(livro)                                        // objeto Java que sera convertido para JSON
                .build();                                               // gera o objeto IndexRequest<Livro> pronto para enviar

        client.index(request); // envia a requisição
    }

    public void atualizarLivro(String id, Livro livro) throws IOException {
        // cria a requisição de atualização
        UpdateRequest<Livro, Livro> request = new UpdateRequest.Builder<Livro, Livro>()
                .index(INDEX)     // o nome do seu índice
                .id(id)          // o ID do documento que será alterado
                .doc(livro)      // o objeto com os novos dados (Partial Document)
                .build();

        // executa a operação no cliente
        UpdateResponse<Livro> response = client.update(request, Livro.class);

        // log ou verificação simples
        if (response.result().name().equals("Updated")) {
            System.out.println("Livro atualizado com sucesso!");
        } else if (response.result().name().equals("Noop")) {
            System.out.println("Nenhuma alteração detectada (No-operation).");
        }
    }

    public void deletar(String id) throws IOException {

        // cria a requisição de delete
        DeleteRequest request = new DeleteRequest.Builder()
                .index(INDEX)
                .id(id)
                .build();

        client.delete(request);
    }

    public List<Livro> buscarTodos() throws IOException {

        // cria um builder para a requisição de busca
        SearchRequest request = new SearchRequest.Builder()
                .index(INDEX) // define o indice onde buscar
                .size(100) // define o número máximo de resultados retornados
                .build();

        // executa a busca
        SearchResponse<Livro> resposta = client.search(request, Livro.class);

        // cria uma lista vazia para armazenar os livros encontrados
        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) { // itera sobre cada documento retornado
            resultado.add(hit.source());
        }

        return resultado;
    }
}
