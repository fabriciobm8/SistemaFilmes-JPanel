package com.moviemanager.model;

public class Filme {
    private Long id;
    private String descricao;
    private String diretor;
    private Genero genero;
    private Tipo tipo;
    private Origem origem;
    private TipoMidia tipoMidia;
    private Locacao locacao;
    private Sublocacao sublocacao;
    private String estante;
    private String estantePrateleira;
    private String estantePrateleiraColuna;
    
    // Construtores
    public Filme() {
    }
    
    public Filme(Long id, String descricao, String diretor, Genero genero, Tipo tipo, 
                 Origem origem, TipoMidia tipoMidia, Locacao locacao, Sublocacao sublocacao,
                 String estante, String estantePrateleira, String estantePrateleiraColuna) {
        this.id = id;
        this.descricao = descricao;
        this.diretor = diretor;
        this.genero = genero;
        this.tipo = tipo;
        this.origem = origem;
        this.tipoMidia = tipoMidia;
        this.locacao = locacao;
        this.sublocacao = sublocacao;
        this.estante = estante;
        this.estantePrateleira = estantePrateleira;
        this.estantePrateleiraColuna = estantePrateleiraColuna;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDiretor() {
        return diretor;
    }
    
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }
    
    public Genero getGenero() {
        return genero;
    }
    
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public Tipo getTipo() {
        return tipo;
    }
    
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public Origem getOrigem() {
        return origem;
    }
    
    public void setOrigem(Origem origem) {
        this.origem = origem;
    }
    
    public TipoMidia getTipoMidia() {
        return tipoMidia;
    }
    
    public void setTipoMidia(TipoMidia tipoMidia) {
        this.tipoMidia = tipoMidia;
    }
    
    public Locacao getLocacao() {
        return locacao;
    }
    
    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }
    
    public Sublocacao getSublocacao() {
        return sublocacao;
    }
    
    public void setSublocacao(Sublocacao sublocacao) {
        this.sublocacao = sublocacao;
    }
    
    public String getEstante() {
        return estante;
    }
    
    public void setEstante(String estante) {
        this.estante = estante;
    }
    
    public String getEstantePrateleira() {
        return estantePrateleira;
    }
    
    public void setEstantePrateleira(String estantePrateleira) {
        this.estantePrateleira = estantePrateleira;
    }
    
    public String getEstantePrateleiraColuna() {
        return estantePrateleiraColuna;
    }
    
    public void setEstantePrateleiraColuna(String estantePrateleiraColuna) {
        this.estantePrateleiraColuna = estantePrateleiraColuna;
    }
    
    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", diretor='" + diretor + '\'' +
                ", genero=" + genero +
                ", tipo=" + tipo +
                ", origem=" + origem +
                ", tipoMidia=" + tipoMidia +
                ", locacao=" + locacao +
                ", sublocacao=" + sublocacao +
                ", estante='" + estante + '\'' +
                ", estantePrateleira='" + estantePrateleira + '\'' +
                ", estantePrateleiraColuna='" + estantePrateleiraColuna + '\'' +
                '}';
    }
}