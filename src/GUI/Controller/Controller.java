package GUI.Controller;

import GUI.Model.Model;
import GUI.View.View;
import triedy.GPS;
import triedy.Nehnutelnost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
                double poziciaDlzky1 = Double.parseDouble(view.getDlzka1());
                double poziciaSirky1 = Double.parseDouble(view.getSirka1());

                char sirka1 = view.getSirkaOrientation1();
                char dlzka1 = view.getDlzkaOrientation1();

                double poziciaDlzky2 = Double.parseDouble(view.getDlzka2());
                double poziciaSirky2 = Double.parseDouble(view.getSirka2());

                char sirka2 = view.getSirkaOrientation2();
                char dlzka2 = view.getDlzkaOrientation2();

                GPS pozicia1 = new GPS(dlzka1, poziciaDlzky1, sirka1, poziciaSirky1);
                GPS pozicia2 = new GPS(dlzka2, poziciaDlzky2, sirka2, poziciaSirky2);

                ArrayList<GPS> gpsPositions = new ArrayList<>();
                gpsPositions.add(pozicia1);
                gpsPositions.add(pozicia2);

                List<Nehnutelnost> results = model.vyhladajNehnutelnost(gpsPositions);

                StringBuilder resultText = new StringBuilder();
                for (Nehnutelnost nehnutelnost : results) {
                    resultText.append(nehnutelnost.toString()).append("\n");
                }
                view.setResultText(resultText.toString());
            } catch (NumberFormatException ex) {
                view.setResultText("Nesprávne súradnice GPS.");
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
                    char dlzkaChar = parts[0].charAt(0); // E or W
                    double dlzka = Double.parseDouble(parts[1]);
                    char sirkaChar = parts[2].charAt(0); // N or S
                    double sirka = Double.parseDouble(parts[3]);
                    gpsPositions.add(new GPS(dlzkaChar, dlzka, sirkaChar, sirka));
                }

                model.pridajNehnutelnost(supisneCislo, popis, gpsPositions);

                view.setResultText("Nehnutelnost (Supisne Cislo: " + supisneCislo +
                        ", Popis: " + popis +
                        ", GPS Pozicie: " + gpsPositions.get(0).getPoziciaDlzky() +
                        " " + gpsPositions.get(0).getPoziciaSirky() + ") pridaná!");
            } catch (NumberFormatException ex) {
                view.setResultText("Nesprávny vstup!");
            }
        }
    }
}
