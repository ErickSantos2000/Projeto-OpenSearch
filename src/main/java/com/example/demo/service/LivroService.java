package com.example.demo.service;

// spring
import com.example.demo.model.Livro;
import org.opensearch.client.opensearch._types.FieldValue;
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

    public List<Livro> buscaPorTitulo(String titulo) throws IOException {
        SearchResponse<Livro> resposta = client.search(new SearchRequest.Builder()
                .index(INDEX)
                .query(q -> q.match(m -> m
                        .field("titulo")
                        .query(FieldValue.of(titulo))))
                .size(100)
                .build(), Livro.class);

        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source());
        }
        return resultado;
    }

    public List<Livro> buscaPorAutor(String autor) throws IOException {
        SearchResponse<Livro> resposta = client.search(new SearchRequest.Builder()
                .index(INDEX)
                .query(q -> q.match(m -> m
                        .field("autor")
                        .query(FieldValue.of(autor))))
                .size(100)
                .build(), Livro.class);

        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source());
        }
        return resultado;
    }

    public List<Livro> buscaPorAno(int ano) throws IOException {
        SearchResponse<Livro> resposta = client.search(new SearchRequest.Builder()
                .index(INDEX)
                .query(q -> q.term(t -> t
                        .field("anoPublicacao")
                        .value(FieldValue.of(ano))))
                .size(100)
                .build(), Livro.class);

        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) {
            resultado.add(hit.source());
        }
        return resultado;
    }



    public void salvar(Livro livro) throws IOException {
        // criando a requisição de indexação manualmente
        IndexRequest<Livro> request = new IndexRequest.Builder<Livro>() // cria o builder para o tipo Livro
                .index(INDEX)                                           // define onde o documento sera salvo
                .id(livro.getId())                                      // define a chave unica do documento
                .document(livro)                                        // objeto Java que sera convertido para JSON
                .build();                                               // gera o objeto IndexRequest<Livro> pronto para enviar

        client.index(request); // envia a requisição
    }

    public Livro buscarPorId(String id) throws IOException { // metodo de busca por id
        GetResponse<Livro> busca = client.get(new GetRequest.Builder() // cria um builder para requisição de busca
                .index(INDEX) // define o indicide onde buscar
                .id(id) // define o id do documento que queremos buscar

                // finaliza a construção da requisição
                .build(), Livro.class); // diz para converter o JSON retornado em um Objeto

                // verifica se o documento foi encontrado
                if(busca.found()){
                    return busca.source(); // se encontrado retorna obj livro
                }
                return null; // se não, retorna null
    }

    public void deletar(String id) throws IOException {
        client.delete(new DeleteRequest.Builder()
                .index(INDEX)
                .id(id)
                .build());
    }

    public List<Livro> buscarTodos() throws IOException {
        // cria um builder para a requisição de busca
        // resultado sera armazenado em busca
        SearchResponse<Livro> resposta = client.search(new SearchRequest.Builder()
                .index(INDEX) // define o indice onde buscar
                .size(100) // define o número máximo de resultados retornados
                .build(), Livro.class); // inicializa a requisição e indica que o JSON será convertido para objetos

        // cria uma lista vazia para armazenar os livros encontrados
        List<Livro> resultado = new ArrayList<>();
        for (Hit<Livro> hit : resposta.hits().hits()) { // tera sobre cada “hit”, cada documento retornado pelo OpenSearch
            resultado.add(hit.source());
        }
        return resultado;
    }
}
