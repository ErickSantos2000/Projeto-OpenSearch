package com.example.demo.controller;

import com.example.demo.model.Livro;
import com.example.demo.service.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/titulo")
    public List<Livro> buscarPorTitulo(@RequestParam String titulo) throws IOException {
        return livroService.buscaPorTitulo(titulo);
    }

    // Busca livros por autor
    @GetMapping("/autor")
    public List<Livro> buscarPorAutor(@RequestParam String autor) throws IOException {
        return livroService.buscaPorAutor(autor);
    }

    // Busca livros por ano
    @GetMapping("/ano")
    public List<Livro> buscarPorAno(@RequestParam int ano) throws IOException {
        return livroService.buscaPorAno(ano);
    }

    @PostMapping
    public Livro criarLivro(@RequestBody Livro livro) throws IOException {
        livroService.salvar(livro); // salva no OpenSearch
        return livro;
    }

    @GetMapping("/{id}")
    public Livro buscarLivro(@PathVariable String id) throws IOException {
        return livroService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable String id) throws IOException {
        livroService.deletar(id);
    }

    @GetMapping
    public List<Livro> listarTodos() throws IOException {
        return livroService.buscarTodos();
    }
}


