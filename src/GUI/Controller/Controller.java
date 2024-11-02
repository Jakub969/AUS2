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

        this.view.searchNehnutelnostButtonListener(new SearchNehnutelnostButtonListener());
        this.view.addNehnutelnostButtonListener(new AddNehnutelnostButtonListener());
        this.view.addParcelaButtonListener(new AddParcelaButtonListener());
        this.view.searchParcelaButtonListener(new SearchParcelaButtonListener());
        this.view.addEditButtonListener(new EditButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addGenerateButtonListener(new GenerateButtonListener());
    }

    class SearchNehnutelnostButtonListener implements ActionListener {
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

                GPS pozicia1 = new GPS(sirka1, poziciaSirky1, dlzka1, poziciaDlzky1);
                GPS pozicia2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);

                ArrayList<GPS> gpsPositions = new ArrayList<>();
                gpsPositions.add(pozicia1);
                gpsPositions.add(pozicia2);

                List<Nehnutelnost> results = model.vyhladajNehnutelnost(gpsPositions);

                StringBuilder resultText = new StringBuilder();
                for (Nehnutelnost nehnutelnost : results) {
                    resultText.append(nehnutelnost.toString()).append("\n");
                }
                view.addResult(resultText.toString(), new EditButtonListener(), new DeleteButtonListener());
            } catch (NumberFormatException ex) {
                view.setResultText("Nesprávne súradnice GPS.");
            }
        }
    }

    class AddNehnutelnostButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int supisneCislo = Integer.parseInt(view.getSupisneCisloNehnutelnosti());
                String popis = view.getPopisNehnutelnosti();
                List<GPS> gpsPositions = getGps();

                model.pridajNehnutelnost(supisneCislo, popis, gpsPositions);

                view.setResultText("Nehnuteľnosť pridaná úspešne!");
            } catch (NumberFormatException ex) {
                view.setResultText("Nesprávny vstup!");
            }
        }

        private List<GPS> getGps() {
            List<GPS> gpsPositions = new ArrayList<>();

            char sirka1 = view.getSirkaNehnutelnostiComboBox1();
            char dlzka1 = view.getDlzkaNehnutelnostiComboBox1();
            double poziciaDlzky1 = Double.parseDouble(view.getDlzkaNehnutelnosti1());
            double poziciaSirky1 = Double.parseDouble(view.getSirkaNehnutelnosti1());
            GPS pozicia1 = new GPS(sirka1, poziciaSirky1, dlzka1, poziciaDlzky1);
            gpsPositions.add(pozicia1);
            char sirka2 = view.getSirkaNehnutelnostiComboBox2();
            char dlzka2 = view.getDlzkaNehnutelnostiComboBox2();
            double poziciaDlzky2 = Double.parseDouble(view.getDlzkaNehnutelnosti2());
            double poziciaSirky2 = Double.parseDouble(view.getSirkaNehnutelnosti2());
            GPS pozicia2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);
            gpsPositions.add(pozicia2);
            return gpsPositions;
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement edit functionality here
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement delete functionality here
        }
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int pocetNehnutelnosti = Integer.parseInt(view.getPocetNehnutelnosti());
                int pocetParciel = Integer.parseInt(view.getPocetParciel());
                double pravdepodobnostPrekrytia = Double.parseDouble(view.getPravdepodobnostPrekrytia());

                // Implement data generation logic here

                view.setResultText("Dáta vygenerované úspešne!");
            } catch (NumberFormatException ex) {
                view.setResultText("Nesprávny vstup!");
            }
        }
    }

    private class AddParcelaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class SearchParcelaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}