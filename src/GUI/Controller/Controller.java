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
                // Get the latitude and longitude values from the view
                double dlzka = Double.parseDouble(view.getDlzka());
                double sirka = Double.parseDouble(view.getSirka());

                // Get orientation for both latitude and longitude
                String sirkaOrientation = view.getSirkaOrientation(); // "N" or "S"
                String dlzkaOrientation = view.getDlzkaOrientation(); // "E" or "W"

                // Adjust the latitude and longitude values based on the orientation
                if (sirkaOrientation.equals("S")) {
                    sirka = -sirka; // Negative for southern hemisphere
                }
                if (dlzkaOrientation.equals("W")) {
                    dlzka = -dlzka; // Negative for western hemisphere
                }

                // Perform search with adjusted coordinates
                List<Nehnutelnost> results = model.searchNehnutelnosti(dlzka, sirka);

                // Display results
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
                // Parse the property details from the view
                int supisneCislo = Integer.parseInt(view.getSupisneCislo());
                String popis = view.getPopis();

                // Parse GPS positions from the GPS positions field
                String[] gpsPositionsStr = view.getGpsPositions().split(";");
                List<GPS> gpsPositions = new ArrayList<>();

                for (String gpsStr : gpsPositionsStr) {
                    String[] parts = gpsStr.split(",");
                    char dlzkaChar = parts[0].charAt(0); // E or W
                    double dlzka = Double.parseDouble(parts[1]);
                    char sirkaChar = parts[2].charAt(0); // N or S
                    double sirka = Double.parseDouble(parts[3]);

                    // Adjust latitude and longitude values based on orientation
                    if (sirkaChar == 'S') {
                        sirka = -sirka;
                    }
                    if (dlzkaChar == 'W') {
                        dlzka = -dlzka;
                    }

                    gpsPositions.add(new GPS(dlzkaChar, dlzka, sirkaChar, sirka));
                }

                // Add new property with the parsed details
                model.addNehnutelnost(supisneCislo, popis, gpsPositions);

                // Display confirmation message
                view.setResultText("Nehnutelnost (Supisne Cislo: " + supisneCislo +
                        ", Popis: " + popis +
                        ", GPS Pozicie: " + gpsPositions.get(0).getPoziciaDlzky() +
                        " " + gpsPositions.get(0).getPoziciaSirky() + ") pridaná!");
            } catch (NumberFormatException ex) {
                view.setResultText("Invalid input.");
            }
        }
    }
}
