package vista;

import controlador.AppController;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrameTop extends JFrame {
    private AppController controller;
    private JTextField txtCodigo;
    private JPasswordField txtPassword;

    public LoginFrameTop(AppController controller) {
        this.controller = controller;
        setTitle("Sancarlista Academy");
        setSize(880, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(vista.UIStyle.BG);
        left.setBorder(new EmptyBorder(45, 45, 45, 45));
        JLabel titulo = vista.UIStyle.title("Sancarlista Academy");
        JLabel desc = new JLabel("<html>Sistema académico desarrollado en Java con MVC, POO, arreglos, serialización, JTable, reportes y bitácora.</html>");
        desc.setForeground(vista.UIStyle.SOFT);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(vista.UIStyle.BG);
        box.add(titulo);
        box.add(Box.createVerticalStrut(15));
        box.add(desc);
        left.add(box, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Color.WHITE);
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(340, 300));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        JLabel login = new JLabel("Iniciar sesión");
        login.setFont(new Font("Segoe UI", Font.BOLD, 24));
        txtCodigo = vista.UIStyle.field();
        txtPassword = vista.UIStyle.password();
        JButton btn = vista.UIStyle.button("Entrar al sistema");
        btn.addActionListener(e -> login());
        card.add(login);
        card.add(Box.createVerticalStrut(18));
        card.add(new JLabel("Código"));
        card.add(txtCodigo);
        card.add(Box.createVerticalStrut(12));
        card.add(new JLabel("Contraseña"));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(22));
        card.add(btn);
        right.add(card);

        add(left);
        add(right);
        setVisible(true);
    }

    private void login() {
        Usuario usuario = controller.login(txtCodigo.getText().trim(), new String(txtPassword.getPassword()));
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
