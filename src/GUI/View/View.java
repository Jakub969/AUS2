package GUI.View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton searchButton;
    private JTextField dlzkaField;
    private JTextField sirkaField;
    private JTextArea resultArea;

    private JTextField supisneCisloField;
    private JTextField popisField;
    private JTextField gpsPositionsField;
    private JButton addButton;

    public View() {
        searchButton = new JButton("Vyhladaj");
        dlzkaField = new JTextField(10);
        sirkaField = new JTextField(10);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        supisneCisloField = new JTextField(10);
        popisField = new JTextField(10);
        gpsPositionsField = new JTextField(30);
        addButton = new JButton("Pridaj Nehnutelnost");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLayout(new java.awt.FlowLayout());
        this.add(new JLabel("Dlzka:"));
        this.add(dlzkaField);
        this.add(new JLabel("Sirka:"));
        this.add(sirkaField);
        this.add(searchButton);
        this.add(new JScrollPane(resultArea));

        this.add(new JLabel("Supisne Cislo:"));
        this.add(supisneCisloField);
        this.add(new JLabel("Popis:"));
        this.add(popisField);
        this.add(new JLabel("GPS Pozicie:"));
        this.add(gpsPositionsField);
        this.add(addButton);
    }

    public String getDlzka() {
        return dlzkaField.getText();
    }

    public String getSirka() {
        return sirkaField.getText();
    }

    public void setResultText(String text) {
        resultArea.setText(text);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getSupisneCislo() {
        return supisneCisloField.getText();
    }

    public String getPopis() {
        return popisField.getText();
    }

    public String getGpsPositions() {
        return gpsPositionsField.getText();
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
}
