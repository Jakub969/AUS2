package GUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton tlacidloVyhladavaniaNehnutelnosti, tlacidloVyhladavaniaParciel, tlacidloVyhladavaniaVsetkych, tlacidloUpravovania, tlacidloMazania, tlacidloPridaniaNehnutelnosti, tlacidloPridaniaParcely, vygenerujDataButton, tlacidloUkladania, tlacidloNacitania, tlacidloVypisaniaVsetkeho;
    private JTextField dlzkaField1, sirkaField1, dlzkaField2, sirkaField2, supisneCisloNehnutelnostiField, popisNehnutelnostiField, supisneCisloParcelyField, popisParcelyField,
            sirkaNehnutelnostiField1, sirkaNehnutelnostiField2, dlzkaNehnutelnostiField1, dlzkaNehnutelnostiField2, sirkaParcelyField1, sirkaParcelyField2, dlzkaParcelyField1, dlzkaParcelyField2, pocetNehnutelnostiField, pocetParcielField, pravdepodobnostPrekrytiaField;
    private JTextArea zobrazenieVysledkov;
    private JFrame frame;
    private JTable resultTable;
    private TabulkaVysledkov tableModel;
    private JComboBox<String> sirkaComboBox1, dlzkaComboBox1, sirkaComboBox2, dlzkaComboBox2, pridanieSirkaNehnutelnostiComboBox1, pridanieDlzkaNehnutelnostiComboBox1, pridanieSirkaNehnutelnostiComboBox2, pridanieDlzkaNehnutelnostiComboBox2, pridanieSirkaParcelyComboBox1,
            pridanieSirkaParcelyComboBox2, pridanieDlzkaParcelyComboBox1, pridanieDlzkaParcelyComboBox2;

    /**
     * Konstruktor triedy View
     */
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
        tlacidloVypisaniaVsetkeho = new JButton("Vypis Vsetko");
        tlacidloUpravovania = new JButton("Edituj");
        tlacidloMazania = new JButton("Vymaz");

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

        tableModel = new TabulkaVysledkov();
        resultTable = new JTable(tableModel);

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

        JPanel actionPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Funkcie"));
        actionPanel.add(tlacidloVyhladavaniaNehnutelnosti);
        actionPanel.add(tlacidloVyhladavaniaParciel);
        actionPanel.add(tlacidloVyhladavaniaVsetkych);
        actionPanel.add(tlacidloUkladania);
        actionPanel.add(tlacidloNacitania);
        actionPanel.add(tlacidloVypisaniaVsetkeho);
        actionPanel.add(tlacidloUpravovania);
        actionPanel.add(tlacidloMazania);

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

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(780, 170));

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Výsledky"));
        resultPanel.add(scrollPane, BorderLayout.CENTER);

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

    public void addResult(String geoObjekt, String gps1, String gps2, String supisneCislo, String popis, String zoznam) {
        tableModel.addRow(geoObjekt, gps1, gps2, supisneCislo, popis, zoznam);
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

    // Listener Methods
    public void searchNehnutelnostButtonListener(ActionListener listener) {
        tlacidloVyhladavaniaNehnutelnosti.addActionListener(listener);
    }

    public void clearResults() {
    tableModel.clear();
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

    public void addPrintAllButtonListener(ActionListener listener) {
        tlacidloVypisaniaVsetkeho.addActionListener(listener);
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

    public char getSirkaParcelyComboBox1() {
        return ((String) pridanieSirkaParcelyComboBox1.getSelectedItem()).charAt(0);
    }

    public String getSirkaParcely1() {
        return sirkaParcelyField1.getText();
    }

    public char getDlzkaParcelyComboBox1() {
        return ((String) pridanieDlzkaParcelyComboBox1.getSelectedItem()).charAt(0);
    }

    public String getDlzkaParcely1() {
        return dlzkaParcelyField1.getText();
    }

    public char getSirkaParcelyComboBox2() {
        return ((String) pridanieSirkaParcelyComboBox2.getSelectedItem()).charAt(0);
    }

    public String getSirkaParcely2() {
        return sirkaParcelyField2.getText();
    }

    public char getDlzkaParcelyComboBox2() {
        return ((String) pridanieDlzkaParcelyComboBox2.getSelectedItem()).charAt(0);
    }

    public String getDlzkaParcely2() {
        return dlzkaParcelyField2.getText();
    }

    public int getSelectedRow() {
        return resultTable.getSelectedRow();
    }

    public String getGps1(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 1);
    }

    public String getGps2(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 2);
    }

    public String getSupisneCislo(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 3);
    }

    public String getPopis(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 4);
    }

    public String getGeografickyObjekt(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 0);
    }

    public void removeRow(int selectedRow) {
        tableModel.removeRow(selectedRow);
    }
}
