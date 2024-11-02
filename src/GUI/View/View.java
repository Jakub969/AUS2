package GUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton searchButton, searchParcelyButton, searchAllButton, editButton, deleteButton, addButton;
    private JTextField dlzkaField1, sirkaField1, dlzkaField2, sirkaField2, supisneCisloField, popisField, gpsPositionsField;
    private JTextArea resultArea;

    private JComboBox<String> sirkaOrientationComboBox1, dlzkaOrientationComboBox1 , sirkaOrientationComboBox2, dlzkaOrientationComboBox2;

    public View() {
        searchButton = new JButton("Vyhladaj Nehnutelnosti");
        searchParcelyButton = new JButton("Vyhladaj Parcely");
        searchAllButton = new JButton("Vyhladaj Vsetko");
        editButton = new JButton("Edituj");
        deleteButton = new JButton("Vymaz");
        addButton = new JButton("Pridaj Nehnutelnost");

        dlzkaField1 = new JTextField(10);
        sirkaField1 = new JTextField(10);
        dlzkaField2 = new JTextField(10);
        sirkaField2 = new JTextField(10);
        supisneCisloField = new JTextField(10);
        popisField = new JTextField(10);
        gpsPositionsField = new JTextField(30);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        sirkaOrientationComboBox1 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaOrientationComboBox1 = new JComboBox<>(new String[]{"E", "W"});
        sirkaOrientationComboBox2 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaOrientationComboBox2 = new JComboBox<>(new String[]{"E", "W"});

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLayout(new BorderLayout(10, 10));

        JPanel gpsSearchPanel1 = new JPanel(new GridLayout(3, 2, 5, 5));
        gpsSearchPanel1.setBorder(BorderFactory.createTitledBorder("Bod 1 - GPS Vyhľadávanie"));

        gpsSearchPanel1.add(new JLabel("Zemepisná šírka:"));
        JPanel sirkaPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sirkaPanel1.add(sirkaOrientationComboBox1);
        sirkaPanel1.add(sirkaField1);
        gpsSearchPanel1.add(sirkaPanel1);

        gpsSearchPanel1.add(new JLabel("Zemepisná dĺžka:"));
        JPanel dlzkaPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dlzkaPanel1.add(dlzkaOrientationComboBox1);
        dlzkaPanel1.add(dlzkaField1);
        gpsSearchPanel1.add(dlzkaPanel1);

        // Panel for GPS Search (Bod 1)
        JPanel gpsSearchPanel2 = new JPanel(new GridLayout(3, 2, 5, 5));
        gpsSearchPanel2.setBorder(BorderFactory.createTitledBorder("Bod 2 - GPS Vyhľadávanie"));

        gpsSearchPanel2.add(new JLabel("Zemepisná šírka:"));
        JPanel sirkaPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sirkaPanel2.add(sirkaOrientationComboBox2);
        sirkaPanel2.add(sirkaField2);
        gpsSearchPanel2.add(sirkaPanel2);

        gpsSearchPanel2.add(new JLabel("Zemepisná dĺžka:"));
        JPanel dlzkaPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dlzkaPanel2.add(dlzkaOrientationComboBox2);
        dlzkaPanel2.add(dlzkaField2);
        gpsSearchPanel2.add(dlzkaPanel2);

        JPanel actionPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Funkcie"));
        actionPanel.add(searchButton);
        actionPanel.add(searchParcelyButton);
        actionPanel.add(searchAllButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        JPanel propertyPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        propertyPanel.setBorder(BorderFactory.createTitledBorder("Pridanie/Editácia Nehnuteľnosti"));
        propertyPanel.add(new JLabel("Súpisné číslo:"));
        propertyPanel.add(supisneCisloField);
        propertyPanel.add(new JLabel("Popis:"));
        propertyPanel.add(popisField);
        propertyPanel.add(new JLabel("GPS Pozície:"));
        propertyPanel.add(gpsPositionsField);

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(addButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Výsledky"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel gpsPanelContainer = new JPanel(new GridLayout(2, 1, 10, 10));
        gpsPanelContainer.add(gpsSearchPanel1);
        gpsPanelContainer.add(gpsSearchPanel2);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(gpsPanelContainer, BorderLayout.NORTH);
        leftPanel.add(actionPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(propertyPanel, BorderLayout.NORTH);
        rightPanel.add(addButtonPanel, BorderLayout.CENTER);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
        this.add(resultPanel, BorderLayout.SOUTH);
    }

    public String getDlzka() {
        return dlzkaField1.getText();
    }

    public String getSirka() {
        return sirkaField1.getText();
    }

    public String getSirkaOrientation() {
        return (String) sirkaOrientationComboBox1.getSelectedItem();
    }

    public String getDlzkaOrientation() {
        return (String) dlzkaOrientationComboBox1.getSelectedItem();
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

    public void setResultText(String text) {
        resultArea.setText(text);
    }

    // Listener Methods
    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addSearchParcelyButtonListener(ActionListener listener) {
        searchParcelyButton.addActionListener(listener);
    }

    public void addSearchAllButtonListener(ActionListener listener) {
        searchAllButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
}
