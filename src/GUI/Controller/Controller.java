package GUI.Controller;

import GUI.Model.Model;
import GUI.View.View;
import triedy.GPS;
import triedy.Nehnutelnost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.view.addSearchButtonListener(new SearchButtonListener());
        this.view.addAddButtonListener(new AddButtonListener());
    }

    class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double dlzka = Double.parseDouble(view.getDlzka());
                double sirka = Double.parseDouble(view.getSirka());
                List<Nehnutelnost> results = model.searchNehnutelnosti(dlzka, sirka);
                StringBuilder resultText = new StringBuilder();
                for (Nehnutelnost nehnutelnost : results) {
                    resultText.append(nehnutelnost.toString()).append("\n");
                }
                view.setResultText(resultText.toString());
            } catch (NumberFormatException ex) {
                view.setResultText("Invalid GPS coordinates.");
            }
        }
    }

    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int supisneCislo = Integer.parseInt(view.getSupisneCislo());
                String popis = view.getPopis();
                String[] gpsPositionsStr = view.getGpsPositions().split(";");
                List<GPS> gpsPositions = new ArrayList<>();
                for (String gpsStr : gpsPositionsStr) {
                    String[] parts = gpsStr.split(",");
                    char dlzkaChar = parts[0].charAt(0);
                    double dlzka = Double.parseDouble(parts[1]);
                    char sirkaChar = parts[2].charAt(0);
                    double sirka = Double.parseDouble(parts[3]);
                    gpsPositions.add(new GPS(dlzkaChar, dlzka, sirkaChar, sirka));
                }
                model.addNehnutelnost(supisneCislo, popis, gpsPositions);
                view.setResultText("Nehnutelnost (Supisne Cislo: " + supisneCislo + ", Popis: " + popis + ", GPS Pozicie: " + gpsPositions.getFirst().getPoziciaDlzky() + " " + gpsPositions.getFirst().getPoziciaSirky() + ") pridaná!");
            } catch (NumberFormatException ex) {
                view.setResultText("Invalid input.");
            }
        }
    }
}
