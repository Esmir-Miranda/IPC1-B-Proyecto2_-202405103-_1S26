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

public class DashboardEstudianteTop extends JFrame {
    private final AppController controller;
    private final Usuario estudiante;
    private JTable table;
    private JTextArea info;

    public DashboardEstudianteTop(AppController controller, Usuario estudiante) {
        this.controller = controller;
        this.estudiante = estudiante;
        setTitle("Sancarlista Academy - Estudiante");
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
        JButton btnPerfil = UIStyle.createButton("Mi Perfil");
        JButton btnCursos = UIStyle.createButton("Ver Cursos");
        JButton btnBitacora = UIStyle.createButton("Ver Bitácora");
        JButton btnPdf = UIStyle.createButton("Exportar PDF");
        JButton btnSalir = UIStyle.createDangerButton("Cerrar Sesión");
        btnPerfil.addActionListener(e -> mostrarPerfil());
        btnCursos.addActionListener(e -> table.setModel(new DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
        btnBitacora.addActionListener(e -> table.setModel(new DefaultTableModel(controller.getSistema().getBitacoraTableData(), controller.getSistema().getBitacoraColumns())));
        btnPdf.addActionListener(e -> exportarPDF());
        btnSalir.addActionListener(e -> cerrarSesion());
        sidebar.add(btnPerfil);
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
        header.add(UIStyle.createTitle("Módulo del Estudiante"), BorderLayout.WEST);
        JLabel user = new JLabel("Estudiante: " + estudiante.getNombre());
        user.setForeground(UIStyle.TEXT_SOFT);
        header.add(user, BorderLayout.EAST);
        info = UIStyle.createTextArea();
        table = UIStyle.styleTable(new JTable(new DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
        mostrarPerfil();
        center.add(header, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        center.add(new JScrollPane(info), BorderLayout.SOUTH);
        return center;
    }

    private void mostrarPerfil() {
        if (info != null) {
            info.setText("Código: " + estudiante.getCodigo() + "\nNombre: " + estudiante.getNombre() + "\nRol: " + estudiante.getRol() + "\nFecha nacimiento: " + estudiante.getFechaNacimiento() + "\nGénero: " + estudiante.getGenero());
        }
    }

    private void exportarPDF() {
        try {
            String archivo = PDFReportUtil.exportarTablaPDF("Reporte Estudiante", table, "ReporteEstudiante");
            JOptionPane.showMessageDialog(this, "PDF generado: " + archivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error PDF: " + e.getMessage());
        }
    }

    private void cerrarSesion() {
        controller.logout(estudiante);
        dispose();
        new LoginFrameTop(controller);
    }
}
