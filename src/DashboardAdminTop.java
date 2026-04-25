package vista;

import controlador.AppController;
import hilos.MonitorSistemaThread;
import modelo.Usuario;
import util.ReporteUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardAdminTop extends JFrame {
    private AppController controller;
    private Usuario admin;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel lblMonitor;
    private MonitorSistemaThread monitor;

    public DashboardAdminTop(AppController controller, Usuario admin) {
        this.controller = controller;
        this.admin = admin;
        setTitle("Sancarlista Academy - Administrador");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(sidebar(), BorderLayout.WEST);
        add(topbar(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
        monitor = new MonitorSistemaThread(controller, lblMonitor);
        monitor.start();
        setVisible(true);
    }

    private JPanel sidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(260, 700));
        side.setLayout(new GridLayout(15, 1, 8, 8));
        side.setBackground(vista.UIStyle.SIDEBAR);
        side.setBorder(new EmptyBorder(15, 15, 15, 15));
        JButton usuarios = vista.UIStyle.button("Ver Usuarios");
        JButton cursos = vista.UIStyle.button("Ver Cursos");
        JButton secciones = vista.UIStyle.button("Ver Secciones");
        JButton bitacora = vista.UIStyle.button("Ver Bitácora");
        JButton crearInstructor = vista.UIStyle.button("Crear Instructor");
        JButton actualizarInstructor = vista.UIStyle.button("Actualizar Instructor");
        JButton eliminarInstructor = vista.UIStyle.danger("Eliminar Instructor");
        JButton crearEstudiante = vista.UIStyle.button("Crear Estudiante");
        JButton actualizarEstudiante = vista.UIStyle.button("Actualizar Estudiante");
        JButton eliminarEstudiante = vista.UIStyle.danger("Eliminar Estudiante");
        JButton crearCurso = vista.UIStyle.button("Crear Curso");
        JButton actualizarCurso = vista.UIStyle.button("Actualizar Curso");
        JButton eliminarCurso = vista.UIStyle.danger("Eliminar Curso");
        JButton crearSeccion = vista.UIStyle.button("Crear Sección");
        JButton asignar = vista.UIStyle.button("Asignar Instructor");
        JButton pdf = vista.UIStyle.button("Exportar PDF");
        JButton csv = vista.UIStyle.button("Exportar CSV");
        JButton salir = vista.UIStyle.danger("Cerrar Sesión");

        usuarios.addActionListener(e -> cargarUsuarios());
        cursos.addActionListener(e -> cargarCursos());
        secciones.addActionListener(e -> cargarSecciones());
        bitacora.addActionListener(e -> cargarBitacora());
        crearInstructor.addActionListener(e -> crearInstructor());
        actualizarInstructor.addActionListener(e -> actualizarInstructor());
        eliminarInstructor.addActionListener(e -> eliminarInstructor());
        crearEstudiante.addActionListener(e -> crearEstudiante());
        actualizarEstudiante.addActionListener(e -> actualizarEstudiante());
        eliminarEstudiante.addActionListener(e -> eliminarEstudiante());
        crearCurso.addActionListener(e -> crearCurso());
        actualizarCurso.addActionListener(e -> actualizarCurso());
        eliminarCurso.addActionListener(e -> eliminarCurso());
        crearSeccion.addActionListener(e -> crearSeccion());
        asignar.addActionListener(e -> asignarInstructor());
        pdf.addActionListener(e -> exportarPDF());
        csv.addActionListener(e -> exportarCSV());
        salir.addActionListener(e -> cerrarSesion());

        side.add(usuarios); side.add(cursos); side.add(secciones); side.add(bitacora);
        side.add(crearInstructor); side.add(actualizarInstructor); side.add(eliminarInstructor);
        side.add(crearEstudiante); side.add(actualizarEstudiante); side.add(eliminarEstudiante);
        side.add(crearCurso); side.add(actualizarCurso); side.add(eliminarCurso);
        side.add(crearSeccion); side.add(asignar);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(vista.UIStyle.SIDEBAR);
        wrapper.add(side, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new GridLayout(3, 1, 8, 8));
        bottom.setBackground(vista.UIStyle.SIDEBAR);
        bottom.setBorder(new EmptyBorder(0, 15, 15, 15));
        bottom.add(pdf); bottom.add(csv); bottom.add(salir);
        wrapper.add(bottom, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel topbar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(vista.UIStyle.BG);
        top.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel title = vista.UIStyle.title("Panel Administrador");
        lblMonitor = new JLabel("Monitor iniciando...");
        lblMonitor.setForeground(vista.UIStyle.SOFT);
        top.add(title, BorderLayout.WEST);
        top.add(lblMonitor, BorderLayout.EAST);
        return top;
    }

    private JPanel content() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(vista.UIStyle.BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        modeloTabla = new DefaultTableModel();
        tabla = vista.UIStyle.table(new JTable(modeloTabla));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        cargarUsuarios();
        return panel;
    }

    private void setTabla(Object[][] data, String[] columns) { modeloTabla.setDataVector(data, columns); }
    private void cargarUsuarios() { setTabla(controller.getSistema().getUsuariosTableData(), controller.getSistema().getUsuariosColumns()); }
    private void cargarCursos() { setTabla(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns()); }
    private void cargarSecciones() { setTabla(controller.getSistema().getSeccionesTableData(), controller.getSistema().getSeccionesColumns()); }
    private void cargarBitacora() { setTabla(controller.getSistema().getBitacoraTableData(), controller.getSistema().getBitacoraColumns()); }

    private JPanel form(String[] labels, JTextField[] fields) {
        JPanel p = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        for (int i = 0; i < labels.length; i++) { p.add(new JLabel(labels[i])); p.add(fields[i]); }
        return p;
    }

    private void crearInstructor() { crearUsuario(true); }
    private void crearEstudiante() { crearUsuario(false); }

    private void crearUsuario(boolean instructor) {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código", "Nombre", "Fecha nacimiento", "Género", "Contraseña"}, f), instructor ? "Crear Instructor" : "Crear Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = instructor ? controller.crearInstructor(f[0].getText(), f[1].getText(), f[2].getText(), f[3].getText(), f[4].getText()) : controller.crearEstudiante(f[0].getText(), f[1].getText(), f[2].getText(), f[3].getText(), f[4].getText());
            JOptionPane.showMessageDialog(this, ok ? "Registro creado." : "No se pudo crear. Código duplicado o datos inválidos.");
            cargarUsuarios();
        }
    }

    private void actualizarInstructor() { actualizarUsuario(true); }
    private void actualizarEstudiante() { actualizarUsuario(false); }

    private void actualizarUsuario(boolean instructor) {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código", "Nuevo nombre", "Nueva contraseña"}, f), "Actualizar", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = instructor ? controller.actualizarInstructor(f[0].getText(), f[1].getText(), f[2].getText()) : controller.actualizarEstudiante(f[0].getText(), f[1].getText(), f[2].getText());
            JOptionPane.showMessageDialog(this, ok ? "Actualizado." : "No encontrado.");
            cargarUsuarios();
        }
    }

    private void eliminarInstructor() { eliminarUsuario(true); }
    private void eliminarEstudiante() { eliminarUsuario(false); }

    private void eliminarUsuario(boolean instructor) {
        String codigo = JOptionPane.showInputDialog(this, "Código a eliminar:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        int c = JOptionPane.showConfirmDialog(this, "¿Confirmar eliminación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            boolean ok = instructor ? controller.eliminarInstructor(codigo) : controller.eliminarEstudiante(codigo);
            JOptionPane.showMessageDialog(this, ok ? "Eliminado." : "No encontrado.");
            cargarUsuarios();
        }
    }

    private void crearCurso() {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código", "Nombre", "Descripción", "Créditos"}, f), "Crear Curso", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.crearCurso(f[0].getText(), f[1].getText(), f[2].getText(), Integer.parseInt(f[3].getText()));
                JOptionPane.showMessageDialog(this, ok ? "Curso creado." : "No se pudo crear.");
                cargarCursos();
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Créditos inválidos."); }
        }
    }

    private void actualizarCurso() {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código", "Nombre", "Descripción", "Créditos"}, f), "Actualizar Curso", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.actualizarCurso(f[0].getText(), f[1].getText(), f[2].getText(), Integer.parseInt(f[3].getText()));
                JOptionPane.showMessageDialog(this, ok ? "Curso actualizado." : "No encontrado.");
                cargarCursos();
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Créditos inválidos."); }
        }
    }

    private void eliminarCurso() {
        String codigo = JOptionPane.showInputDialog(this, "Código del curso:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        boolean ok = controller.eliminarCurso(codigo);
        JOptionPane.showMessageDialog(this, ok ? "Curso eliminado." : "No encontrado.");
        cargarCursos();
    }

    private void crearSeccion() {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código sección", "Código curso", "Semestre", "Horario"}, f), "Crear Sección", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = controller.crearSeccion(f[0].getText(), f[1].getText(), f[2].getText(), f[3].getText());
            JOptionPane.showMessageDialog(this, ok ? "Sección creada." : "No se pudo crear. Verifica curso o duplicado.");
            cargarSecciones();
        }
    }

    private void asignarInstructor() {
        JTextField[] f = {vista.UIStyle.field(), vista.UIStyle.field()};
        int r = JOptionPane.showConfirmDialog(this, form(new String[]{"Código instructor", "Código sección"}, f), "Asignar Instructor", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = controller.asignarInstructor(f[0].getText(), f[1].getText());
            JOptionPane.showMessageDialog(this, ok ? "Asignado." : "No se pudo asignar.");
            cargarSecciones();
        }
    }

    private void exportarPDF() {
        try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarPDFBasico(tabla, "ReporteSistema")); }
        catch (Exception e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void exportarCSV() {
        try { JOptionPane.showMessageDialog(this, "Archivo generado: " + ReporteUtil.exportarCSV(tabla, "ReporteSistema")); }
        catch (Exception e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void cerrarSesion() {
        if (monitor != null) monitor.detener();
        controller.logout(admin);
        dispose();
        new vista.LoginFrameTop(controller);
    }
}
