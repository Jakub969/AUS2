package GUI.Controller;

import GUI.Model.Model;
import GUI.View.View;
import US.KdStrom.Vrchol;
import triedy.GPS;
import triedy.GeografickyObjekt;
import triedy.Nehnutelnost;
import triedy.Parcela;

import javax.swing.*;
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
        this.view.addSaveButtonListener(new SaveButtonListener());
        this.view.addLoadButtonListener(new LoadButtonListener());
        this.view.addSearchAllButtonListener(new SearchAllButtonListener());
        this.view.addPrintAllButtonListener(new PrintAllButtonListener());
    }

    class SearchNehnutelnostButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

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

                ArrayList<Vrchol<Nehnutelnost>> results = model.vyhladajNehnutelnost(gpsPositions);

                ArrayList<Vrchol<Nehnutelnost>> duplicity = new ArrayList<>();
                for (Vrchol<Nehnutelnost> nehnutelnostVrchol : results) {
                    duplicity.addAll(nehnutelnostVrchol.getDuplicity());
                }

                results.addAll(duplicity);

                if (results.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Nehnutelnost nebola nájdená!");
                } else {
                    for (Vrchol<Nehnutelnost> result : results) {
                        view.addResult("Nehnuteľnosť", result.getData().getGPSsuradnice().toString(), result.getData().getReferenciaNaRovnakuNehnutelnostSInymiGPS().getGPSsuradnice().toString(), String.valueOf(result.getData().getSupisneCislo()), result.getData().getPopis());
                    }
                }
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    class AddNehnutelnostButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

                int supisneCislo = Integer.parseInt(view.getSupisneCisloNehnutelnosti());
                String popis = view.getPopisNehnutelnosti();
                ArrayList<GPS> gpsPositions = getGps();

                model.pridajNehnutelnost(supisneCislo, popis, gpsPositions);

                /*view.setResultText("Nehnuteľnosť " + supisneCislo + " " + popis + " GPS suradnice {: " + gpsPositions.getFirst().getSirka() + " " + gpsPositions.getFirst().getPoziciaSirky() + " " + gpsPositions.getFirst().getDlzka() + " " + gpsPositions.getFirst().getPoziciaDlzky() +
                            " ; " + gpsPositions.getLast().getSirka() + " " + gpsPositions.getLast().getPoziciaSirky() + " " + gpsPositions.getLast().getDlzka() + " " + gpsPositions.getLast().getPoziciaDlzky() + "} pridaná úspešne!");*/
                String gps1 = gpsPositions.getFirst().toString();
                String gps2 = gpsPositions.getLast().toString();

                view.addResult("Nehnuteľnosť", gps1, gps2, String.valueOf(supisneCislo), popis);
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }

        private ArrayList<GPS> getGps() {
            ArrayList<GPS> gpsPositions = new ArrayList<>();

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
                view.clearResults();
                int pocetNehnutelnosti = Integer.parseInt(view.getPocetNehnutelnosti());
                int pocetParciel = Integer.parseInt(view.getPocetParciel());
                double pravdepodobnostPrekrytia = Double.parseDouble(view.getPravdepodobnostPrekrytia());

                model.generujData(pocetNehnutelnosti, pocetParciel, pravdepodobnostPrekrytia);

                view.addResult("Generovanie", "N/A", "N/A", "N/A", "Dáta boli vygenerované!");
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    private class AddParcelaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

                int supisneCislo = Integer.parseInt(view.getSupisneCisloParcely());
                String popis = view.getPopisParcely();
                ArrayList<GPS> gpsPositions = getGps();

                model.pridajParcelu(supisneCislo, popis, gpsPositions);

                /*view.setResultText("Nehnuteľnosť " + supisneCislo + " " + popis + " GPS suradnice {: " + gpsPositions.getFirst().getSirka() + " " + gpsPositions.getFirst().getPoziciaSirky() + " " + gpsPositions.getFirst().getDlzka() + " " + gpsPositions.getFirst().getPoziciaDlzky() +
                            " ; " + gpsPositions.getLast().getSirka() + " " + gpsPositions.getLast().getPoziciaSirky() + " " + gpsPositions.getLast().getDlzka() + " " + gpsPositions.getLast().getPoziciaDlzky() + "} pridaná úspešne!");*/
                String gps1 = gpsPositions.getFirst().toString();
                String gps2 = gpsPositions.getLast().toString();

                view.addResult("Parcela", gps1, gps2, String.valueOf(supisneCislo), popis);
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }

        private ArrayList<GPS> getGps() {
            ArrayList<GPS> gpsPositions = new ArrayList<>();

            char sirka1 = view.getSirkaParcelyComboBox1();
            char dlzka1 = view.getDlzkaParcelyComboBox1();
            double poziciaDlzky1 = Double.parseDouble(view.getDlzkaParcely1());
            double poziciaSirky1 = Double.parseDouble(view.getSirkaParcely1());
            GPS pozicia1 = new GPS(sirka1, poziciaSirky1, dlzka1, poziciaDlzky1);
            gpsPositions.add(pozicia1);
            char sirka2 = view.getSirkaParcelyComboBox2();
            char dlzka2 = view.getDlzkaParcelyComboBox2();
            double poziciaDlzky2 = Double.parseDouble(view.getDlzkaParcely2());
            double poziciaSirky2 = Double.parseDouble(view.getSirkaParcely2());
            GPS pozicia2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);
            gpsPositions.add(pozicia2);
            return gpsPositions;
        }
    }

    private class SearchParcelaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

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

                ArrayList<Vrchol<Parcela>> results = model.vyhladajParcelu(gpsPositions);

                ArrayList<Vrchol<Parcela>> duplicity = new ArrayList<>();
                for (Vrchol<Parcela> parcelaVrchol : results) {
                    duplicity.addAll(parcelaVrchol.getDuplicity());
                }

                results.addAll(duplicity);

                if (results.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Parcela nebola nájdená!");
                } else {
                    for (Vrchol<Parcela> result : results) {
                        view.addResult("Parcela", result.getData().getGPSsuradnice().toString(), result.getData().getReferenciaNaRovnakuParceluSInymiGPS().getGPSsuradnice().toString(), String.valueOf(result.getData().getCisloParcely()), result.getData().getPopis());
                    }
                }
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Uložiť dáta");
                int userSelection = fileChooser.showSaveDialog(view);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    view.clearResults();
                    model.saveData(fileChooser.getSelectedFile().getAbsolutePath());
                    view.addResult("Uloženie", "N/A", "N/A", "N/A", "Dáta boli uložené!");
                }
            } catch (Exception ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Chyba pri ukladaní dát!");
            }
        }
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Načítať dáta");
                int userSelection = fileChooser.showOpenDialog(view);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    view.clearResults();
                    model.loadData(fileChooser.getSelectedFile().getAbsolutePath());
                    view.addResult("Načítanie", "N/A", "N/A", "N/A", "Dáta boli načítané!");
                }
            } catch (Exception ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Chyba pri načítaní dát!");
            }
        }
    }

    private class SearchAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

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

                ArrayList<Vrchol<GeografickyObjekt>> results = model.vyhladajVsetky(gpsPositions);
                ArrayList<Nehnutelnost> nehnutelnosti = new ArrayList<>();
                ArrayList<Parcela> parcely = new ArrayList<>();
                ArrayList<Vrchol<GeografickyObjekt>> duplicity = new ArrayList<>();
                for (Vrchol<GeografickyObjekt> result : results) {
                    duplicity.addAll(result.getDuplicity());
                }
                results.addAll(duplicity);
                for (Vrchol<GeografickyObjekt> result : results) {
                    if (result.getData().getNehnutelnost() != null) {
                        nehnutelnosti.add(result.getData().getNehnutelnost());
                    } else if (result.getData().getParcela() != null) {
                        parcely.add(result.getData().getParcela());
                    }
                }
                if (nehnutelnosti.isEmpty() && parcely.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Žiadne dáta neboli nájdené!");
                } else {
                    for (Nehnutelnost nehnutelnost : nehnutelnosti) {
                        view.addResult("Nehnuteľnosť", nehnutelnost.getGPSsuradnice().toString(), nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS().getGPSsuradnice().toString(), String.valueOf(nehnutelnost.getSupisneCislo()), nehnutelnost.getPopis());
                    }
                    for (Parcela parcela : parcely) {
                        view.addResult("Parcela", parcela.getGPSsuradnice().toString(), parcela.getReferenciaNaRovnakuParceluSInymiGPS().getGPSsuradnice().toString(), String.valueOf(parcela.getCisloParcely()), parcela.getPopis());
                    }
                }

            } catch (Exception ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Chyba pri vyhľadávaní dát!");
            }
        }
    }

    private class PrintAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();
                ArrayList<Vrchol<GeografickyObjekt>> results = model.vypisVsetky();
                ArrayList<Vrchol<GeografickyObjekt>> duplicity = new ArrayList<>();
                for (Vrchol<GeografickyObjekt> result : results) {
                    duplicity.addAll(result.getDuplicity());
                }
                results.addAll(duplicity);
                ArrayList<Nehnutelnost> nehnutelnosti = new ArrayList<>();
                ArrayList<Parcela> parcely = new ArrayList<>();
                for (Vrchol<GeografickyObjekt> result : results) {
                    if (result.getData().getNehnutelnost() != null) {
                        nehnutelnosti.add(result.getData().getNehnutelnost());
                    } else if (result.getData().getParcela() != null) {
                        parcely.add(result.getData().getParcela());
                    }
                }
                if (nehnutelnosti.isEmpty() && parcely.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Žiadne dáta neboli nájdené!");
                } else {
                    for (Nehnutelnost nehnutelnost : nehnutelnosti) {
                        view.addResult("Nehnuteľnosť", nehnutelnost.getGPSsuradnice().toString(), nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS().getGPSsuradnice().toString(), String.valueOf(nehnutelnost.getSupisneCislo()), nehnutelnost.getPopis());
                    }
                    for (Parcela parcela : parcely) {
                        view.addResult("Parcela", parcela.getGPSsuradnice().toString(), parcela.getReferenciaNaRovnakuParceluSInymiGPS().getGPSsuradnice().toString(), String.valueOf(parcela.getCisloParcely()), parcela.getPopis());
                    }
                }

            } catch (Exception ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Chyba pri vyhľadávaní dát!");
            }
        }
    }
}