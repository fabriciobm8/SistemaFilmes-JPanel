package com.moviemanager.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.moviemanager.controller.FilmeController;
import com.moviemanager.model.*;
import com.moviemanager.util.ExcelExporter;
import com.moviemanager.util.PdfExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {
    private final FilmeController controller;

    // Componentes de filtro
    private final JTextField txtDescricaoFiltro;
    private final JTextField txtAnoInicialFiltro;
    private final JTextField txtAnoFinalFiltro;
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
    private final JButton btnExportarPdf;

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
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Inicializa componentes
        txtDescricaoFiltro = new JTextField(12);
        txtAnoInicialFiltro = new JTextField(3);
        txtAnoFinalFiltro = new JTextField(3);
        txtDiretorFiltro = new JTextField(8);
        cmbGeneroFiltro = new JComboBox<>();
        cmbGeneroFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Genero genero : Genero.values()) {
            cmbGeneroFiltro.addItem(new ComboItem<>(genero, genero.getDescricao()));
        }
        cmbGeneroFiltro.setPreferredSize(new Dimension(80, cmbGeneroFiltro.getPreferredSize().height));
        cmbTipoFiltro = new JComboBox<>();
        cmbTipoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Tipo tipo : Tipo.values()) {
            cmbTipoFiltro.addItem(new ComboItem<>(tipo, tipo.getDescricao()));
        }
        cmbTipoFiltro.setPreferredSize(new Dimension(80, cmbTipoFiltro.getPreferredSize().height));
        cmbOrigemFiltro = new JComboBox<>();
        cmbOrigemFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Origem origem : Origem.values()) {
            cmbOrigemFiltro.addItem(new ComboItem<>(origem, origem.getDescricao()));
        }
        cmbOrigemFiltro.setPreferredSize(new Dimension(80, cmbOrigemFiltro.getPreferredSize().height));
        cmbTipoMidiaFiltro = new JComboBox<>();
        cmbTipoMidiaFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (TipoMidia tipoMidia : TipoMidia.values()) {
            cmbTipoMidiaFiltro.addItem(new ComboItem<>(tipoMidia, tipoMidia.getDescricao()));
        }
        cmbTipoMidiaFiltro.setPreferredSize(new Dimension(80, cmbTipoMidiaFiltro.getPreferredSize().height));
        cmbLocacaoFiltro = new JComboBox<>();
        cmbLocacaoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Locacao locacao : Locacao.values()) {
            cmbLocacaoFiltro.addItem(new ComboItem<>(locacao, locacao.getDescricao()));
        }
        cmbLocacaoFiltro.setPreferredSize(new Dimension(80, cmbLocacaoFiltro.getPreferredSize().height));
        cmbSublocacaoFiltro = new JComboBox<>();
        cmbSublocacaoFiltro.addItem(new ComboItem<>(null, "Todos"));
        for (Sublocacao sublocacao : Sublocacao.values()) {
            cmbSublocacaoFiltro.addItem(new ComboItem<>(sublocacao, sublocacao.getDescricao()));
        }
        cmbSublocacaoFiltro.setPreferredSize(new Dimension(80, cmbSublocacaoFiltro.getPreferredSize().height));
        txtEstanteFiltro = new JTextField(5);
        txtEstantePrateleiraFiltro = new JTextField(5);
        txtEstantePrateleiraColunaFiltro = new JTextField(5);

        lblContagem = new JLabel("Total: 0 filmes");

        // Inicializa a tabela
        tableModel = new FilmeTableModel();
        tblFilmes = new JTable(tableModel);
        tblFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFilmes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblFilmes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblFilmes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblFilmes.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblFilmes.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblFilmes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(7).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(8).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(9).setPreferredWidth(120);
        tblFilmes.getColumnModel().getColumn(10).setPreferredWidth(100);
        tblFilmes.getColumnModel().getColumn(11).setPreferredWidth(120);
        tblFilmes.getColumnModel().getColumn(12).setPreferredWidth(150);

        // Inicializa os botões
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnFiltrar = new JButton("Filtrar");
        btnLimparFiltros = new JButton("Limpar Filtros");
        btnExportar = new JButton("Exportar para Excel");
        btnExportarPdf = new JButton("Exportar para PDF");

        // Layout da tela
        setLayout(new BorderLayout());

        // Painel de filtros (20% superior)
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 2, 5, 2);
        gbc.anchor = GridBagConstraints.WEST;

        // Primeira linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtDescricaoFiltro, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Ano Inicial:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtAnoInicialFiltro, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Ano Final:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtAnoFinalFiltro, gbc);

        // Segunda linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Diretor:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtDiretorFiltro, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Gênero:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbGeneroFiltro, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbTipoFiltro, gbc);

        // Terceira linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Origem:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbOrigemFiltro, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Tipo de Mídia:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbTipoMidiaFiltro, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Locação:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbLocacaoFiltro, gbc);

        // Quarta linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Sublocação:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(cmbSublocacaoFiltro, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Estante:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtEstanteFiltro, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Estante Prateleira:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtEstantePrateleiraFiltro, gbc);

        // Quinta linha de filtros
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFiltros.add(new JLabel("Estante Prat. Coluna:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(txtEstantePrateleiraColunaFiltro, gbc);

        // Botões de filtro
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel panelBotoesFiltro = new JPanel();
        panelBotoesFiltro.add(btnFiltrar);
        panelBotoesFiltro.add(btnLimparFiltros);
        panelBotoesFiltro.add(btnExportar);
        panelBotoesFiltro.add(btnExportarPdf);
        panelBotoesFiltro.add(Box.createHorizontalStrut(20));
        panelBotoesFiltro.add(lblContagem);
        panelFiltros.add(panelBotoesFiltro, gbc);

        // Filler para espaço extra
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFiltros.add(Box.createHorizontalGlue(), gbc);

        // Painel para combinar imagem e filtros
        JPanel panelFiltroComImagem = new JPanel(new BorderLayout());
        panelFiltroComImagem.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Adiciona a imagem à esquerda
        JLabel lblImagem = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.png"));
            Image img = icon.getImage();
            lblImagem.setIcon(new ImageIcon(img));
            lblImagem.setBorder(new EmptyBorder(5, 0, 5, 5));
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            lblImagem.setText("Imagem não encontrada");
        }
        panelFiltroComImagem.add(lblImagem, BorderLayout.WEST);

        // Painel para filtros com título
        JPanel panelFiltrosComBorda = new JPanel(new GridBagLayout());
        panelFiltrosComBorda.setBorder(new TitledBorder("Filtros"));
        GridBagConstraints gbcFiltros = new GridBagConstraints();
        gbcFiltros.anchor = GridBagConstraints.WEST;
        gbcFiltros.fill = GridBagConstraints.HORIZONTAL;
        gbcFiltros.weightx = 1.0;
        gbcFiltros.gridx = 0;
        gbcFiltros.gridy = 0;
        gbcFiltros.insets = new Insets(0, 0, 0, 0);
        panelFiltrosComBorda.add(panelFiltros, gbcFiltros);

        panelFiltroComImagem.add(panelFiltrosComBorda, BorderLayout.CENTER);

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
        add(panelFiltroComImagem, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        // Configura os listeners
        configureListeners();

        // Carrega os dados iniciais
        carregarDados();
    }

    private void configureListeners() {
        btnAdicionar.addActionListener(e -> adicionarFilme());
        btnEditar.addActionListener(e -> editarFilme());
        btnExcluir.addActionListener(e -> excluirFilme());
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        btnLimparFiltros.addActionListener(e -> limparFiltros());
        tblFilmes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarFilme();
                }
            }
        });
        btnExportar.addActionListener(e -> {
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
                    JOptionPane.showMessageDialog(MainFrame.this, "Dados exportados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro ao exportar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        btnExportarPdf.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar como PDF");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF (*.pdf)", "pdf"));
            if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }
                try {
                    List<Filme> filmes = tableModel.getFilmes();
                    int totalFilmes = filmes.size();
                    String filtrosAplicados = construirFiltrosAplicados();
                    PdfExporter.exportToPdf(filmes, filePath, filtrosAplicados, totalFilmes);
                    JOptionPane.showMessageDialog(MainFrame.this, "Relatório PDF gerado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro ao gerar PDF: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private String construirFiltrosAplicados() {
        List<String> filtros = new ArrayList<>();

        String descricao = txtDescricaoFiltro.getText().trim();
        if (!descricao.isEmpty()) {
            filtros.add("Descrição: '" + descricao + "'");
        }

        String anoInicial = txtAnoInicialFiltro.getText().trim();
        if (!anoInicial.isEmpty()) {
            filtros.add("Ano Inicial: " + anoInicial);
        }

        String anoFinal = txtAnoFinalFiltro.getText().trim();
        if (!anoFinal.isEmpty()) {
            filtros.add("Ano Final: " + anoFinal);
        }

        String diretor = txtDiretorFiltro.getText().trim();
        if (!diretor.isEmpty()) {
            filtros.add("Diretor: '" + diretor + "'");
        }

        ComboItem<Genero> generoItem = (ComboItem<Genero>) cmbGeneroFiltro.getSelectedItem();
        if (generoItem.getValue() != null) {
            filtros.add("Gênero: " + generoItem.getValue().getDescricao());
        }

        ComboItem<Tipo> tipoItem = (ComboItem<Tipo>) cmbTipoFiltro.getSelectedItem();
        if (tipoItem.getValue() != null) {
            filtros.add("Tipo: " + tipoItem.getValue().getDescricao());
        }

        ComboItem<Origem> origemItem = (ComboItem<Origem>) cmbOrigemFiltro.getSelectedItem();
        if (origemItem.getValue() != null) {
            filtros.add("Origem: " + origemItem.getValue().getDescricao());
        }

        ComboItem<TipoMidia> tipoMidiaItem = (ComboItem<TipoMidia>) cmbTipoMidiaFiltro.getSelectedItem();
        if (tipoMidiaItem.getValue() != null) {
            filtros.add("Tipo de Mídia: " + tipoMidiaItem.getValue().getDescricao());
        }

        ComboItem<Locacao> locacaoItem = (ComboItem<Locacao>) cmbLocacaoFiltro.getSelectedItem();
        if (locacaoItem.getValue() != null) {
            filtros.add("Locação: " + locacaoItem.getValue().getDescricao());
        }

        ComboItem<Sublocacao> sublocacaoItem = (ComboItem<Sublocacao>) cmbSublocacaoFiltro.getSelectedItem();
        if (sublocacaoItem.getValue() != null) {
            filtros.add("Sublocação: " + sublocacaoItem.getValue().getDescricao());
        }

        String estante = txtEstanteFiltro.getText().trim();
        if (!estante.isEmpty()) {
            filtros.add("Estante: '" + estante + "'");
        }

        String estantePrateleira = txtEstantePrateleiraFiltro.getText().trim();
        if (!estantePrateleira.isEmpty()) {
            filtros.add("Estante Prateleira: '" + estantePrateleira + "'");
        }

        String estantePrateleiraColuna = txtEstantePrateleiraColunaFiltro.getText().trim();
        if (!estantePrateleiraColuna.isEmpty()) {
            filtros.add("Estante Prateleira Coluna: '" + estantePrateleiraColuna + "'");
        }

        if (filtros.isEmpty()) {
            return "Nenhum (Lista Completa)";
        } else {
            return String.join(", ", filtros);
        }
    }

    private void carregarDados() {
        try {
            List<Filme> filmes = controller.buscarTodos();
            tableModel.atualizarDados(filmes);
            lblContagem.setText("Total: " + filmes.size() + " filmes");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Filme adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar filme: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void editarFilme() {
        int selectedRow = tblFilmes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um filme para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Filme filme = tableModel.getFilmeAt(selectedRow);
        FilmeFormDialog dialog = new FilmeFormDialog(this, "Editar Filme", true);
        dialog.setFilme(filme);
        dialog.setVisible(true);
        if (dialog.isConfirmou()) {
            try {
                controller.salvar(dialog.getFilme());
                JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar filme: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void excluirFilme() {
        int selectedRow = tblFilmes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um filme para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Filme filme = tableModel.getFilmeAt(selectedRow);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o filme \"" + filme.getDescricao() + "\"?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(filme.getId());
                JOptionPane.showMessageDialog(this, "Filme excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir filme: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void aplicarFiltros() {
        try {
            String descricao = txtDescricaoFiltro.getText().trim();
            if (descricao.isEmpty()) {
                descricao = null;
            }
            Integer anoInicial = null;
            String anoInicialText = txtAnoInicialFiltro.getText().trim();
            if (!anoInicialText.isEmpty()) {
                try {
                    anoInicial = Integer.parseInt(anoInicialText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Ano Inicial deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Integer anoFinal = null;
            String anoFinalText = txtAnoFinalFiltro.getText().trim();
            if (!anoFinalText.isEmpty()) {
                try {
                    anoFinal = Integer.parseInt(anoFinalText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Ano Final deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
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
            List<Filme> filmes = controller.buscarPorFiltro(descricao, anoInicial, anoFinal, diretor, genero, tipo, origem, tipoMidia, locacao, sublocacao, estante, estantePrateleira, estantePrateleiraColuna);
            tableModel.atualizarDados(filmes);
            lblContagem.setText("Total: " + filmes.size() + " filmes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao aplicar filtros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparFiltros() {
        txtDescricaoFiltro.setText("");
        txtAnoInicialFiltro.setText("");
        txtAnoFinalFiltro.setText("");
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