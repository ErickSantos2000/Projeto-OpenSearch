package com.example.demo.service;

// classe de repository e model
import com.example.demo.repository.LivroRepository;
import com.example.demo.model.Livro;

// spring
import org.springframework.beans.factory.annotation.Autowired;

// OpenSearch
import org.springframework.stereotype.Service;

import java.io.IOException;  // para tratar exceções de I/O
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // OPERAÇÕES DE BUSCA
    public List<Livro> buscaPorTitulo(String titulo) {
        try {
            return livroRepository.buscaPorTitulo(titulo);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar livros por título: " + titulo, e);
        }
    }

    public List<Livro> buscaPorAutor(String autor) {
        try {
            return livroRepository.buscaPorAutor(autor);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar livros por autor: " + autor, e);
        }
    }

    public List<Livro> buscaPorAno(int ano) {
        try {
            return livroRepository.buscaPorAno(ano);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar livros pelo ano: " + ano, e);
        }
    }

    // OPERAÇÕES PRINCIPAIS DO CRUD
    public void salvar(Livro livro) {
        try {
            livroRepository.salvar(livro);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o livro: " + livro.getTitulo(), e);
        }
    }

    public void atualizarLivro(String id, Livro livro) {
        try {
            livroRepository.atualizarLivro(id, livro);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao atualizar o livro com id: " + id, e);
        }
    }

    public void deletar(String id) {
        try {
            livroRepository.deletar(id);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar o livro com id: " + id, e);
        }
    }

    public List<Livro> buscarTodos() {
        try {
            return livroRepository.buscarTodos();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar todos os livros", e);
        }
    }
}
