package vista;

import controlador.AppController;
import hilos.MonitorSesionesThread;
import modelo.Usuario;
import util.PDFReportUtil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class DashboardAdminTop extends JFrame {
    private final AppController controller;
    private final Usuario admin;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea areaSesiones;
    private MonitorSesionesThread thread;

    public DashboardAdminTop(AppController controller, Usuario admin) {
        this.controller = controller;
        this.admin = admin;
        setTitle("Sancarlista Academy - Administrador");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(buildTopbar(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
        iniciarThread();
        cargarTablaUsuarios();
        setVisible(true);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 700));
        sidebar.setBackground(UIStyle.SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel brand = UIStyle.createTitle("Academy Pro");
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton btnInstructor = UIStyle.createButton("Crear Instructor");
        JButton btnEstudiante = UIStyle.createButton("Crear Estudiante");
        JButton btnCurso = UIStyle.createButton("Crear Curso");
        JButton btnUsuarios = UIStyle.createButton("Tabla Usuarios");
        JButton btnCursos = UIStyle.createButton("Tabla Cursos");
        JButton btnBitacora = UIStyle.createButton("Tabla Bitácora");
        JButton btnPdf = UIStyle.createButton("Exportar PDF");
        JButton btnSalir = UIStyle.createDangerButton("Cerrar Sesión");

        btnInstructor.addActionListener(e -> crearInstructor());
        btnEstudiante.addActionListener(e -> crearEstudiante());
        btnCurso.addActionListener(e -> crearCurso());
        btnUsuarios.addActionListener(e -> cargarTablaUsuarios());
        btnCursos.addActionListener(e -> cargarTablaCursos());
        btnBitacora.addActionListener(e -> cargarTablaBitacora());
        btnPdf.addActionListener(e -> exportarPDF());
        btnSalir.addActionListener(e -> cerrarSesion());

        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(25));
        sidebar.add(btnInstructor); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnEstudiante); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCurso); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnUsuarios); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCursos); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnBitacora); sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnPdf);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnSalir);
        return sidebar;
    }

    private JPanel buildTopbar() {
        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(UIStyle.BG);
        topbar.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel title = UIStyle.createSubtitle("Panel Administrativo Profesional");
        JLabel user = new JLabel("Usuario: " + admin.getNombre());
        user.setForeground(UIStyle.TEXT_SOFT);
        user.setFont(UIStyle.normalFont());
        topbar.add(title, BorderLayout.WEST);
        topbar.add(user, BorderLayout.EAST);
        return topbar;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 15));
        content.setBackground(UIStyle.BG);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 15));
        cards.setBackground(UIStyle.BG);
        cards.add(cardValor("Usuarios", String.valueOf(controller.getSistema().getTotalUsuarios())));
        cards.add(cardValor("Cursos", String.valueOf(controller.getSistema().getTotalCursos())));
        JPanel c3 = UIStyle.createCard();
        c3.setLayout(new BorderLayout());
        c3.add(UIStyle.createSubtitle("Sesiones"), BorderLayout.NORTH);
        areaSesiones = UIStyle.createTextArea();
        c3.add(new JScrollPane(areaSesiones), BorderLayout.CENTER);
        cards.add(c3);

        model = new DefaultTableModel();
        table = UIStyle.styleTable(new JTable(model));
        JPanel tableCard = UIStyle.createCard();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(UIStyle.createSubtitle("Datos del Sistema"), BorderLayout.NORTH);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        content.add(cards, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);
        return content;
    }

    private JPanel cardValor(String titulo, String valor) {
        JPanel card = UIStyle.createCard();
        card.setLayout(new BorderLayout());
        JLabel numero = new JLabel(valor, JLabel.CENTER);
        numero.setForeground(UIStyle.TEXT);
        numero.setFont(new Font("Segoe UI", Font.BOLD, 32));
        card.add(UIStyle.createSubtitle(titulo), BorderLayout.NORTH);
        card.add(numero, BorderLayout.CENTER);
        return card;
    }

    private void iniciarThread() {
        thread = new MonitorSesionesThread(controller, areaSesiones);
        thread.start();
    }

    private void cargarTablaUsuarios() {
        model.setDataVector(controller.getSistema().getUsuariosTableData(), controller.getSistema().getUsuariosColumns());
    }

    private void cargarTablaCursos() {
        model.setDataVector(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns());
    }

    private void cargarTablaBitacora() {
        model.setDataVector(controller.getSistema().getBitacoraTableData(), controller.getSistema().getBitacoraColumns());
    }

    private void crearInstructor() {
        crearUsuario("INSTRUCTOR");
    }

    private void crearEstudiante() {
        crearUsuario("ESTUDIANTE");
    }

    private void crearUsuario(String tipo) {
        JTextField codigo = UIStyle.createTextField();
        JTextField nombre = UIStyle.createTextField();
        JTextField fecha = UIStyle.createTextField();
        JTextField genero = UIStyle.createTextField();
        JTextField pass = UIStyle.createTextField();
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.add(new JLabel("Código:")); panel.add(codigo);
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Fecha nacimiento:")); panel.add(fecha);
        panel.add(new JLabel("Género:")); panel.add(genero);
        panel.add(new JLabel("Contraseña:")); panel.add(pass);
        int r = JOptionPane.showConfirmDialog(this, panel, "Crear " + tipo, JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = tipo.equals("INSTRUCTOR")
                    ? controller.crearInstructor(codigo.getText(), nombre.getText(), fecha.getText(), genero.getText(), pass.getText())
                    : controller.crearEstudiante(codigo.getText(), nombre.getText(), fecha.getText(), genero.getText(), pass.getText());
            JOptionPane.showMessageDialog(this, ok ? tipo + " creado correctamente." : "No se pudo crear. Revisa código duplicado.");
            cargarTablaUsuarios();
        }
    }

    private void crearCurso() {
        JTextField codigo = UIStyle.createTextField();
        JTextField nombre = UIStyle.createTextField();
        JTextField descripcion = UIStyle.createTextField();
        JTextField creditos = UIStyle.createTextField();
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Código:")); panel.add(codigo);
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Descripción:")); panel.add(descripcion);
        panel.add(new JLabel("Créditos:")); panel.add(creditos);
        int r = JOptionPane.showConfirmDialog(this, panel, "Crear Curso", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.crearCurso(codigo.getText(), nombre.getText(), descripcion.getText(), Integer.parseInt(creditos.getText()));
                JOptionPane.showMessageDialog(this, ok ? "Curso creado correctamente." : "No se pudo crear. Revisa código duplicado.");
                cargarTablaCursos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Créditos debe ser un número entero.");
            }
        }
    }

    private void exportarPDF() {
        try {
            String archivo = PDFReportUtil.exportarTablaPDF("Reporte Sancarlista Academy", table, "Reporte");
            JOptionPane.showMessageDialog(this, "PDF generado: " + archivo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + ex.getMessage());
        }
    }

    private void cerrarSesion() {
        if (thread != null) thread.detener();
        controller.logout(admin);
        dispose();
        new LoginFrameTop(controller);
    }
}
