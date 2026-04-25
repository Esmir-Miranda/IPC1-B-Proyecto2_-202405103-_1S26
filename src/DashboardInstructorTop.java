package vista;

import controlador.AppController;
import modelo.Usuario;
import util.ReporteUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardInstructorTop extends JFrame {
        private AppController controller;
        private Usuario instructor;
        private JTable tabla;
        private DefaultTableModel modeloTabla;

        public DashboardInstructorTop(AppController controller, Usuario instructor) {
                this.controller = controller;
                this.instructor = instructor;
                setTitle("Sancarlista Academy - Instructor");
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
                JButton sec = vista.UIStyle.button("Ver Secciones");
                JButton nota = vista.UIStyle.button("Registrar Nota");
                JButton bit = vista.UIStyle.button("Ver Bitácora");
                JButton pdf = vista.UIStyle.button("Exportar PDF");
                JButton csv = vista.UIStyle.button("Exportar CSV");
                JButton salir = vista.UIStyle.danger("Cerrar Sesión");
                sec.addActionListener(e -> tabla.setModel(new DefaultTableModel(controller.getSistema().getSeccionesTableData(), controller.getSistema().getSeccionesColumns())));
                nota.addActionListener(e -> registrarNota());
                bit.addActionListener(e -> tabla.setModel(new DefaultTableModel(controller.getSistema().getBitacoraTableData(), controller.getSistema().getBitacoraColumns())));
                pdf.addActionListener(e -> exportarPDF());
                csv.addActionListener(e -> exportarCSV());
                salir.addActionListener(e -> cerrarSesion());
                side.add(sec); side.add(nota); side.add(bit); side.add(pdf); side.add(csv); side.add(salir);
                return side;
        }

        private JPanel content() {
                JPanel p = new JPanel(new BorderLayout());
                p.setBackground(vista.UIStyle.BG);
                p.setBorder(new EmptyBorder(20, 20, 20, 20));
                JLabel title = vista.UIStyle.title("Panel Instructor - " + instructor.getNombre());
                modeloTabla = new DefaultTableModel(controller.getSistema().getSeccionesTableData(), controller.getSistema().getSeccionesColumns());
                tabla = vista.UIStyle.table(new JTable(modeloTabla));
                p.add(title, BorderLayout.NORTH);
                p.add(new JScrollPane(tabla), BorderLayout.CENTER);
                return p;
        }

        private JPanel form(String[] labels, JTextField[] fields) {
                JPanel p = new JPanel(new GridLayout(labels.length, 2, 8, 8));
                for (int i = 0; i < labels.length; i++) { p.add(new JLabel(labels[i])); p.add(fields[i]); }
                return p;
        }

        private void registrarNota() {
                JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
                int r = JOptionPane.showConfirmDialog(this,
                        form(new String[]{"Código curso", "Código sección", "Código estudiante", "Actividad", "Ponderación", "Nota", "Fecha"}, f),
                        "Registrar Nota", JOptionPane.OK_CANCEL_OPTION);
                if (r == JOptionPane.OK_OPTION) {
                        try {
                                boolean ok = controller.registrarNota(instructor.getCodigo(), f[0].getText(), f[1].getText(), f[2].getText(), f[3].getText(), Double.parseDouble(f[4].getText()), Double.parseDouble(f[5].getText()), f[6].getText());
                                JOptionPane.showMessageDialog(this, ok ? "Nota registrada." : "No se pudo registrar. Verifica sección, estudiante, rango o duplicado.");
                        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Ponderación o nota inválida."); }
                }
        }

        private void exportarPDF() { try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarPDFBasico(tabla, "ReporteInstructor")); } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); } }
        private void exportarCSV() { try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarCSV(tabla, "ReporteInstructor")); } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); } }
        private void cerrarSesion() { controller.logout(instructor); dispose(); new vista.LoginFrameTop(controller); }
}
