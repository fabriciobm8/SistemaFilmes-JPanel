package com.moviemanager.view;

import com.moviemanager.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilmeFormDialog extends JDialog {
    private final JTextField txtDescricao;
    private final JTextField txtDiretor;
    private final JComboBox<Genero> cmbGenero;
    private final JComboBox<Tipo> cmbTipo;
    private final JComboBox<Origem> cmbOrigem;
    private final JComboBox<TipoMidia> cmbTipoMidia;
    private final JComboBox<Locacao> cmbLocacao;
    private final JComboBox<Sublocacao> cmbSublocacao;
    private final JTextField txtEstante;
    private final JTextField txtEstantePrateleira;
    private final JTextField txtEstantePrateleiraColuna;
    
    private Filme filme;
    private boolean confirmou = false;
    
    public FilmeFormDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        
        // Inicializa componentes
        txtDescricao = new JTextField(30);
        txtDiretor = new JTextField(30);
        cmbGenero = new JComboBox<>(Genero.values());
        cmbTipo = new JComboBox<>(Tipo.values());
        cmbOrigem = new JComboBox<>(Origem.values());
        cmbTipoMidia = new JComboBox<>(TipoMidia.values());
        cmbLocacao = new JComboBox<>(Locacao.values());
        cmbSublocacao = new JComboBox<>(Sublocacao.values());
        txtEstante = new JTextField(10);
        txtEstantePrateleira = new JTextField(10);
        txtEstantePrateleiraColuna = new JTextField(10);
        
        // Configuração da UI
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Primeira coluna (labels)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        
        panel.add(new JLabel("Descrição:*"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Diretor:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Gênero:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Origem:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Tipo de Mídia:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Locação:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Sublocação:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Estante:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Estante Prateleira:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Estante Prateleira Coluna:"), gbc);
        
        // Segunda coluna (campos)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        
        panel.add(txtDescricao, gbc);
        gbc.gridy++;
        panel.add(txtDiretor, gbc);
        gbc.gridy++;
        panel.add(cmbGenero, gbc);
        gbc.gridy++;
        panel.add(cmbTipo, gbc);
        gbc.gridy++;
        panel.add(cmbOrigem, gbc);
        gbc.gridy++;
        panel.add(cmbTipoMidia, gbc);
        gbc.gridy++;
        panel.add(cmbLocacao, gbc);
        gbc.gridy++;
        panel.add(cmbSublocacao, gbc);
        gbc.gridy++;
        panel.add(txtEstante, gbc);
        gbc.gridy++;
        panel.add(txtEstantePrateleira, gbc);
        gbc.gridy++;
        panel.add(txtEstantePrateleiraColuna, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        // Adiciona os botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        // Adiciona o painel ao diálogo
        getContentPane().add(panel);
        
        // Configura ações dos botões
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    confirmar();
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
        
        // Configurações finais do diálogo
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }
    
    public void setFilme(Filme filme) {
        this.filme = filme;
        
        if (filme != null) {
            // Preenche o formulário com os dados do filme
            txtDescricao.setText(filme.getDescricao());
            txtDiretor.setText(filme.getDiretor());
            cmbGenero.setSelectedItem(filme.getGenero());
            cmbTipo.setSelectedItem(filme.getTipo());
            cmbOrigem.setSelectedItem(filme.getOrigem());
            cmbTipoMidia.setSelectedItem(filme.getTipoMidia());
            cmbLocacao.setSelectedItem(filme.getLocacao());
            cmbSublocacao.setSelectedItem(filme.getSublocacao());
            txtEstante.setText(filme.getEstante());
            txtEstantePrateleira.setText(filme.getEstantePrateleira());
            txtEstantePrateleiraColuna.setText(filme.getEstantePrateleiraColuna());
        } else {
            // Limpa o formulário para um novo filme
            txtDescricao.setText("");
            txtDiretor.setText("");
            cmbGenero.setSelectedIndex(0);
            cmbTipo.setSelectedIndex(0);
            cmbOrigem.setSelectedIndex(0);
            cmbTipoMidia.setSelectedIndex(0);
            cmbLocacao.setSelectedIndex(0);
            cmbSublocacao.setSelectedIndex(0);
            txtEstante.setText("");
            txtEstantePrateleira.setText("");
            txtEstantePrateleiraColuna.setText("");
            
            this.filme = new Filme();
        }
    }
    
    public Filme getFilme() {
        return filme;
    }
    
    public boolean isConfirmou() {
        return confirmou;
    }
    
    private void confirmar() {
        // Atualiza o objeto filme com os dados do formulário
        filme.setDescricao(txtDescricao.getText().trim());
        filme.setDiretor(txtDiretor.getText().trim());
        filme.setGenero((Genero) cmbGenero.getSelectedItem());
        filme.setTipo((Tipo) cmbTipo.getSelectedItem());
        filme.setOrigem((Origem) cmbOrigem.getSelectedItem());
        filme.setTipoMidia((TipoMidia) cmbTipoMidia.getSelectedItem());
        filme.setLocacao((Locacao) cmbLocacao.getSelectedItem());
        filme.setSublocacao((Sublocacao) cmbSublocacao.getSelectedItem());
        filme.setEstante(txtEstante.getText().trim());
        filme.setEstantePrateleira(txtEstantePrateleira.getText().trim());
        filme.setEstantePrateleiraColuna(txtEstantePrateleiraColuna.getText().trim());
        
        confirmou = true;
        dispose();
    }
    
    private void cancelar() {
        confirmou = false;
        dispose();
    }
    
    private boolean validarCampos() {
        if (txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "O campo Descrição é obrigatório.", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
            txtDescricao.requestFocus();
            return false;
        }
        
        return true;
    }
}