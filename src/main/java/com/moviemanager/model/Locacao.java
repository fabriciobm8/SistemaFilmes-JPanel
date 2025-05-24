package com.moviemanager.model;

public enum Locacao {
    APARTAMENTO("Apartamento"),
    CASA("Casa"),
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