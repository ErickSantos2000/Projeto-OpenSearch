package com.example.demo.controller;

import com.example.demo.model.Livro;
import com.example.demo.repository.LivroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
    public Livro criarLivro(@RequestBody Livro livro) throws IOException {
        livroRepository.salvar(livro); // salva no OpenSearch
        return livro;
    }

    @GetMapping("/{id}")
    public Livro buscarLivro(@PathVariable String id) throws IOException {
        return livroRepository.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable String id) throws IOException {
        livroRepository.deletar(id);
    }

    @GetMapping
    public List<Livro> listarTodos() throws IOException {
        return livroRepository.buscarTodos();
    }
}


