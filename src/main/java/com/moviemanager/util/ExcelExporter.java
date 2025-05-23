package com.moviemanager.util;

import com.moviemanager.model.Filme;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
  public static void exportToExcel(List<Filme> filmes, String filePath) throws IOException {
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Filmes");

      // Criar estilo para o cabeçalho
      CellStyle headerStyle = workbook.createCellStyle();
      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerStyle.setFont(headerFont);

      // Criar cabeçalho
      Row headerRow = sheet.createRow(0);
      String[] columns = {"ID", "Descrição", "Ano", "Diretor", "Gênero", "Tipo", "Origem",
          "Tipo de Mídia", "Locação", "Sublocação", "Estante",
          "Estante Prateleira", "Estante Prateleira Coluna"};

      for (int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        cell.setCellStyle(headerStyle);
      }

      // Preencher dados
      int rowNum = 1;
      for (Filme filme : filmes) {
        Row row = sheet.createRow(rowNum++);

        row.createCell(0).setCellValue(filme.getId() != null ? filme.getId().doubleValue() : 0.0);
        row.createCell(1).setCellValue(filme.getDescricao() != null ? filme.getDescricao() : "");
        row.createCell(2).setCellValue(filme.getAno() != null ? filme.getAno().doubleValue() : 0.0);
        row.createCell(3).setCellValue(filme.getDiretor() != null ? filme.getDiretor() : "");
        row.createCell(4).setCellValue(filme.getGenero() != null ? filme.getGenero().getDescricao() : "");
        row.createCell(5).setCellValue(filme.getTipo() != null ? filme.getTipo().getDescricao() : "");
        row.createCell(6).setCellValue(filme.getOrigem() != null ? filme.getOrigem().getDescricao() : "");
        row.createCell(7).setCellValue(filme.getTipoMidia() != null ? filme.getTipoMidia().getDescricao() : "");
        row.createCell(8).setCellValue(filme.getLocacao() != null ? filme.getLocacao().getDescricao() : "");
        row.createCell(9).setCellValue(filme.getSublocacao() != null ? filme.getSublocacao().getDescricao() : "");
        row.createCell(10).setCellValue(filme.getEstante() != null ? filme.getEstante() : "");
        row.createCell(11).setCellValue(filme.getEstantePrateleira() != null ? filme.getEstantePrateleira() : "");
        row.createCell(12).setCellValue(filme.getEstantePrateleiraColuna() != null ? filme.getEstantePrateleiraColuna() : "");
      }

      // Ajustar largura das colunas
      for (int i = 0; i < columns.length; i++) {
        sheet.autoSizeColumn(i);
      }

      // Salvar arquivo
      try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
        workbook.write(fileOut);
      }
    }
  }
}