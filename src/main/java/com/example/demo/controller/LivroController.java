package com.example.demo.controller;

import com.example.demo.model.Livro;
import com.example.demo.service.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/titulo")
    public List<Livro> buscarPorTitulo(@RequestParam String titulo){
        return livroService.buscaPorTitulo(titulo);
    }

    // Busca livros por autor
    @GetMapping("/autor")
    public List<Livro> buscarPorAutor(@RequestParam String autor){
        return livroService.buscaPorAutor(autor);
    }

    // Busca livros por ano
    @GetMapping("/ano")
    public List<Livro> buscarPorAno(@RequestParam int ano){
        return livroService.buscaPorAno(ano);
    }

    @PostMapping
    public Livro criarLivro(@RequestBody Livro livro){
        livroService.salvar(livro); // salva no OpenSearch
        return livro;
    }

    // ADIÇÃO: Atualização (PUT)
    @PutMapping("/{id}")
    public void atualizarLivro(@PathVariable String id, @RequestBody Livro livro){
        // Chama o método de atualização que criamos no Service
        livroService.atualizarLivro(id, livro);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable String id){
        livroService.deletar(id);
    }

    @GetMapping
    public List<Livro> listarTodos(){
        return livroService.buscarTodos();
    }
}


