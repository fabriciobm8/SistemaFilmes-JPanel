package com.moviemanager.model;

public enum Origem {
    INTERNACIONAL("Internacional"),
    NACIONAL("Nacional");
    private final String descricao;
    
    Origem(String descricao) {
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