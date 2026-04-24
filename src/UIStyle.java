package vista;
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
    field.setBackground(new Color(241, 245, 249));
    field.setForeground(Color.BLACK);
    field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(148, 163, 184), 1),
            new EmptyBorder(8, 10, 8, 10)
    ));
    return field;
}

public static JPasswordField createPasswordField() {
    JPasswordField field = new JPasswordField();
    field.setFont(normalFont());
    field.setBackground(new Color(241, 245, 249));
    field.setForeground(Color.BLACK);
    field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(148, 163, 184), 1),
            new EmptyBorder(8, 10, 8, 10)
    ));
    return field;
}

public static JTextArea createTextArea() {
    JTextArea area = new JTextArea();
    area.setFont(normalFont());
    area.setBackground(CARD);
    area.setForeground(TEXT);
    area.setCaretColor(TEXT);
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setBorder(new EmptyBorder(12, 12, 12, 12));
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
    table.setBackground(Color.WHITE);
    table.setSelectionBackground(new Color(191, 219, 254));
    table.setGridColor(new Color(226, 232, 240));
    return table;
}
}