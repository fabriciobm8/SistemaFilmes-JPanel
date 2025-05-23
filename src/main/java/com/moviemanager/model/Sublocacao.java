package com.moviemanager.model;

public enum Sublocacao {
    APTO_QUARTO("Apto Quarto"),
    APTO_ESCRITORIO("Apto Escritório"),
    CASA_QUARTO("Casa Quarto");
    
    private final String descricao;
    
    Sublocacao(String descricao) {
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