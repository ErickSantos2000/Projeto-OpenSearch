package com.example.demo.service;

import com.example.demo.model.Livro;
import com.example.demo.repository.LivroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class InicializacaoDadosService implements CommandLineRunner {

    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public InicializacaoDadosService(LivroRepository livroRepository, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.livroRepository = livroRepository;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        // verifica se o indice "livros" está vazio
        if (livroRepository.buscarTodos().isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados. Inicializando dados a partir de add_livros.json...");
            try {
                // carrega o arquivo JSON da raiz do projeto
                Resource resource = resourceLoader.getResource("file:add_livros.json");

                // verifica se o recurso existe e pode ser lido
                if (resource.exists() && resource.isReadable()) {
                    try (InputStream inputStream = resource.getInputStream()) {
                        List<Livro> livros = objectMapper.readValue(inputStream, new TypeReference<List<Livro>>() {});
                        for (Livro livro : livros) {
                            livroRepository.salvar(livro);
                        }
                        System.out.println("Sucesso: " + livros.size() + " livros foram inicializados.");
                    }
                } else {
                    System.err.println("Erro: O arquivo add_livros.json não foi encontrado");
                }
            } catch (IOException e) {
                System.err.println("Erro ao inicializar os dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Já existem livros no banco de dados. Pulando a inicialização de dados.");
        }
    }
}