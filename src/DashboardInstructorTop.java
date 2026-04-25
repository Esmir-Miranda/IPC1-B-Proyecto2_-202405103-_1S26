package vista;
        sidebar.setPreferredSize(new Dimension(250, 650));
        sidebar.setBackground(UIStyle.SIDEBAR);
        sidebar.setLayout(new GridLayout(4, 1, 10, 10));

JButton btnResumen = UIStyle.createButton("Resumen");
JButton btnTablaUsuarios = UIStyle.createButton("Usuarios");
JButton btnTablaCursos = UIStyle.createButton("Cursos");
JButton btnLogout = UIStyle.createDangerButton("Cerrar Sesión");

        sidebar.add(btnResumen);
        sidebar.add(btnTablaUsuarios);
        sidebar.add(btnTablaCursos);
        sidebar.add(btnLogout);

JTable tabla = UIStyle.styleTable(new JTable());
JTextArea top = UIStyle.createTextArea();
        top.setText("Bienvenido instructor: " + instructor.getNombre());

JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIStyle.BG);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

JPanel header = UIStyle.createCard();
        header.setLayout(new BorderLayout());
        header.add(UIStyle.createTitle("Módulo del Instructor"), BorderLayout.WEST);

JPanel console = UIStyle.createCard();
        console.setLayout(new BorderLayout());
        console.add(new JScrollPane(top), BorderLayout.NORTH);
        console.add(new JScrollPane(tabla), BorderLayout.CENTER);

        center.add(header, BorderLayout.NORTH);
        center.add(console, BorderLayout.CENTER);

add(sidebar, BorderLayout.WEST);
add(center, BorderLayout.CENTER);

        btnResumen.addActionListener(e -> top.setText("Este panel puede ampliarse con notas, secciones y reportes por instructor."));
        btnTablaUsuarios.addActionListener(e -> tabla.setModel(new javax.swing.table.DefaultTableModel(controller.getSistema().getUsuariosTableData(), controller.getSistema().getUsuariosColumns())));
        btnTablaCursos.addActionListener(e -> tabla.setModel(new javax.swing.table.DefaultTableModel(controller.getSistema().getCursosTableData(), controller.getSistema().getCursosColumns())));
        btnLogout.addActionListener(e -> {
        controller.logout(instructor);
dispose();
            new LoginFrameTop(controller);
        });

setVisible(true);
    }
            }