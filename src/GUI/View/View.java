package GUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton tlacidloVyhladavaniaNehnutelnosti, tlacidloVyhladavaniaParciel, tlacidloVyhladavaniaVsetkych, tlacidloUpravovania, tlacidloMazania, tlacidloPridania;
    private JTextField dlzkaField1, sirkaField1, dlzkaField2, sirkaField2, supisneCisloField, popisField, gpsPozicieField;
    private JTextArea zobrazenieVysledkov;

    private JComboBox<String> sirkaComboBox1, dlzkaComboBox1, sirkaComboBox2, dlzkaComboBox2;

    public View() {
        tlacidloVyhladavaniaNehnutelnosti = new JButton("Vyhladaj Nehnutelnosti");
        tlacidloVyhladavaniaParciel = new JButton("Vyhladaj Parcely");
        tlacidloVyhladavaniaVsetkych = new JButton("Vyhladaj Vsetko");
        tlacidloUpravovania = new JButton("Edituj");
        tlacidloMazania = new JButton("Vymaz");
        tlacidloPridania = new JButton("Pridaj Nehnutelnost");

        dlzkaField1 = new JTextField(10);
        sirkaField1 = new JTextField(10);
        dlzkaField2 = new JTextField(10);
        sirkaField2 = new JTextField(10);
        supisneCisloField = new JTextField(10);
        popisField = new JTextField(10);
        gpsPozicieField = new JTextField(30);
        zobrazenieVysledkov = new JTextArea(10, 30);
        zobrazenieVysledkov.setEditable(false);

        sirkaComboBox1 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaComboBox1 = new JComboBox<>(new String[]{"E", "W"});
        sirkaComboBox2 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaComboBox2 = new JComboBox<>(new String[]{"E", "W"});

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLayout(new BorderLayout(10, 10));

        JPanel gpsSearchPanel1 = new JPanel(new GridLayout(3, 2, 5, 5));
        gpsSearchPanel1.setBorder(BorderFactory.createTitledBorder("Bod 1 - GPS Vyhľadávanie"));

        gpsSearchPanel1.add(new JLabel("Zemepisná šírka:"));
        JPanel sirkaPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sirkaPanel1.add(sirkaComboBox1);
        sirkaPanel1.add(sirkaField1);
        gpsSearchPanel1.add(sirkaPanel1);

        gpsSearchPanel1.add(new JLabel("Zemepisná dĺžka:"));
        JPanel dlzkaPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dlzkaPanel1.add(dlzkaComboBox1);
        dlzkaPanel1.add(dlzkaField1);
        gpsSearchPanel1.add(dlzkaPanel1);

        // Panel for GPS Search (Bod 1)
        JPanel gpsSearchPanel2 = new JPanel(new GridLayout(3, 2, 5, 5));
        gpsSearchPanel2.setBorder(BorderFactory.createTitledBorder("Bod 2 - GPS Vyhľadávanie"));

        gpsSearchPanel2.add(new JLabel("Zemepisná šírka:"));
        JPanel sirkaPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sirkaPanel2.add(sirkaComboBox2);
        sirkaPanel2.add(sirkaField2);
        gpsSearchPanel2.add(sirkaPanel2);

        gpsSearchPanel2.add(new JLabel("Zemepisná dĺžka:"));
        JPanel dlzkaPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dlzkaPanel2.add(dlzkaComboBox2);
        dlzkaPanel2.add(dlzkaField2);
        gpsSearchPanel2.add(dlzkaPanel2);

        JPanel actionPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Funkcie"));
        actionPanel.add(tlacidloVyhladavaniaNehnutelnosti);
        actionPanel.add(tlacidloVyhladavaniaParciel);
        actionPanel.add(tlacidloVyhladavaniaVsetkych);
        actionPanel.add(tlacidloUpravovania);
        actionPanel.add(tlacidloMazania);

        JPanel propertyPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        propertyPanel.setBorder(BorderFactory.createTitledBorder("Pridanie/Editácia Nehnuteľnosti"));
        propertyPanel.add(new JLabel("Súpisné číslo:"));
        propertyPanel.add(supisneCisloField);
        propertyPanel.add(new JLabel("Popis:"));
        propertyPanel.add(popisField);
        propertyPanel.add(new JLabel("GPS Pozície:"));
        propertyPanel.add(gpsPozicieField);

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(tlacidloPridania);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Výsledky"));
        resultPanel.add(new JScrollPane(zobrazenieVysledkov), BorderLayout.CENTER);

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

    public String getDlzka1() {
        return dlzkaField1.getText();
    }

    public String getSirka1() {
        return sirkaField1.getText();
    }

    public char getSirkaOrientation1() {
        return (char) sirkaComboBox1.getSelectedItem();
    }

    public char getDlzkaOrientation1() {
        return (char) dlzkaComboBox1.getSelectedItem();
    }

    public String getDlzka2() {
        return dlzkaField2.getText();
    }

    public String getSirka2() {
        return sirkaField2.getText();
    }

    public char getSirkaOrientation2() {
        return (char) sirkaComboBox2.getSelectedItem();
    }

    public char getDlzkaOrientation2() {
        return (char) dlzkaComboBox2.getSelectedItem();
    }

    public String getSupisneCislo() {
        return supisneCisloField.getText();
    }

    public String getPopis() {
        return popisField.getText();
    }

    public String getGpsPositions() {
        return gpsPozicieField.getText();
    }



    public void setResultText(String text) {
        zobrazenieVysledkov.setText(text);
    }

    // Listener Methods
    public void addSearchButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaNehnutelnosti.addActionListener(listener);
    }

    public void addSearchParcelyButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaParciel.addActionListener(listener);
    }

    public void addSearchAllButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaVsetkych.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        tlacidloUpravovania.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        tlacidloMazania.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        tlacidloPridania.addActionListener(listener);
    }
}
