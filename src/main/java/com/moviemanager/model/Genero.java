package com.moviemanager.model;

public enum Genero {

    ACAO("Ação"),
    SUSPENSE("Suspense"),
    FICCAO("Ficção"),
    TERROR("Terror"),
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    AVENTURA("Aventura"),
    GUERRA("Guerra"),
    WESTERN("Western"),
    DOCUMENTARIO("Documentário"),
    ANIMACAO("Animação"),
    MUSICAL("Musical"),
    INFANTIL("Infantil");
    
    private final String descricao;
    
    Genero(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}