package util;

import javax.swing.JTable;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class PDFReportUtil {
    public static String exportarTablaPDF(String titulo, JTable tabla, String prefijo) throws Exception {
        String nombre = util.FechaUtil.archivo() + "_" + prefijo + ".pdf";
        StringBuilder texto = new StringBuilder();
        texto.append(titulo).append("\n");
        texto.append("Fecha: ").append(util.FechaUtil.ahora()).append("\n\n");
        for (int c = 0; c < tabla.getColumnCount(); c++) texto.append(tabla.getColumnName(c)).append(" | ");
        texto.append("\n");
        for (int f = 0; f < tabla.getRowCount(); f++) {
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Object val = tabla.getValueAt(f, c);
                texto.append(val == null ? "" : val.toString()).append(" | ");
            }
            texto.append("\n");
        }
        crearPDFBasico(nombre, texto.toString());
        return nombre;
    }

    private static void crearPDFBasico(String archivo, String contenido) throws Exception {
        String[] lineas = contenido.replace("(", "[").replace(")", "]").split("\\n");
        StringBuilder stream = new StringBuilder();
        stream.append("BT\n/F1 10 Tf\n50 790 Td\n");
        for (int i = 0; i < lineas.length && i < 60; i++) {
            String linea = lineas[i];
            if (linea.length() > 110) linea = linea.substring(0, 110);
            stream.append("(").append(linea).append(") Tj\n0 -14 Td\n");
        }
        stream.append("ET");
        byte[] streamBytes = stream.toString().getBytes(StandardCharsets.ISO_8859_1);

        StringBuilder pdf = new StringBuilder();
        int[] offsets = new int[6];
        pdf.append("%PDF-1.4\n");
        offsets[1] = pdf.length();
        pdf.append("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n");
        offsets[2] = pdf.length();
        pdf.append("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n");
        offsets[3] = pdf.length();
        pdf.append("3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 612 842] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >> endobj\n");
        offsets[4] = pdf.length();
        pdf.append("4 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n");
        offsets[5] = pdf.length();
        pdf.append("5 0 obj << /Length ").append(streamBytes.length).append(" >> stream\n");
        pdf.append(stream);
        pdf.append("\nendstream endobj\n");
        int xref = pdf.length();
        pdf.append("xref\n0 6\n0000000000 65535 f \n");
        for (int i = 1; i <= 5; i++) pdf.append(String.format("%010d 00000 n \n", offsets[i]));
        pdf.append("trailer << /Size 6 /Root 1 0 R >>\nstartxref\n").append(xref).append("\n%%EOF");

        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(pdf.toString().getBytes(StandardCharsets.ISO_8859_1));
        fos.close();
    }
}
