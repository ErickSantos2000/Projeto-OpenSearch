package com.example.demo.service;

// spring
import com.example.demo.model.Livro;
import org.opensearch.client.opensearch.core.GetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// OpenSearch
import org.opensearch.client.opensearch.OpenSearchClient; // cliente do OpenSearch
import org.opensearch.client.opensearch.core.IndexRequest; // indexar/Salvar
import org.opensearch.client.opensearch.core.GetResponse;  // resposta de busca
import org.opensearch.client.opensearch.core.DeleteRequest; // deletar
import org.opensearch.client.opensearch.core.SearchRequest; // buscar múltiplos
import org.opensearch.client.opensearch.core.SearchResponse; // resposta de busca
import org.opensearch.client.opensearch.core.search.Hit;

import java.io.IOException;  // para tratar exceções de I/O
import java.util.ArrayList;   // para lista de resultados
import java.util.List;

// diz ao Spring que essa classe é um componente de acesso a dados
// permite que Spring faça injeção de dependência e trate exceções de persistência
@Repository
public class LivroService {

    // injeta o bean do OpenSearchClient que foi criado na classe de configuraçao
    // spring gerencia o ciclo de vida dele
    @Autowired
    private OpenSearchClient client;

    // cada documento no OpenSearch precisa estar dentro de um índice, como se fosse uma tabela
    // aqui é definido que todos os livros vão para o indice "livros"
    private final String INDEX = "livros";

    public void salvar(Livro livro) throws IOException {
        // Criando a requisição de indexação manualmente
        IndexRequest<Livro> request = new IndexRequest.Builder<Livro>() // cria o builder para o tipo Livro
                .index(INDEX)                                           // define onde o documento sera salvo
                .id(livro.getId())                                      // define a chave unica do documento
                .document(livro)                                        // objeto Java que sera convertido para JSON
                .build();                                               // gera o objeto IndexRequest<Livro> pronto para enviar

        client.index(request); // envia a requisição
    }

    public Livro buscarPorId(String id) throws IOException {
        GetResponse<Livro> response = client.get(new GetRequest.Builder()
                .index(INDEX)
                .id(id)
                .build(), Livro.class);
        return response.found() ? response.source() : null;
    }

    public void deletar(String id) throws IOException {
        client.delete(new DeleteRequest.Builder()
                .index(INDEX)
                .id(id)
                .build());
    }

    public List<Livro> buscarTodos() throws IOException {
        SearchResponse<Livro> response = client.search(new SearchRequest.Builder()
                .index(INDEX)
                .size(1000)
                .build(), Livro.class);

        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : response.hits().hits()) {
            resultado.add(hit.source());
        }
        return resultado;
    }
}
