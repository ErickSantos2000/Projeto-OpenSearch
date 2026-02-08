package com.example.demo.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Livro {

    private String id;
    private String titulo;
    private String autor;
    private String descricao;
    private Integer anoPublicacao;
    private Boolean disponivel;

    public Livro() {}

}
