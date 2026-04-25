package vista;
JButton btnLogin = UIStyle.createButton("Entrar al sistema");
        btnLogin.addActionListener(e -> iniciarSesion());

        loginCard.add(lblLogin);
        loginCard.add(Box.createVerticalStrut(20));
        loginCard.add(lblCodigo);
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(txtCodigo);
        loginCard.add(Box.createVerticalStrut(15));
        loginCard.add(lblPass);
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(txtPassword);
        loginCard.add(Box.createVerticalStrut(25));
        loginCard.add(btnLogin);

        rightPanel.add(loginCard);

add(leftPanel);
add(rightPanel);

setVisible(true);
    }

private void iniciarSesion() {
    String codigo = txtCodigo.getText().trim();
    String pass = new String(txtPassword.getPassword());

    if (codigo.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Completa todos los campos.");
        return;
    }

    Usuario usuario = controller.login(codigo, pass);

    if (usuario == null) {
        JOptionPane.showMessageDialog(this, "Credenciales inválidas.");
        return;
    }

    dispose();

    switch (usuario.getRol()) {
        case "ADMINISTRADOR":
            new DashboardAdminTop(controller, usuario);
            break;
        case "INSTRUCTOR":
            new DashboardInstructorTop(controller, usuario);
            break;
        case "ESTUDIANTE":
            new DashboardEstudianteTop(controller, usuario);
            break;
    }
}
}