package util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.JTable;
import java.io.FileOutputStream;

public class PDFReportUtil {

    public static String exportarTablaPDF(String titulo, JTable tabla, String prefijoArchivo) throws Exception {
        String nombre = FechaUtil.getFechaArchivo() + "_" + prefijoArchivo + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nombre));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        document.add(new Paragraph(titulo, titleFont));
        document.add(new Paragraph("Fecha de emisión: " + FechaUtil.getFechaHoraActual(), bodyFont));
        document.add(new Paragraph(" "));

        int columnas = tabla.getColumnCount();
        PdfPTable pdfTable = new PdfPTable(columnas);
        pdfTable.setWidthPercentage(100);

        for (int i = 0; i < columnas; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(tabla.getColumnName(i), bodyFont));
            pdfTable.addCell(cell);
        }

        for (int fila = 0; fila < tabla.getRowCount(); fila++) {
            for (int col = 0; col < columnas; col++) {
                Object valor = tabla.getValueAt(fila, col);
                pdfTable.addCell(valor == null ? "" : valor.toString());
            }
        }

        document.add(pdfTable);
        document.close();
        return nombre;
    }
}