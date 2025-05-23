package com.moviemanager.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.moviemanager.controller.FilmeController;
import com.moviemanager.model.*;

import com.moviemanager.util.ExcelExporter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {
    private final FilmeController controller;
    
    // Componentes de filtro
    private final JTextField txtDescricaoFiltro;
    private final JTextField txtDiretorFiltro;
    private final JComboBox<ComboItem<Genero>> cmbGeneroFiltro;
    private final JComboBox<ComboItem<Tipo>> cmbTipoFiltro;
    private final JComboBox<ComboItem<Origem>> cmbOrigemFiltro;
    private final JComboBox<ComboItem<TipoMidia>> cmbTipoMidiaFiltro;
    private final JComboBox<ComboItem<Locacao>> cmbLocacaoFiltro;
    private final JComboBox<ComboItem<Sublocacao>> cmbSublocacaoFiltro;
    private final JTextField txtEstanteFiltro;
    private final JTextField txtEstantePrateleiraFiltro;
    private final JTextField txtEstantePrateleiraColunaFiltro;

    private final JLabel lblContagem;

    // Componentes da tabela
    private final JTable tblFilmes;
    private final FilmeTableModel tableModel;
    
    // Botões de ação
    private final JButton btnAdicionar;
    private final JButton btnEditar;
    private final JButton btnExcluir;
    private final JButton btnFiltrar;
    private final JButton btnLimparFiltros;
    private final JButton btnExportar;


    public MainFrame() {
        // Configura o look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicializa o controlador
        controller = new FilmeController();
        
        // Configura a janela principal
        setTitle("Gerenciador de Filmes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(1200, 700);
        //setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Inicializa componentes
        txtDescricaoFiltro = new JTextField(15);
        txtDiretorFiltro = new JTextField(15);
        
        cmbGeneroFiltro = new JComboBox<>();
        cmbGeneroFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Genero genero : Genero.values()) {
            cmbGeneroFiltro.addItem(new ComboItem<>(genero, genero.getDescricao()));
        }
        
        cmbTipoFiltro = new JComboBox<>();
        cmbTipoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Tipo tipo : Tipo.values()) {
            cmbTipoFiltro.addItem(new ComboItem<>(tipo, tipo.getDescricao()));
        }
        
        cmbOrigemFiltro = new JComboBox<>();
        cmbOrigemFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Origem origem : Origem.values()) {
            cmbOrigemFiltro.addItem(new ComboItem<>(origem, origem.getDescricao()));
        }
        
        cmbTipoMidiaFiltro = new JComboBox<>();
        cmbTipoMidiaFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (TipoMidia tipoMidia : TipoMidia.values()) {
            cmbTipoMidiaFiltro.addItem(new ComboItem<>(tipoMidia, tipoMidia.getDescricao()));
        }
        
        cmbLocacaoFiltro = new JComboBox<>();
        cmbLocacaoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Locacao locacao : Locacao.values()) {
            cmbLocacaoFiltro.addItem(new ComboItem<>(locacao, locacao.getDescricao()));
        }
        
        cmbSublocacaoFiltro = new JComboBox<>();
        cmbSublocacaoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Sublocacao sublocacao : Sublocacao.values()) {
            cmbSublocacaoFiltro.addItem(new ComboItem<>(sublocacao, sublocacao.getDescricao()));
        }
        
        txtEstanteFiltro = new JTextField(10);
        txtEstantePrateleiraFiltro = new JTextField(10);
        txtEstantePrateleiraColunaFiltro = new JTextField(10);

        lblContagem = new JLabel("Total: 0 filmes");

        // Inicializa a tabela
        tableModel = new FilmeTableModel();
        tblFilmes = new JTable(tableModel);
        tblFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFilmes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblFilmes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblFilmes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblFilmes.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblFilmes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(7).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(8).setPreferredWidth(120);
        tblFilmes.getColumnModel().getColumn(9).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(10).setPreferredWidth(120);
        tblFilmes.getColumnModel().getColumn(11).setPreferredWidth(150);
        
        // Inicializa os botões
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnFiltrar = new JButton("Filtrar");
        btnLimparFiltros = new JButton("Limpar Filtros");
        btnExportar = new JButton("Exportar para Excel");


        // Layout da tela
        setLayout(new BorderLayout());
        
        // Painel de filtros (20% superior)
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(new TitledBorder("Filtros"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Primeira linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFiltros.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        panelFiltros.add(txtDescricaoFiltro, gbc);
        
        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Diretor:"), gbc);
        
        gbc.gridx = 3;
        panelFiltros.add(txtDiretorFiltro, gbc);
        
        gbc.gridx = 4;
        panelFiltros.add(new JLabel("Gênero:"), gbc);
        
        gbc.gridx = 5;
        panelFiltros.add(cmbGeneroFiltro, gbc);
        
        // Segunda linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFiltros.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        panelFiltros.add(cmbTipoFiltro, gbc);
        
        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Origem:"), gbc);
        
        gbc.gridx = 3;
        panelFiltros.add(cmbOrigemFiltro, gbc);
        
        gbc.gridx = 4;
        panelFiltros.add(new JLabel("Tipo de Mídia:"), gbc);
        
        gbc.gridx = 5;
        panelFiltros.add(cmbTipoMidiaFiltro, gbc);
        
        // Terceira linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFiltros.add(new JLabel("Locação:"), gbc);
        
        gbc.gridx = 1;
        panelFiltros.add(cmbLocacaoFiltro, gbc);
        
        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Sublocação:"), gbc);
        
        gbc.gridx = 3;
        panelFiltros.add(cmbSublocacaoFiltro, gbc);
        
        gbc.gridx = 4;
        panelFiltros.add(new JLabel("Estante:"), gbc);
        
        gbc.gridx = 5;
        panelFiltros.add(txtEstanteFiltro, gbc);
        
        // Quarta linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFiltros.add(new JLabel("Estante Prateleira:"), gbc);
        
        gbc.gridx = 1;
        panelFiltros.add(txtEstantePrateleiraFiltro, gbc);
        
        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Estante Prat. Coluna:"), gbc);
        
        gbc.gridx = 3;
        panelFiltros.add(txtEstantePrateleiraColunaFiltro, gbc);
        
        // Botões de filtro
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel panelBotoesFiltro = new JPanel();
        panelBotoesFiltro.add(btnFiltrar);
        panelBotoesFiltro.add(btnLimparFiltros);
        panelBotoesFiltro.add(btnExportar);
        panelBotoesFiltro.add(Box.createHorizontalStrut(20)); // Espaçamento
        panelBotoesFiltro.add(lblContagem);
        panelFiltros.add(panelBotoesFiltro, gbc);


        // Painel da tabela (60% meio)
        JScrollPane scrollPane = new JScrollPane(tblFilmes);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Painel de botões (20% inferior)
        JPanel panelBotoes = new JPanel();
        panelBotoes.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        
        // Adiciona os painéis ao frame
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Configura os listeners
        configureListeners();
        
        // Carrega os dados iniciais
        carregarDados();
    }
    
    private void configureListeners() {
        // Ação do botão Adicionar
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarFilme();
            }
        });
        
        // Ação do botão Editar
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarFilme();
            }
        });
        
        // Ação do botão Excluir
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirFilme();
            }
        });
        
        // Ação do botão Filtrar
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });
        
        // Ação do botão Limpar Filtros
        btnLimparFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparFiltros();
            }
        });
        
        // Duplo clique na tabela para editar
        tblFilmes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarFilme();
                }
            }
        });

        // Botão de Exportação
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salvar como Excel");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos Excel (*.xlsx)", "xlsx"));

                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

                    try {
                        List<Filme> filmes = tableModel.getFilmes();
                        ExcelExporter.exportToExcel(filmes, filePath);
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Dados exportados com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro ao exportar dados: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
    
    private void carregarDados() {
        try {
            List<Filme> filmes = controller.buscarTodos();
            tableModel.atualizarDados(filmes);
            lblContagem.setText("Total: " + filmes.size() + " filmes");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void adicionarFilme() {
        FilmeFormDialog dialog = new FilmeFormDialog(this, "Adicionar Filme", true);
        dialog.setFilme(null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmou()) {
            try {
                controller.salvar(dialog.getFilme());
                JOptionPane.showMessageDialog(this,
                        "Filme adicionado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao adicionar filme: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void editarFilme() {
        int selectedRow = tblFilmes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um filme para editar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Filme filme = tableModel.getFilmeAt(selectedRow);
        FilmeFormDialog dialog = new FilmeFormDialog(this, "Editar Filme", true);
        dialog.setFilme(filme);
        dialog.setVisible(true);
        
        if (dialog.isConfirmou()) {
            try {
                controller.salvar(dialog.getFilme());
                JOptionPane.showMessageDialog(this,
                        "Filme atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao atualizar filme: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void excluirFilme() {
        int selectedRow = tblFilmes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um filme para excluir.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Filme filme = tableModel.getFilmeAt(selectedRow);
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o filme \"" + filme.getDescricao() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(filme.getId());
                JOptionPane.showMessageDialog(this,
                        "Filme excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao excluir filme: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void aplicarFiltros() {
        try {
            String descricao = txtDescricaoFiltro.getText().trim();
            if (descricao.isEmpty()) {
                descricao = null;
            }
            
            String diretor = txtDiretorFiltro.getText().trim();
            if (diretor.isEmpty()) {
                diretor = null;
            }
            
            Genero genero = ((ComboItem<Genero>) cmbGeneroFiltro.getSelectedItem()).getValue();
            Tipo tipo = ((ComboItem<Tipo>) cmbTipoFiltro.getSelectedItem()).getValue();
            Origem origem = ((ComboItem<Origem>) cmbOrigemFiltro.getSelectedItem()).getValue();
            TipoMidia tipoMidia = ((ComboItem<TipoMidia>) cmbTipoMidiaFiltro.getSelectedItem()).getValue();
            Locacao locacao = ((ComboItem<Locacao>) cmbLocacaoFiltro.getSelectedItem()).getValue();
            Sublocacao sublocacao = ((ComboItem<Sublocacao>) cmbSublocacaoFiltro.getSelectedItem()).getValue();
            
            String estante = txtEstanteFiltro.getText().trim();
            if (estante.isEmpty()) {
                estante = null;
            }
            
            String estantePrateleira = txtEstantePrateleiraFiltro.getText().trim();
            if (estantePrateleira.isEmpty()) {
                estantePrateleira = null;
            }
            
            String estantePrateleiraColuna = txtEstantePrateleiraColunaFiltro.getText().trim();
            if (estantePrateleiraColuna.isEmpty()) {
                estantePrateleiraColuna = null;
            }
            
            List<Filme> filmes = controller.buscarPorFiltro(
                    descricao, diretor, genero, tipo, origem, tipoMidia, locacao,
                    sublocacao, estante, estantePrateleira, estantePrateleiraColuna);
            
            tableModel.atualizarDados(filmes);
            lblContagem.setText("Total: " + filmes.size() + " filmes");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao aplicar filtros: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limparFiltros() {
        txtDescricaoFiltro.setText("");
        txtDiretorFiltro.setText("");
        cmbGeneroFiltro.setSelectedIndex(0);
        cmbTipoFiltro.setSelectedIndex(0);
        cmbOrigemFiltro.setSelectedIndex(0);
        cmbTipoMidiaFiltro.setSelectedIndex(0);
        cmbLocacaoFiltro.setSelectedIndex(0);
        cmbSublocacaoFiltro.setSelectedIndex(0);
        txtEstanteFiltro.setText("");
        txtEstantePrateleiraFiltro.setText("");
        txtEstantePrateleiraColunaFiltro.setText("");
        
        carregarDados();
    }
    
    // Classe auxiliar para ComboBox com valor + descrição
    private static class ComboItem<T> {
        private final T value;
        private final String label;
        
        public ComboItem(T value, String label) {
            this.value = value;
            this.label = label;
        }
        
        public T getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }
}