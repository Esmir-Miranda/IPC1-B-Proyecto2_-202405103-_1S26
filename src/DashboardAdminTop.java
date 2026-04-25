package vista;
        panel.add(new JLabel("Código:")); panel.add(codigo);
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Fecha nacimiento:")); panel.add(fecha);
        panel.add(new JLabel("Género:")); panel.add(genero);
        panel.add(new JLabel("Contraseña:")); panel.add(pass);

int r = JOptionPane.showConfirmDialog(this, panel, "Crear Instructor", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
boolean ok = controller.crearInstructor(codigo.getText(), nombre.getText(), fecha.getText(), genero.getText(), pass.getText());
            JOptionPane.showMessageDialog(this, ok ? "Instructor creado." : "No se pudo crear.");
cargarTablaUsuarios();
        }
                }

private void crearEstudiante() {
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

    int r = JOptionPane.showConfirmDialog(this, panel, "Crear Estudiante", JOptionPane.OK_CANCEL_OPTION);
    if (r == JOptionPane.OK_OPTION) {
        boolean ok = controller.crearEstudiante(codigo.getText(), nombre.getText(), fecha.getText(), genero.getText(), pass.getText());
        JOptionPane.showMessageDialog(this, ok ? "Estudiante creado." : "No se pudo crear.");
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
            JOptionPane.showMessageDialog(this, ok ? "Curso creado." : "No se pudo crear.");
            cargarTablaCursos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Créditos inválidos.");
        }
    }
}

private void exportarPDFActual() {
    try {
        String archivo = PDFReportUtil.exportarTablaPDF("Reporte del Sistema", table, "ReporteSistema");
        JOptionPane.showMessageDialog(this, "PDF generado: " + archivo);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error exportando PDF: " + e.getMessage());
    }
}

private void cerrarSesion() {
    detenerThreads();
    controller.logout(admin);
    dispose();
    new LoginFrameTop(controller);
}