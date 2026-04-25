package vista;
public DashboardEstudianteTop(AppController controller, Usuario estudiante) {
    setTitle("Panel Estudiante");
    setSize(1100, 650);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel sidebar = new JPanel();
    sidebar.setPreferredSize(new Dimension(250, 650));
    sidebar.setBackground(UIStyle.SIDEBAR);
    sidebar.setLayout(new GridLayout(4, 1, 10, 10));

    JButton btnPerfil = UIStyle.createButton("Mi Perfil");
    JButton btnUsuarios = UIStyle.createButton("Usuarios");
    JButton btnCursos = UIStyle.createButton("Cursos");
    JButton btnLogout = UIStyle.createDangerButton("Cerrar Sesión");

    sidebar.add(btnPerfil);
    sidebar.add(btnUsuarios);
    sidebar.add(btnCursos);
    sidebar.add(btnLogout);

    JTextArea info = UIStyle.createTextArea();
    info.setText("Bienvenido estudiante: " + estudiante.getNombre());

    JTable tabla = UIStyle.styleTable(new JTable());

    JPanel center = new JPanel(new BorderLayout());
    center.setBackground(UIStyle.BG);
    center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel header = UIStyle.createCard();
    header.setLayout(new BorderLayout());
    header.add(UIStyle.createTitle("Módulo del Estudiante"), BorderLayout.WEST);

    JPanel console = UIStyle.createCard();
    console.setLayout(new BorderLayout());
    console.add(new JScrollPane(info), BorderLayout.NORTH);
    console.add(new JScrollPane(tabla), BorderLayout.CENTER);

    center.add(header, BorderLayout.NORTH);
    center.add(console, BorderLayout.CENTER);

    add(sidebar, BorderLayout.WEST);
    add(center, BorderLayout.CENTER);

    btnPerfil.addActionListener(e -> info.setText(
            "Código: " + estudiante.getCodigo() + "\n" +
                    "Nombre: " + estudiante.getNombre() + "\n" +
                    "Rol: " + estudiante.getRol()
    ));
    btnUsuarios.addActionListener(e -> tabla.setModel(new javax.swing.table.DefaultTableModel(controller.getSistema().getUsuariosTableData(), controller.getSistema().getUsuariosColumns())));
    btnCursos.addActionListener(e -> tabla.setModel(new javax.swing.table.DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
    btnLogout.addActionListener(e -> {
        controller.logout(estudiante);
        dispose();
        new LoginFrameTop(controller);
    });

    setVisible(true);
}
}