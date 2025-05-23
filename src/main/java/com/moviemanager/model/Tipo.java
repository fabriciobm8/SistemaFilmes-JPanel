package com.moviemanager.model;

public enum Tipo {
    FILME("Filme"),
    SERIE("Série"),
    SHOW("Show"),
    DOCUMENTARIO("Documentário");
    
    private final String descricao;
    
    Tipo(String descricao) {
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