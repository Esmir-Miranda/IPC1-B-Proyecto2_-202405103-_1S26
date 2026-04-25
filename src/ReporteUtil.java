package util;

import javax.swing.JTable;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ReporteUtil {

    public static String exportarCSV(JTable tabla, String tipoReporte) throws Exception {
        String nombre = util.FechaUtil.fechaArchivo() + "_" + tipoReporte + ".csv";
        PrintWriter pw = new PrintWriter(new FileWriter(nombre));

        for (int c = 0; c < tabla.getColumnCount(); c++) {
            pw.print(limpiarCSV(tabla.getColumnName(c)));
            if (c < tabla.getColumnCount() - 1) {
                pw.print(",");
            }
        }
        pw.println();

        for (int f = 0; f < tabla.getRowCount(); f++) {
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Object valor = tabla.getValueAt(f, c);
                pw.print(limpiarCSV(valor == null ? "" : valor.toString()));
                if (c < tabla.getColumnCount() - 1) {
                    pw.print(",");
                }
            }
            pw.println();
        }

        pw.close();
        return nombre;
    }

    /*
     * Generador PDF real sin librerias externas.
     * No usa ArrayList, LinkedList, HashMap, List, Collections ni streams.
     * Crea un PDF valido con texto y varias paginas si la tabla tiene muchas filas.
     */
    public static String exportarPDFBasico(JTable tabla, String tipoReporte) throws Exception {
        String nombre = util.FechaUtil.fechaArchivo() + "_" + tipoReporte + ".pdf";

        String[] lineas = construirLineasReporte(tabla, tipoReporte);
        int totalLineas = contarLineas(lineas);
        int lineasPorPagina = 58;
        int paginas = totalLineas / lineasPorPagina;
        if (totalLineas % lineasPorPagina != 0) {
            paginas++;
        }
        if (paginas == 0) {
            paginas = 1;
        }

        int totalObjetos = 3 + (paginas * 2);
        int[] offsets = new int[totalObjetos + 1];
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        escribir(out, "%PDF-1.4\n");

        offsets[1] = out.size();
        escribir(out, "1 0 obj\n");
        escribir(out, "<< /Type /Catalog /Pages 2 0 R >>\n");
        escribir(out, "endobj\n");

        offsets[2] = out.size();
        escribir(out, "2 0 obj\n");
        escribir(out, "<< /Type /Pages /Kids [");
        for (int p = 0; p < paginas; p++) {
            int pageObj = 4 + (p * 2);
            escribir(out, pageObj + " 0 R ");
        }
        escribir(out, "] /Count " + paginas + " >>\n");
        escribir(out, "endobj\n");

        offsets[3] = out.size();
        escribir(out, "3 0 obj\n");
        escribir(out, "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\n");
        escribir(out, "endobj\n");

        for (int p = 0; p < paginas; p++) {
            int pageObj = 4 + (p * 2);
            int contentObj = pageObj + 1;

            String contenido = crearContenidoPagina(lineas, p, lineasPorPagina, totalLineas, tipoReporte);

            offsets[pageObj] = out.size();
            escribir(out, pageObj + " 0 obj\n");
            escribir(out, "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] ");
            escribir(out, "/Resources << /Font << /F1 3 0 R >> >> ");
            escribir(out, "/Contents " + contentObj + " 0 R >>\n");
            escribir(out, "endobj\n");

            offsets[contentObj] = out.size();
            escribir(out, contentObj + " 0 obj\n");
            escribir(out, "<< /Length " + contenido.getBytes("ISO-8859-1").length + " >>\n");
            escribir(out, "stream\n");
            escribir(out, contenido);
            escribir(out, "\nendstream\n");
            escribir(out, "endobj\n");
        }

        int inicioXref = out.size();
        escribir(out, "xref\n");
        escribir(out, "0 " + (totalObjetos + 1) + "\n");
        escribir(out, "0000000000 65535 f \n");
        for (int i = 1; i <= totalObjetos; i++) {
            escribir(out, rellenar10(offsets[i]) + " 00000 n \n");
        }

        escribir(out, "trailer\n");
        escribir(out, "<< /Size " + (totalObjetos + 1) + " /Root 1 0 R >>\n");
        escribir(out, "startxref\n");
        escribir(out, inicioXref + "\n");
        escribir(out, "%%EOF");

        FileOutputStream fos = new FileOutputStream(nombre);
        out.writeTo(fos);
        fos.close();

        return nombre;
    }

    private static String[] construirLineasReporte(JTable tabla, String tipoReporte) {
        String[] lineas = new String[6000];
        int pos = 0;

        lineas[pos++] = "REPORTE: " + tipoReporte;
        lineas[pos++] = "Fecha de emision: " + util.FechaUtil.fechaHoraActual();
        lineas[pos++] = "Filas: " + tabla.getRowCount() + " | Columnas: " + tabla.getColumnCount();
        lineas[pos++] = "------------------------------------------------------------";

        StringBuilder encabezado = new StringBuilder();
        for (int c = 0; c < tabla.getColumnCount(); c++) {
            encabezado.append(tabla.getColumnName(c));
            if (c < tabla.getColumnCount() - 1) {
                encabezado.append(" | ");
            }
        }
        pos = agregarTextoDividido(lineas, pos, encabezado.toString());
        lineas[pos++] = "------------------------------------------------------------";

        for (int f = 0; f < tabla.getRowCount(); f++) {
            StringBuilder fila = new StringBuilder();
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Object valor = tabla.getValueAt(f, c);
                fila.append(valor == null ? "" : valor.toString());
                if (c < tabla.getColumnCount() - 1) {
                    fila.append(" | ");
                }
            }
            pos = agregarTextoDividido(lineas, pos, fila.toString());
        }

        return lineas;
    }

    private static int agregarTextoDividido(String[] lineas, int pos, String texto) {
        texto = texto == null ? "" : texto;
        int max = 105;

        if (texto.length() <= max) {
            if (pos < lineas.length) {
                lineas[pos++] = texto;
            }
            return pos;
        }

        int inicio = 0;
        while (inicio < texto.length() && pos < lineas.length) {
            int fin = inicio + max;
            if (fin > texto.length()) {
                fin = texto.length();
            }
            lineas[pos++] = texto.substring(inicio, fin);
            inicio = fin;
        }
        return pos;
    }

    private static int contarLineas(String[] lineas) {
        int total = 0;
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i] != null) {
                total++;
            } else {
                break;
            }
        }
        return total;
    }

    private static String crearContenidoPagina(String[] lineas, int pagina, int lineasPorPagina, int totalLineas, String tipoReporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("BT\n");
        sb.append("/F1 9 Tf\n");
        sb.append("40 760 Td\n");

        int inicio = pagina * lineasPorPagina;
        int fin = inicio + lineasPorPagina;
        if (fin > totalLineas) {
            fin = totalLineas;
        }

        for (int i = inicio; i < fin; i++) {
            sb.append("(").append(escaparPDF(lineas[i])).append(") Tj\n");
            sb.append("0 -12 Td\n");
        }

        sb.append("0 -12 Td\n");
        sb.append("(Pagina ").append(pagina + 1).append(") Tj\n");
        sb.append("ET");
        return sb.toString();
    }

    private static String escaparPDF(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char ch = texto.charAt(i);
            if (ch == '(' || ch == ')' || ch == '\\') {
                sb.append('\\');
            }
            if (ch >= 32 && ch <= 126) {
                sb.append(ch);
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private static String limpiarCSV(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace(",", " ").replace("\n", " ").replace("\r", " ");
    }

    private static String rellenar10(int numero) {
        String s = String.valueOf(numero);
        while (s.length() < 10) {
            s = "0" + s;
        }
        return s;
    }

    private static void escribir(ByteArrayOutputStream out, String texto) throws Exception {
        out.write(texto.getBytes("ISO-8859-1"));
    }
}
