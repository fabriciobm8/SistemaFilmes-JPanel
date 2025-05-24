package com.moviemanager.util;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.moviemanager.model.Filme;
import java.io.IOException;
import java.util.List;

public class PdfExporter {
  public static void exportToPdf(List<Filme> filmes, String filePath, String filtrosAplicados, int totalFilmes) throws IOException {
    PdfWriter writer = new PdfWriter(filePath);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    // Adiciona manipulador de eventos para números de página
    pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new PageNumberEventHandler(pdf));

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

    // Dados (sem negrito)
    for (Filme filme : filmes) {
      table.addCell(new Cell().add(new Paragraph(filme.getDescricao() != null ? filme.getDescricao() : "")));
      table.addCell(new Cell().add(new Paragraph(filme.getAno() != null ? filme.getAno().toString() : "")));
      table.addCell(new Cell().add(new Paragraph(filme.getDiretor() != null ? filme.getDiretor() : "")));
      table.addCell(new Cell().add(new Paragraph(filme.getGenero() != null ? filme.getGenero().getDescricao() : "")));
      table.addCell(new Cell().add(new Paragraph(filme.getTipoMidia() != null ? filme.getTipoMidia().getDescricao() : "")));
    }

    document.add(table);
    document.close();
  }

  // Manipulador de eventos para números de página
  private static class PageNumberEventHandler implements IEventHandler {
    private final PdfDocument pdf;

    public PageNumberEventHandler(PdfDocument pdf) {
      this.pdf = pdf;
    }

    @Override
    public void handleEvent(Event event) {
      PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
      PdfPage page = docEvent.getPage();
      PdfCanvas canvas = new PdfCanvas(page);
      Rectangle pageSize = page.getPageSize();
      int pageNumber = pdf.getPageNumber(page);
      int totalPages = pdf.getNumberOfPages();

      // Adiciona "Página X de Y" no rodapé (centro)
      String pageText = String.format("Página %d de %d", pageNumber, totalPages);
      canvas.beginText();
      try {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        canvas.setFontAndSize(font, 10);
      } catch (IOException e) {
        e.printStackTrace();
      }
      float x = pageSize.getWidth() / 2;
      float y = pageSize.getBottom() + 20; // 20 pontos acima da borda inferior
      canvas.moveText(x - (pageText.length() * 2.5f), y); // Ajuste aproximado para centralizar
      canvas.showText(pageText);
      canvas.endText();
      canvas.release();
    }
  }
}