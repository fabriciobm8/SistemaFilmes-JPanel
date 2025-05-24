package com.moviemanager.model;

public enum Sublocacao {
    APTO_QUARTO("Apto Quarto"),
    APTO_ESCRITORIO("Apto Escritório"),
    APTO_SALA("Apto Sala"),
    APTO_SUITE("Apto Suíte"),
    CASA_QUARTO("Casa Quarto"),
    CASA_SALA("Casa Sala"),
    EMPRESTIMO_TEMP("Emp. Temporário");
    
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