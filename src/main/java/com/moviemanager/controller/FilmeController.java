package com.moviemanager.controller;

import com.moviemanager.model.*;
import com.moviemanager.model.dao.FilmeDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmeController {
    private final FilmeDAO filmeDAO;
    
    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }
    
    public Filme salvar(Filme filme) throws SQLException {
        // Validação básica
        if (filme.getDescricao() == null || filme.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do filme é obrigatória");
        }
        
        return filmeDAO.save(filme);
    }
    
    public void excluir(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID do filme é obrigatório");
        }
        
        filmeDAO.delete(id);
    }
    
    public Filme buscarPorId(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID do filme é obrigatório");
        }
        
        return filmeDAO.findById(id);
    }
    
    public List<Filme> buscarTodos() throws SQLException {
        return filmeDAO.findAll();
    }
    
    public List<Filme> buscarPorFiltro(String descricao, String diretor, 
                                       Genero genero, Tipo tipo, Origem origem, 
                                       TipoMidia tipoMidia, Locacao locacao, 
                                       Sublocacao sublocacao, String estante,
                                       String estantePrateleira, String estantePrateleiraColuna) throws SQLException {
        
        Map<String, Object> filtros = new HashMap<>();
        
        if (descricao != null && !descricao.trim().isEmpty()) {
            filtros.put("descricao", descricao);
        }
        
        if (diretor != null && !diretor.trim().isEmpty()) {
            filtros.put("diretor", diretor);
        }
        
        if (genero != null) {
            filtros.put("genero", genero);
        }
        
        if (tipo != null) {
            filtros.put("tipo", tipo);
        }
        
        if (origem != null) {
            filtros.put("origem", origem);
        }
        
        if (tipoMidia != null) {
            filtros.put("tipoMidia", tipoMidia);
        }
        
        if (locacao != null) {
            filtros.put("locacao", locacao);
        }
        
        if (sublocacao != null) {
            filtros.put("sublocacao", sublocacao);
        }
        
        if (estante != null && !estante.trim().isEmpty()) {
            filtros.put("estante", estante);
        }
        
        if (estantePrateleira != null && !estantePrateleira.trim().isEmpty()) {
            filtros.put("estantePrateleira", estantePrateleira);
        }
        
        if (estantePrateleiraColuna != null && !estantePrateleiraColuna.trim().isEmpty()) {
            filtros.put("estantePrateleiraColuna", estantePrateleiraColuna);
        }
        
        return filmeDAO.findByFilter(filtros);
    }
}