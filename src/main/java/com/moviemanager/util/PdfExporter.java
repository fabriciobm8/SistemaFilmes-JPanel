package com.moviemanager.util;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.moviemanager.model.Filme;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfExporter {
  public static void exportToPdf(List<Filme> filmes, String filePath, String filtrosAplicados, int totalFilmes) throws IOException {
    // Criar arquivo temporário para o conteúdo sem números de página
    String tempFilePath = filePath + ".tmp";
    PdfWriter writer = new PdfWriter(tempFilePath);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    // Título
    Paragraph title = new Paragraph("Relatório de Filmes")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(16)
            .setBold();
    document.add(title);

    // Data
    String data = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
    Paragraph dataParagraph = new Paragraph("Gerado em: " + data)
            .setTextAlignment(TextAlignment.LEFT)
            .setFontSize(12)
            .setMarginTop(5);
    document.add(dataParagraph);

    // Contagem
    Paragraph contagemParagraph = new Paragraph("Total: " + totalFilmes + " filme" + (totalFilmes == 1 ? "" : "s"))
            .setTextAlignment(TextAlignment.LEFT)
            .setFontSize(12)
            .setMarginTop(5);
    document.add(contagemParagraph);

    // Filtros
    Paragraph filtrosParagraph = new Paragraph("Filtros: " + filtrosAplicados)
            .setTextAlignment(TextAlignment.LEFT)
            .setFontSize(12)
            .setMarginTop(5)
            .setMarginBottom(10);
    document.add(filtrosParagraph);

    // Tabela
    float[] columnWidths = {200, 60, 150, 100, 100};
    Table table = new Table(columnWidths);
    table.setWidth(510);

    // Cabeçalhos (com negrito)
    table.addHeaderCell(new Cell().add(new Paragraph("Descrição")).setBold());
    table.addHeaderCell(new Cell().add(new Paragraph("Ano")).setBold());
    table.addHeaderCell(new Cell().add(new Paragraph("Diretor")).setBold());
    table.addHeaderCell(new Cell().add(new Paragraph("Gênero")).setBold());
    table.addHeaderCell(new Cell().add(new Paragraph("Tipo de Mídia")).setBold());

    // Dados (sem negrito, com fonte menor)
    for (Filme filme : filmes) {
      table.addCell(new Cell().add(new Paragraph(filme.getDescricao() != null ? filme.getDescricao() : "").setFontSize(8)));
      table.addCell(new Cell().add(new Paragraph(filme.getAno() != null ? filme.getAno().toString() : "").setFontSize(8)));
      table.addCell(new Cell().add(new Paragraph(filme.getDiretor() != null ? filme.getDiretor() : "").setFontSize(8)));
      table.addCell(new Cell().add(new Paragraph(filme.getGenero() != null ? filme.getGenero().getDescricao() : "").setFontSize(8)));
      table.addCell(new Cell().add(new Paragraph(filme.getTipoMidia() != null ? filme.getTipoMidia().getDescricao() : "").setFontSize(8)));
    }

    document.add(table);
    document.close();

    // Adicionar números de página no arquivo final
    addPageNumbers(tempFilePath, filePath);

    // Deletar o arquivo temporário
    new File(tempFilePath).delete();
  }

  private static void addPageNumbers(String src, String dest) throws IOException {
    PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
    Document doc = new Document(pdfDoc);

    int numberOfPages = pdfDoc.getNumberOfPages();
    PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

    for (int i = 1; i <= numberOfPages; i++) {
      PdfPage page = pdfDoc.getPage(i);
      Rectangle pageSize = page.getPageSize();
      float x = pageSize.getWidth() / 2;
      float y = pageSize.getBottom() + 20;

      // Adicionar texto alinhado ao centro
      Paragraph pageText = new Paragraph(String.format("Página %d de %d", i, numberOfPages))
              .setFont(font)
              .setFontSize(10);

      doc.showTextAligned(pageText, x, y, i, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0);
    }

    doc.close();
  }
}