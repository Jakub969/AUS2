package GUI.View;

import GUI.Model.Model;
import triedy.Nehnutelnost;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {
    private JButton searchButton;
    private JTextField dlzkaField;
    private JTextField sirkaField;
    private JTextArea resultArea;

    public View() {
        searchButton = new JButton("Vyhladaj");
        dlzkaField = new JTextField(10);
        sirkaField = new JTextField(10);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new java.awt.FlowLayout());
        this.add(new JLabel("Dlzka:"));
        this.add(dlzkaField);
        this.add(new JLabel("Sirka:"));
        this.add(sirkaField);
        this.add(searchButton);
        this.add(new JScrollPane(resultArea));
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
}
