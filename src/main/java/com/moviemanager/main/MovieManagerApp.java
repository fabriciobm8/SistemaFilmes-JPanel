package com.moviemanager.main;

import com.moviemanager.util.DatabaseConnection;
import com.moviemanager.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MovieManagerApp {
    
    public static void main(String[] args) {
        // Define o look and feel no Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Configura o frame principal
                MainFrame mainFrame = new MainFrame();

                // ADICIONE AQUI O ÍCONE
                try {
                    // Usando ClassLoader
                    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/filme.png"));
                    mainFrame.setIconImage(icon.getImage());
                } catch (Exception iconException) {
                    System.out.println("Não foi possível carregar o ícone: " + iconException.getMessage());
                }
                
                // Centraliza na tela
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int width = 1200;
                int height = 700;
                int x = (screenSize.width - width) / 2;
                int y = (screenSize.height - height) / 2;
                mainFrame.setBounds(x, y, width, height);
                
                // Exibe o frame
                mainFrame.setVisible(true);
                
                // Configura o encerramento da aplicação
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        // Fecha a conexão com o banco de dados
                        DatabaseConnection.closeDataSource();
                    }
                });
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Erro ao iniciar a aplicação: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}