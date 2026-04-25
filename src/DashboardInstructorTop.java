package vista;

import controlador.AppController;
import modelo.Usuario;
import util.PDFReportUtil;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

public class DashboardInstructorTop extends JFrame {
        private final AppController controller;
        private final Usuario instructor;
        private JTable table;

        public DashboardInstructorTop(AppController controller, Usuario instructor) {
                this.controller = controller;
                this.instructor = instructor;
                setTitle("Sancarlista Academy - Instructor");
                setSize(1100, 650);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout());
                add(buildSidebar(), BorderLayout.WEST);
                add(buildContent(), BorderLayout.CENTER);
                setVisible(true);
        }

        private JPanel buildSidebar() {
                JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 10));
                sidebar.setPreferredSize(new Dimension(250, 650));
                sidebar.setBackground(UIStyle.SIDEBAR);
                JButton btnUsuarios = UIStyle.createButton("Ver Usuarios");
                JButton btnCursos = UIStyle.createButton("Ver Cursos");
                JButton btnBitacora = UIStyle.createButton("Ver Bitácora");
                JButton btnPdf = UIStyle.createButton("Exportar PDF");
                JButton btnSalir = UIStyle.createDangerButton("Cerrar Sesión");
                btnUsuarios.addActionListener(e -> table.setModel(new DefaultTableModel(controller.getSistema().getUsuariosTableData(), controller.getSistema().getUsuariosColumns())));
                btnCursos.addActionListener(e -> table.setModel(new DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
                btnBitacora.addActionListener(e -> table.setModel(new DefaultTableModel(controller.getSistema().getBitacoraTableData(), controller.getSistema().getBitacoraColumns())));
                btnPdf.addActionListener(e -> exportarPDF());
                btnSalir.addActionListener(e -> cerrarSesion());
                sidebar.add(btnUsuarios);
                sidebar.add(btnCursos);
                sidebar.add(btnBitacora);
                sidebar.add(btnPdf);
                sidebar.add(btnSalir);
                return sidebar;
        }

        private JPanel buildContent() {
                JPanel center = new JPanel(new BorderLayout(0, 12));
                center.setBackground(UIStyle.BG);
                JPanel header = UIStyle.createCard();
                header.setLayout(new BorderLayout());
                header.add(UIStyle.createTitle("Módulo del Instructor"), BorderLayout.WEST);
                JLabel user = new JLabel("Instructor: " + instructor.getNombre());
                user.setForeground(UIStyle.TEXT_SOFT);
                header.add(user, BorderLayout.EAST);
                JTextArea info = UIStyle.createTextArea();
                info.setText("Bienvenido. Desde este panel puedes consultar información académica y exportarla a PDF.");
                table = UIStyle.styleTable(new JTable(new DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
                center.add(header, BorderLayout.NORTH);
                center.add(new JScrollPane(table), BorderLayout.CENTER);
                center.add(new JScrollPane(info), BorderLayout.SOUTH);
                return center;
        }

        private void exportarPDF() {
                try {
                        String archivo = PDFReportUtil.exportarTablaPDF("Reporte Instructor", table, "ReporteInstructor");
                        JOptionPane.showMessageDialog(this, "PDF generado: " + archivo);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error PDF: " + e.getMessage());
                }
        }

        private void cerrarSesion() {
                controller.logout(instructor);
                dispose();
                new LoginFrameTop(controller);
        }
}
