package com.moviemanager.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static HikariDataSource dataSource;
    
    static {
        try {
            initializeDataSource();
            createTablesIfNotExist();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar conexão com banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getDbUrl());
        config.setUsername(DatabaseConfig.getDbUser());
        config.setPassword(DatabaseConfig.getDbPassword());
        config.setMaximumPoolSize(DatabaseConfig.getMaxPoolSize());
        
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        dataSource = new HikariDataSource(config);
    }
    
    private static void createTablesIfNotExist() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Cria a tabela de filmes se não existir
            String createTableSQL = "CREATE TABLE IF NOT EXISTS filmes (" +
                    "id SERIAL PRIMARY KEY, " +
                    "descricao VARCHAR(255) NOT NULL, " +
                    "diretor VARCHAR(255), " +
                    "genero VARCHAR(50), " +
                    "tipo VARCHAR(50), " +
                    "origem VARCHAR(50), " +
                    "tipo_midia VARCHAR(50), " +
                    "locacao VARCHAR(50), " +
                    "sublocacao VARCHAR(50), " +
                    "estante VARCHAR(100), " +
                    "estante_prateleira VARCHAR(100), " +
                    "estante_prateleira_coluna VARCHAR(100)" +
                    ")";
            
            stmt.execute(createTableSQL);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}