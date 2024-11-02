package GUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton tlacidloVyhladavaniaNehnutelnosti, tlacidloVyhladavaniaParciel, tlacidloVyhladavaniaVsetkych, tlacidloUpravovania, tlacidloMazania, tlacidloPridaniaNehnutelnosti, tlacidloPridaniaParcely, vygenerujDataButton, tlacidloUkladania, tlacidloNacitania;
    private JTextField dlzkaField1, sirkaField1, dlzkaField2, sirkaField2, supisneCisloNehnutelnostiField, popisNehnutelnostiField, supisneCisloParcelyField, popisParcelyField,
            sirkaNehnutelnostiField1, sirkaNehnutelnostiField2, dlzkaNehnutelnostiField1, dlzkaNehnutelnostiField2, sirkaParcelyField1, sirkaParcelyField2, dlzkaParcelyField1, dlzkaParcelyField2, pocetNehnutelnostiField, pocetParcielField, pravdepodobnostPrekrytiaField;
    private JTextArea zobrazenieVysledkov;

    private JComboBox<String> sirkaComboBox1, dlzkaComboBox1, sirkaComboBox2, dlzkaComboBox2, pridanieSirkaNehnutelnostiComboBox1, pridanieDlzkaNehnutelnostiComboBox1, pridanieSirkaNehnutelnostiComboBox2, pridanieDlzkaNehnutelnostiComboBox2, pridanieSirkaParcelyComboBox1,
            pridanieSirkaParcelyComboBox2, pridanieDlzkaParcelyComboBox1, pridanieDlzkaParcelyComboBox2;

    public View() {
        tlacidloVyhladavaniaNehnutelnosti = new JButton("Vyhladaj Nehnutelnosti");
        tlacidloVyhladavaniaParciel = new JButton("Vyhladaj Parcely");
        tlacidloVyhladavaniaVsetkych = new JButton("Vyhladaj Vsetko");
        tlacidloUpravovania = new JButton("Edituj");
        tlacidloMazania = new JButton("Vymaz");
        tlacidloPridaniaNehnutelnosti = new JButton("Pridaj Nehnutelnost");
        tlacidloPridaniaParcely = new JButton("Pridaj Parcelu");
        tlacidloUkladania = new JButton("Uloz");
        tlacidloNacitania = new JButton("Nacitaj");

        dlzkaField1 = new JTextField(10);
        sirkaField1 = new JTextField(10);
        dlzkaField2 = new JTextField(10);
        sirkaField2 = new JTextField(10);
        supisneCisloNehnutelnostiField = new JTextField(10);
        popisNehnutelnostiField = new JTextField(10);
        supisneCisloParcelyField = new JTextField(10);
        popisParcelyField = new JTextField(10);
        sirkaNehnutelnostiField1 = new JTextField(10);
        dlzkaNehnutelnostiField1 = new JTextField(10);
        sirkaNehnutelnostiField2 = new JTextField(10);
        dlzkaNehnutelnostiField2 = new JTextField(10);
        sirkaParcelyField1 = new JTextField(10);
        dlzkaParcelyField1 = new JTextField(10);
        sirkaParcelyField2 = new JTextField(10);
        dlzkaParcelyField2 = new JTextField(10);
        pocetNehnutelnostiField = new JTextField(10);
        pocetParcielField = new JTextField(10);
        pravdepodobnostPrekrytiaField = new JTextField(10);
        zobrazenieVysledkov = new JTextArea(10, 30);
        zobrazenieVysledkov.setEditable(false);

        sirkaComboBox1 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaComboBox1 = new JComboBox<>(new String[]{"E", "W"});
        sirkaComboBox2 = new JComboBox<>(new String[]{"N", "S"});
        dlzkaComboBox2 = new JComboBox<>(new String[]{"E", "W"});
        pridanieSirkaNehnutelnostiComboBox1 = new JComboBox<>(new String[]{"N", "S"});
        pridanieDlzkaNehnutelnostiComboBox1 = new JComboBox<>(new String[]{"E", "W"});
        pridanieSirkaNehnutelnostiComboBox2 = new JComboBox<>(new String[]{"N", "S"});
        pridanieDlzkaNehnutelnostiComboBox2 = new JComboBox<>(new String[]{"E", "W"});
        pridanieSirkaParcelyComboBox1 = new JComboBox<>(new String[]{"N", "S"});
        pridanieDlzkaParcelyComboBox1 = new JComboBox<>(new String[]{"E", "W"});
        pridanieSirkaParcelyComboBox2 = new JComboBox<>(new String[]{"N", "S"});
        pridanieDlzkaParcelyComboBox2 = new JComboBox<>(new String[]{"E", "W"});

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
        actionPanel.add(tlacidloUkladania);
        actionPanel.add(tlacidloNacitania);

        JPanel propertyPanel1 = new JPanel(new GridLayout(4, 2, 5, 5));
        propertyPanel1.setBorder(BorderFactory.createTitledBorder("Pridanie Nehnuteľnosti"));
        propertyPanel1.add(new JLabel("Súpisné číslo:"));
        propertyPanel1.add(supisneCisloNehnutelnostiField);
        propertyPanel1.add(new JLabel("Popis:"));
        propertyPanel1.add(popisNehnutelnostiField);
        propertyPanel1.add(new JLabel("GPS Pozície 1:"));
        JPanel gpsPozicieNehnutelnostiPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        gpsPozicieNehnutelnostiPanel1.add(pridanieSirkaNehnutelnostiComboBox1);
        gpsPozicieNehnutelnostiPanel1.add(sirkaNehnutelnostiField1);
        gpsPozicieNehnutelnostiPanel1.add(pridanieDlzkaNehnutelnostiComboBox1);
        gpsPozicieNehnutelnostiPanel1.add(dlzkaNehnutelnostiField1);
        propertyPanel1.add(gpsPozicieNehnutelnostiPanel1);
        propertyPanel1.add(new JLabel("GPS Pozície 2:"));
        JPanel gpsPozicieNehnutelnostiPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        gpsPozicieNehnutelnostiPanel2.add(pridanieSirkaNehnutelnostiComboBox2);
        gpsPozicieNehnutelnostiPanel2.add(sirkaNehnutelnostiField2);
        gpsPozicieNehnutelnostiPanel2.add(pridanieDlzkaNehnutelnostiComboBox2);
        gpsPozicieNehnutelnostiPanel2.add(dlzkaNehnutelnostiField2);
        propertyPanel1.add(gpsPozicieNehnutelnostiPanel2);

        JPanel addButtonPanel1 = new JPanel();
        addButtonPanel1.add(tlacidloPridaniaNehnutelnosti);

        JPanel propertyPanel2 = new JPanel(new GridLayout(4, 2, 5, 5));
        propertyPanel2.setBorder(BorderFactory.createTitledBorder("Pridanie Parcely"));
        propertyPanel2.add(new JLabel("Súpisné číslo:"));
        propertyPanel2.add(supisneCisloParcelyField);
        propertyPanel2.add(new JLabel("Popis:"));
        propertyPanel2.add(popisParcelyField);
        propertyPanel2.add(new JLabel("GPS Pozície 1:"));
        JPanel gpsPozicieParcelyPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        gpsPozicieParcelyPanel1.add(pridanieSirkaParcelyComboBox1);
        gpsPozicieParcelyPanel1.add(sirkaParcelyField1);
        gpsPozicieParcelyPanel1.add(pridanieDlzkaParcelyComboBox1);
        gpsPozicieParcelyPanel1.add(dlzkaParcelyField1);
        propertyPanel2.add(gpsPozicieParcelyPanel1);
        propertyPanel2.add(new JLabel("GPS Pozície 2:"));
        JPanel gpsPozicieParcelyPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        gpsPozicieParcelyPanel2.add(pridanieSirkaParcelyComboBox2);
        gpsPozicieParcelyPanel2.add(sirkaParcelyField2);
        gpsPozicieParcelyPanel2.add(pridanieDlzkaParcelyComboBox2);
        gpsPozicieParcelyPanel2.add(dlzkaParcelyField2);
        propertyPanel2.add(gpsPozicieParcelyPanel2);



        JPanel addButtonPanel2 = new JPanel();
        addButtonPanel2.add(tlacidloPridaniaParcely);

        JPanel propertyPanel3 = new JPanel(new GridLayout(4, 1, 5, 5));
        propertyPanel3.setBorder(BorderFactory.createTitledBorder("Generovanie dát"));
        propertyPanel3.add(new JLabel("Počet nehnuteľnosti:"));
        propertyPanel3.add(pocetNehnutelnostiField);
        propertyPanel3.add(new JLabel("Počet parciel:"));
        propertyPanel3.add(pocetParcielField);
        propertyPanel3.add(new JLabel("Pravdepodobnosť prekrytia:"));
        propertyPanel3.add(pravdepodobnostPrekrytiaField);

        vygenerujDataButton = new JButton("Vygeneruj dáta");
        JPanel generateButtonPanel = new JPanel();
        generateButtonPanel.add(vygenerujDataButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Výsledky"));
        resultPanel.add(new JScrollPane(zobrazenieVysledkov), BorderLayout.CENTER);

        JPanel gpsPanelContainer = new JPanel(new GridLayout(3, 1, 10, 10));
        gpsPanelContainer.add(gpsSearchPanel1);
        gpsPanelContainer.add(gpsSearchPanel2);
        gpsPanelContainer.add(actionPanel);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(gpsPanelContainer, BorderLayout.NORTH);

        JPanel rightPanelNehnutelnost = new JPanel(new BorderLayout(10, 10));
        rightPanelNehnutelnost.add(propertyPanel1, BorderLayout.NORTH);
        rightPanelNehnutelnost.add(addButtonPanel1, BorderLayout.CENTER);

        JPanel rightPanelParcela = new JPanel(new BorderLayout(10, 10));
        rightPanelParcela.add(propertyPanel2, BorderLayout.NORTH);
        rightPanelParcela.add(addButtonPanel2, BorderLayout.CENTER);

        JPanel rightPanelGenerovanie = new JPanel(new BorderLayout(10, 10));
        rightPanelGenerovanie.add(propertyPanel3, BorderLayout.NORTH);
        rightPanelGenerovanie.add(generateButtonPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        rightPanel.add(rightPanelNehnutelnost);
        rightPanel.add(rightPanelParcela);
        rightPanel.add(rightPanelGenerovanie);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
        this.add(resultPanel, BorderLayout.SOUTH);
    }

    public void addResult(String resultText, ActionListener editListener, ActionListener deleteListener) {
    JPanel resultPanel = new JPanel(new BorderLayout());
    JTextArea resultArea = new JTextArea(resultText);
    resultArea.setEditable(false);
    resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton editButton = new JButton("Edituj");
    editButton.addActionListener(editListener);
    JButton deleteButton = new JButton("Vymaz");
    deleteButton.addActionListener(deleteListener);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);

    resultPanel.add(buttonPanel, BorderLayout.SOUTH);
    this.zobrazenieVysledkov.add(resultPanel);
    this.zobrazenieVysledkov.setText(resultText);
    //this.zobrazenieVysledkov.revalidate();
    //this.zobrazenieVysledkov.repaint();
}

    public String getDlzka1() {
        return dlzkaField1.getText();
    }

    public String getSirka1() {
        return sirkaField1.getText();
    }

    public char getSirkaOrientation1() {
        return ((String) sirkaComboBox1.getSelectedItem()).charAt(0);
    }

    public char getDlzkaOrientation1() {
        return ((String) dlzkaComboBox1.getSelectedItem()).charAt(0);
    }

    public String getDlzka2() {
        return dlzkaField2.getText();
    }

    public String getSirka2() {
        return sirkaField2.getText();
    }

    public char getSirkaOrientation2() {
        return ((String) sirkaComboBox2.getSelectedItem()).charAt(0);
    }

    public char getDlzkaOrientation2() {
        return ((String) dlzkaComboBox2.getSelectedItem()).charAt(0);
    }

    public String getSupisneCisloNehnutelnosti() {
        return supisneCisloNehnutelnostiField.getText();
    }

    public String getPopisNehnutelnosti() {
        return popisNehnutelnostiField.getText();
    }

    public char getSirkaNehnutelnostiComboBox1() {
        return ((String) pridanieSirkaNehnutelnostiComboBox1.getSelectedItem()).charAt(0);
    }

    public String getSirkaNehnutelnosti1() {
        return sirkaNehnutelnostiField1.getText();
    }

    public char getDlzkaNehnutelnostiComboBox1() {
        return ((String) pridanieDlzkaNehnutelnostiComboBox1.getSelectedItem()).charAt(0);
    }

    public String getDlzkaNehnutelnosti1() {
        return dlzkaNehnutelnostiField1.getText();
    }

    public char getSirkaNehnutelnostiComboBox2() {
        return ((String) pridanieSirkaNehnutelnostiComboBox2.getSelectedItem()).charAt(0);
    }

    public String getSirkaNehnutelnosti2() {
        return sirkaNehnutelnostiField2.getText();
    }

    public char getDlzkaNehnutelnostiComboBox2() {
        return ((String) pridanieDlzkaNehnutelnostiComboBox2.getSelectedItem()).charAt(0);
    }

    public String getDlzkaNehnutelnosti2() {
        return dlzkaNehnutelnostiField2.getText();
    }

    public String getPopisParcely() {
        return popisParcelyField.getText();
    }

    public String getPocetNehnutelnosti() {
        return pocetNehnutelnostiField.getText();
    }

    public String getPocetParciel() {
        return pocetParcielField.getText();
    }

    public String getPravdepodobnostPrekrytia() {
        return pravdepodobnostPrekrytiaField.getText();
    }

    public String getSupisneCisloParcely() {
        return supisneCisloParcelyField.getText();
    }

    public void setResultText(String text) {
        this.zobrazenieVysledkov.removeAll();
        addResult(text, null, null);
    }

    // Listener Methods
    public void searchNehnutelnostButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaNehnutelnosti.addActionListener(listener);
    }

    public void searchParcelaButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaParciel.addActionListener(listener);
    }

    public void addSaveButtonListener(ActionListener listener) {
        tlacidloUkladania.addActionListener(listener);
    }

    public void addLoadButtonListener(ActionListener listener) {
        tlacidloNacitania.addActionListener(listener);
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

    public void addNehnutelnostButtonListener(ActionListener listener) {
        tlacidloPridaniaNehnutelnosti.addActionListener(listener);
    }

    public void addParcelaButtonListener(ActionListener listener) {
        tlacidloPridaniaParcely.addActionListener(listener);
    }

    public void addGenerateButtonListener(ActionListener listener) {
        vygenerujDataButton.addActionListener(listener);
    }
}
