package com.moviemanager.model.dao;

import com.moviemanager.model.*;
import com.moviemanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FilmeDAO {
    
    public Filme save(Filme filme) throws SQLException {
        String sql;
        
        if (filme.getId() == null) {
            // Insert
            sql = "INSERT INTO filmes (descricao, diretor, genero, tipo, origem, tipo_midia, " +
                  "locacao, sublocacao, estante, estante_prateleira, estante_prateleira_coluna) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
                  
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                int i = 1;
                stmt.setString(i++, filme.getDescricao());
                stmt.setString(i++, filme.getDiretor());
                stmt.setString(i++, filme.getGenero() != null ? filme.getGenero().name() : null);
                stmt.setString(i++, filme.getTipo() != null ? filme.getTipo().name() : null);
                stmt.setString(i++, filme.getOrigem() != null ? filme.getOrigem().name() : null);
                stmt.setString(i++, filme.getTipoMidia() != null ? filme.getTipoMidia().name() : null);
                stmt.setString(i++, filme.getLocacao() != null ? filme.getLocacao().name() : null);
                stmt.setString(i++, filme.getSublocacao() != null ? filme.getSublocacao().name() : null);
                stmt.setString(i++, filme.getEstante());
                stmt.setString(i++, filme.getEstantePrateleira());
                stmt.setString(i++, filme.getEstantePrateleiraColuna());
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    filme.setId(rs.getLong("id"));
                }
            }
        } else {
            // Update
            sql = "UPDATE filmes SET descricao = ?, diretor = ?, genero = ?, tipo = ?, " +
                  "origem = ?, tipo_midia = ?, locacao = ?, sublocacao = ?, estante = ?, " +
                  "estante_prateleira = ?, estante_prateleira_coluna = ? WHERE id = ?";
                  
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                int i = 1;
                stmt.setString(i++, filme.getDescricao());
                stmt.setString(i++, filme.getDiretor());
                stmt.setString(i++, filme.getGenero() != null ? filme.getGenero().name() : null);
                stmt.setString(i++, filme.getTipo() != null ? filme.getTipo().name() : null);
                stmt.setString(i++, filme.getOrigem() != null ? filme.getOrigem().name() : null);
                stmt.setString(i++, filme.getTipoMidia() != null ? filme.getTipoMidia().name() : null);
                stmt.setString(i++, filme.getLocacao() != null ? filme.getLocacao().name() : null);
                stmt.setString(i++, filme.getSublocacao() != null ? filme.getSublocacao().name() : null);
                stmt.setString(i++, filme.getEstante());
                stmt.setString(i++, filme.getEstantePrateleira());
                stmt.setString(i++, filme.getEstantePrateleiraColuna());
                stmt.setLong(i++, filme.getId());
                
                stmt.executeUpdate();
            }
        }
        
        return filme;
    }
    
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    
    public Filme findById(Long id) throws SQLException {
        String sql = "SELECT * FROM filmes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToFilme(rs);
            }
        }
        
        return null;
    }
    
    public List<Filme> findAll() throws SQLException {
        return findByFilter(new HashMap<>());
    }
    
    public List<Filme> findByFilter(Map<String, Object> filters) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM filmes WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        // Adiciona filtros dinâmicos
        if (filters.containsKey("descricao") && filters.get("descricao") != null) {
            sql.append(" AND LOWER(descricao) LIKE LOWER(?)");
            params.add("%" + filters.get("descricao") + "%");
        }
        
        if (filters.containsKey("diretor") && filters.get("diretor") != null) {
            sql.append(" AND LOWER(diretor) LIKE LOWER(?)");
            params.add("%" + filters.get("diretor") + "%");
        }
        
        if (filters.containsKey("genero") && filters.get("genero") != null) {
            sql.append(" AND genero = ?");
            params.add(((Genero) filters.get("genero")).name());
        }
        
        if (filters.containsKey("tipo") && filters.get("tipo") != null) {
            sql.append(" AND tipo = ?");
            params.add(((Tipo) filters.get("tipo")).name());
        }
        
        if (filters.containsKey("origem") && filters.get("origem") != null) {
            sql.append(" AND origem = ?");
            params.add(((Origem) filters.get("origem")).name());
        }
        
        if (filters.containsKey("tipoMidia") && filters.get("tipoMidia") != null) {
            sql.append(" AND tipo_midia = ?");
            params.add(((TipoMidia) filters.get("tipoMidia")).name());
        }
        
        if (filters.containsKey("locacao") && filters.get("locacao") != null) {
            sql.append(" AND locacao = ?");
            params.add(((Locacao) filters.get("locacao")).name());
        }
        
        if (filters.containsKey("sublocacao") && filters.get("sublocacao") != null) {
            sql.append(" AND sublocacao = ?");
            params.add(((Sublocacao) filters.get("sublocacao")).name());
        }
        
        if (filters.containsKey("estante") && filters.get("estante") != null) {
            sql.append(" AND LOWER(estante) LIKE LOWER(?)");
            params.add("%" + filters.get("estante") + "%");
        }
        
        if (filters.containsKey("estantePrateleira") && filters.get("estantePrateleira") != null) {
            sql.append(" AND LOWER(estante_prateleira) LIKE LOWER(?)");
            params.add("%" + filters.get("estantePrateleira") + "%");
        }
        
        if (filters.containsKey("estantePrateleiraColuna") && filters.get("estantePrateleiraColuna") != null) {
            sql.append(" AND LOWER(estante_prateleira_coluna) LIKE LOWER(?)");
            params.add("%" + filters.get("estantePrateleiraColuna") + "%");
        }
        
        sql.append(" ORDER BY id");
        
        List<Filme> filmes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            // Define os parâmetros
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                filmes.add(mapResultSetToFilme(rs));
            }
        }
        
        return filmes;
    }
    
    private Filme mapResultSetToFilme(ResultSet rs) throws SQLException {
        Filme filme = new Filme();
        filme.setId(rs.getLong("id"));
        filme.setDescricao(rs.getString("descricao"));
        filme.setDiretor(rs.getString("diretor"));
        
        String generoStr = rs.getString("genero");
        if (generoStr != null) {
            filme.setGenero(Genero.valueOf(generoStr));
        }
        
        String tipoStr = rs.getString("tipo");
        if (tipoStr != null) {
            filme.setTipo(Tipo.valueOf(tipoStr));
        }
        
        String origemStr = rs.getString("origem");
        if (origemStr != null) {
            filme.setOrigem(Origem.valueOf(origemStr));
        }
        
        String tipoMidiaStr = rs.getString("tipo_midia");
        if (tipoMidiaStr != null) {
            filme.setTipoMidia(TipoMidia.valueOf(tipoMidiaStr));
        }
        
        String locacaoStr = rs.getString("locacao");
        if (locacaoStr != null) {
            filme.setLocacao(Locacao.valueOf(locacaoStr));
        }
        
        String sublocacaoStr = rs.getString("sublocacao");
        if (sublocacaoStr != null) {
            filme.setSublocacao(Sublocacao.valueOf(sublocacaoStr));
        }
        
        filme.setEstante(rs.getString("estante"));
        filme.setEstantePrateleira(rs.getString("estante_prateleira"));
        filme.setEstantePrateleiraColuna(rs.getString("estante_prateleira_coluna"));
        
        return filme;
    }
}