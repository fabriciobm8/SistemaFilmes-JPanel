package com.moviemanager.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";
    
    static {
        try {
            // Primeiro tenta carregar do arquivo de configuração
            try {
                properties.load(new FileInputStream(CONFIG_FILE));
            } catch (IOException e) {
                // Se não encontrar o arquivo, usa valores padrão
                properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/filmes");
                properties.setProperty("db.user", "postgres");
                properties.setProperty("db.password", "root");
                properties.setProperty("db.maxPoolSize", "10");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar configurações: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }
    
    public static String getDbUser() {
        return properties.getProperty("db.user");
    }
    
    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
    
    public static int getMaxPoolSize() {
        return Integer.parseInt(properties.getProperty("db.maxPoolSize", "10"));
    }
}