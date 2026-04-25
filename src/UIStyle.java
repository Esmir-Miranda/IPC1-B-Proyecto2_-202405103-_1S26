package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIStyle {
    public static final Color BG = new Color(15, 23, 42);
    public static final Color SIDEBAR = new Color(17, 24, 39);
    public static final Color CARD = new Color(30, 41, 59);
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color DANGER = new Color(220, 38, 38);
    public static final Color TEXT = new Color(248, 250, 252);
    public static final Color SOFT = new Color(203, 213, 225);

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setForeground(TEXT);
        b.setBackground(PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 14, 10, 14));
        return b;
    }

    public static JButton danger(String text) {
        JButton b = button(text);
        b.setBackground(DANGER);
        return b;
    }

    public static JTextField field() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return f;
    }

    public static JPasswordField password() {
        JPasswordField f = new JPasswordField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return f;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 26));
        l.setForeground(TEXT);
        return l;
    }

    public static JTable table(JTable t) {
        t.setRowHeight(28);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.getTableHeader().setBackground(PRIMARY);
        t.getTableHeader().setForeground(TEXT);
        t.setSelectionBackground(new Color(191, 219, 254));
        return t;
    }
}
