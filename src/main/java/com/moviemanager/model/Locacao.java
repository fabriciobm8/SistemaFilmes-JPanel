package com.moviemanager.model;

public enum Locacao {
    CASA("Casa"),
    APARTAMENTO("Apartamento"),
    EMPRESTIMO("Empréstimo");
    
    private final String descricao;
    
    Locacao(String descricao) {
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