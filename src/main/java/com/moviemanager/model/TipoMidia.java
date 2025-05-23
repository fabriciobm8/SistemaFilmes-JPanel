package com.moviemanager.model;

public enum TipoMidia {
    BD("BD"),
    DVD("DVD");
    
    private final String descricao;
    
    TipoMidia(String descricao) {
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