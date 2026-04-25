package vista;

import controlador.AppController;
import modelo.Usuario;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class LoginFrameTop extends JFrame {
    private final AppController controller;
    private JTextField txtCodigo;
    private JPasswordField txtPassword;

    public LoginFrameTop(AppController controller) {
        this.controller = controller;
        setTitle("Sancarlista Academy");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(UIStyle.BG);
        leftPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblBrand = UIStyle.createTitle("Sancarlista Academy");
        JLabel lblDesc = new JLabel("<html>Plataforma académica moderna para gestionar cursos, instructores y estudiantes.</html>");
        lblDesc.setFont(UIStyle.normalFont());
        lblDesc.setForeground(UIStyle.TEXT_SOFT);

        JPanel brandBox = new JPanel();
        brandBox.setBackground(UIStyle.BG);
        brandBox.setLayout(new BoxLayout(brandBox, BoxLayout.Y_AXIS));
        brandBox.add(lblBrand);
        brandBox.add(Box.createVerticalStrut(15));
        brandBox.add(lblDesc);
        leftPanel.add(brandBox, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(248, 250, 252));

        JPanel loginCard = new JPanel();
        loginCard.setPreferredSize(new Dimension(340, 320));
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblLogin = new JLabel("Iniciar sesión");
        lblLogin.setFont(UIStyle.titleFont());
        lblLogin.setForeground(UIStyle.BG);
        txtCodigo = UIStyle.createTextField();
        txtPassword = UIStyle.createPasswordField();
        JButton btnLogin = UIStyle.createButton("Entrar al sistema");
        btnLogin.addActionListener(e -> iniciarSesion());

        loginCard.add(lblLogin);
        loginCard.add(Box.createVerticalStrut(20));
        loginCard.add(new JLabel("Código"));
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(txtCodigo);
        loginCard.add(Box.createVerticalStrut(15));
        loginCard.add(new JLabel("Contraseña"));
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
        if (usuario.getRol().equals("ADMINISTRADOR")) new vista.DashboardAdminTop(controller, usuario);
        else if (usuario.getRol().equals("INSTRUCTOR")) new vista.DashboardInstructorTop(controller, usuario);
        else new vista.DashboardEstudianteTop(controller, usuario);
    }
}
