package com.moviemanager.model;

public enum Genero {
    DRAMA("Drama"),
    ACAO("Ação"),
    FICCAO("Ficção"),
    TERROR("Terror");
    
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