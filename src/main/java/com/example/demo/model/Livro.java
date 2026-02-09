package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data // gera getters, setters, toString, equals e hashCode
@AllArgsConstructor // // gera construtor com todos os atributos
public class Livro {

    private String id;
    private String titulo;
    private String autor;
    private String descricao;
    private Integer anoPublicacao;
    private Boolean disponivel;

    public Livro() {}

}
