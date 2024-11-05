package GUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditWindow extends JFrame {
    private JTextField gpsField1, gpsField2, supisneCisloField, popisField;
    private JButton confirmButton, cancelButton;

    public EditWindow(String geografickyObjekt, String gps1, String gps2, String supisneCislo, String popis) {
        setTitle("Editacia zaznamu");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Geograficky objekt:"));
        add(new JLabel(geografickyObjekt));

        add(new JLabel("GPS 1:"));
        gpsField1 = new JTextField(gps1);
        add(gpsField1);

        add(new JLabel("GPS 2:"));
        gpsField2 = new JTextField(gps2);
        add(gpsField2);

        add(new JLabel("Súpisné číslo:"));
        supisneCisloField = new JTextField(supisneCislo);
        add(supisneCisloField);

        add(new JLabel("Popis:"));
        popisField = new JTextField(popis);
        add(popisField);

        confirmButton = new JButton("Potvrdiť");
        cancelButton = new JButton("Zrušiť");

        add(confirmButton);
        add(cancelButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void addConfirmButtonListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public String getGeografickyObjekt() {
        return ((JLabel) getContentPane().getComponent(1)).getText();
    }

    public String getGps1() {
        return gpsField1.getText();
    }

    public String getGps2() {
        return gpsField2.getText();
    }

    public String getSupisneCislo() {
        return supisneCisloField.getText();
    }

    public String getPopis() {
        return popisField.getText();
    }
}
