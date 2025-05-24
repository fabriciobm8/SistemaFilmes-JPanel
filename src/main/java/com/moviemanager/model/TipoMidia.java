package com.moviemanager.model;

public enum TipoMidia {
    DVD("DVD"),
    BD("BD"),
    BD_3D("3D-BD"),
    BD_4K("4K-BD"),
    VHS("VHS"),
    VCD("VCD");
    
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