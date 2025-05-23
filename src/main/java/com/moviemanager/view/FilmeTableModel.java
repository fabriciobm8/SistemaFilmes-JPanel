package com.moviemanager.view;

import com.moviemanager.model.Filme;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FilmeTableModel extends AbstractTableModel {
    private final List<Filme> filmes;
    private final String[] colunas = {
        "ID", "Descrição", "Ano", "Diretor", "Gênero", "Tipo", "Origem",
        "Tipo de Mídia", "Locação", "Sublocação", "Estante",
        "Estante Prateleira", "Estante Prateleira Coluna"
    };

    public FilmeTableModel() {
        this.filmes = new ArrayList<>();
    }

    public FilmeTableModel(List<Filme> filmes) {
        this.filmes = new ArrayList<>(filmes);
    }

    @Override
    public int getRowCount() {
        return filmes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Long.class;
        }
        if (columnIndex == 2) {
            return Integer.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Filme filme = filmes.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> filme.getId();
            case 1 -> filme.getDescricao();
            case 2 -> filme.getAno() != null ? filme.getAno() : "";
            case 3 -> filme.getDiretor();
            case 4 -> filme.getGenero() != null ? filme.getGenero().getDescricao() : "";
            case 5 -> filme.getTipo() != null ? filme.getTipo().getDescricao() : "";
            case 6 -> filme.getOrigem() != null ? filme.getOrigem().getDescricao() : "";
            case 7 -> filme.getTipoMidia() != null ? filme.getTipoMidia().getDescricao() : "";
            case 8 -> filme.getLocacao() != null ? filme.getLocacao().getDescricao() : "";
            case 9 -> filme.getSublocacao() != null ? filme.getSublocacao().getDescricao() : "";
            case 10 -> filme.getEstante();
            case 11 -> filme.getEstantePrateleira();
            case 12 -> filme.getEstantePrateleiraColuna();
            default -> null;
        };
    }

    public void atualizarDados(List<Filme> filmes) {
        this.filmes.clear();
        this.filmes.addAll(filmes);
        fireTableDataChanged();
    }

    public Filme getFilmeAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < filmes.size()) {
            return filmes.get(rowIndex);
        }
        return null;
    }

    public List<Filme> getFilmes() {
        return new ArrayList<>(filmes); // Retorna uma cópia para evitar alterações externas
    }
}