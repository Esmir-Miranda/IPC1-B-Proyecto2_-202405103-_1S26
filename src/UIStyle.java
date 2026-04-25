package vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class UIStyle {
    public static final Color BG = new Color(15, 23, 42);
    public static final Color SIDEBAR = new Color(17, 24, 39);
    public static final Color CARD = new Color(30, 41, 59);
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color DANGER = new Color(220, 38, 38);
    public static final Color TEXT = new Color(248, 250, 252);
    public static final Color TEXT_SOFT = new Color(203, 213, 225);

    public static Font titleFont() { return new Font("Segoe UI", Font.BOLD, 26); }
    public static Font subtitleFont() { return new Font("Segoe UI", Font.BOLD, 18); }
    public static Font normalFont() { return new Font("Segoe UI", Font.PLAIN, 14); }
    public static Font buttonFont() { return new Font("Segoe UI", Font.BOLD, 14); }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont());
        button.setBackground(PRIMARY);
        button.setForeground(TEXT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        return button;
    }

    public static JButton createDangerButton(String text) {
        JButton button = createButton(text);
        button.setBackground(DANGER);
        return button;
    }

    public static JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(titleFont());
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel createSubtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(subtitleFont());
        label.setForeground(TEXT);
        return label;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(normalFont());
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(148, 163, 184)), new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(normalFont());
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(148, 163, 184)), new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    public static JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setFont(normalFont());
        area.setBackground(CARD);
        area.setForeground(TEXT);
        area.setCaretColor(TEXT);
        area.setBorder(new EmptyBorder(12, 12, 12, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }

    public static JTable styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(normalFont());
        table.getTableHeader().setFont(buttonFont());
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(TEXT);
        table.setSelectionBackground(new Color(191, 219, 254));
        table.setGridColor(new Color(226, 232, 240));
        return table;
    }
}
