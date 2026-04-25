package vista;

import controlador.AppController;
import modelo.Usuario;
import util.ReporteUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardEstudianteTop extends JFrame {
    private AppController controller;
    private Usuario estudiante;
    private JTable tabla;

    public DashboardEstudianteTop(AppController controller, Usuario estudiante) {
        this.controller = controller;
        this.estudiante = estudiante;
        setTitle("Sancarlista Academy - Estudiante");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(sidebar(), BorderLayout.WEST);
        add(content(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel sidebar() {
        JPanel side = new JPanel(new GridLayout(6, 1, 8, 8));
        side.setPreferredSize(new Dimension(240, 650));
        side.setBackground(vista.UIStyle.SIDEBAR);
        side.setBorder(new EmptyBorder(15, 15, 15, 15));
        JButton perfil = vista.UIStyle.button("Ver Perfil");
        JButton secciones = vista.UIStyle.button("Secciones Disponibles");
        JButton inscribir = vista.UIStyle.button("Inscribirme");
        JButton pdf = vista.UIStyle.button("Exportar PDF");
        JButton csv = vista.UIStyle.button("Exportar CSV");
        JButton salir = vista.UIStyle.danger("Cerrar Sesión");
        perfil.addActionListener(e -> JOptionPane.showMessageDialog(this, "Código: " + estudiante.getCodigo() + "\nNombre: " + estudiante.getNombre() + "\nRol: " + estudiante.getRol()));
        secciones.addActionListener(e -> tabla.setModel(new DefaultTableModel(controller.getSistema().getSeccionesTableData(), controller.getSistema().getSeccionesColumns())));
        inscribir.addActionListener(e -> inscribir());
        pdf.addActionListener(e -> exportarPDF());
        csv.addActionListener(e -> exportarCSV());
        salir.addActionListener(e -> cerrarSesion());
        side.add(perfil); side.add(secciones); side.add(inscribir); side.add(pdf); side.add(csv); side.add(salir);
        return side;
    }

    private JPanel content() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(vista.UIStyle.BG);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel title = vista.UIStyle.title("Panel Estudiante - " + estudiante.getNombre());
        tabla = vista.UIStyle.table(new JTable(new DefaultTableModel(controller.getSistema().getSeccionesTableData(), controller.getSistema().getSeccionesColumns())));
        p.add(title, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    private void inscribir() {
        String codigo = JOptionPane.showInputDialog(this, "Código de sección:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        boolean ok = controller.inscribir(estudiante.getCodigo(), codigo);
        JOptionPane.showMessageDialog(this, ok ? "Inscripción realizada." : "No se pudo inscribir. Puede haber duplicado, choque o sección cerrada.");
    }

    private void exportarPDF() { try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarPDFBasico(tabla, "ReporteEstudiante")); } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); } }
    private void exportarCSV() { try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarCSV(tabla, "ReporteEstudiante")); } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); } }
    private void cerrarSesion() { controller.logout(estudiante); dispose(); new vista.LoginFrameTop(controller); }
}
