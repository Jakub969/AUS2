package GUI.Controller;

import GUI.Model.Model;
import GUI.View.EditovacieOkno;
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

public class Controller {
    private final Model model;
    private final View view;

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

                ArrayList<GPS> gpsPositions = getGps();

                ArrayList<Vrchol<Nehnutelnost>> results = model.vyhladajNehnutelnost(gpsPositions);

                if (!results.isEmpty()) {
                    ArrayList<Vrchol<Nehnutelnost>> duplicity = new ArrayList<>();
                    for (Vrchol<Nehnutelnost> nehnutelnostVrchol : results) {
                        if (nehnutelnostVrchol != null) {
                            duplicity.addAll(nehnutelnostVrchol.getDuplicity());
                        }
                    }
                    results.addAll(duplicity);
                }

                if (results.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Nehnutelnost nebola nájdená!");
                } else {
                    for (Vrchol<Nehnutelnost> nehnutelnostVrchol : results) {
                        Nehnutelnost nehnutelnost = nehnutelnostVrchol.getData();
                        view.addResult("Nehnuteľnosť", nehnutelnost.getGPSsuradnice().toString(), nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS().getGPSsuradnice().toString(), String.valueOf(nehnutelnost.getSupisneCislo()), nehnutelnost.getPopis());
                    }
                }
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    private ArrayList<GPS> getGps() {
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
        return gpsPositions;
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
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getSelectedRow();
            if (selectedRow != -1) {
                String geografickyObjekt = view.getGeografickyObjekt(selectedRow);
                String gps1 = view.getGps1(selectedRow);
                String gps2 = view.getGps2(selectedRow);
                String supisneCislo = view.getSupisneCislo(selectedRow);
                String popis = view.getPopis(selectedRow);

                String objektPred = geografickyObjekt + "," + gps1 + "," + gps2 + "," + supisneCislo + "," + popis;

                EditovacieOkno editovacieOkno = new EditovacieOkno(geografickyObjekt, gps1, gps2, supisneCislo, popis);
                editovacieOkno.addConfirmButtonListener(confirmEvent -> {
                    updateModel(objektPred, selectedRow, editovacieOkno.getGps1(), editovacieOkno.getGps2(), editovacieOkno.getSupisneCislo(), editovacieOkno.getPopis());
                    editovacieOkno.dispose();
                });
                editovacieOkno.addCancelButtonListener(cancelEvent -> editovacieOkno.dispose());
                editovacieOkno.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Prosím, vyberte riadok na úpravu.");
            }
        }

        private void updateModel(String objektPred, int selectedRow, String gps1, String gps2, String supisneCislo, String popis) {
            try {
                String gps1Part = gps1.split(" ")[1];
                if (gps1Part.endsWith(";")) {
                    gps1Part = gps1Part.substring(0, gps1Part.length() - 1);
                }
                double poziciaSirky1 = Double.parseDouble(gps1Part);
                double poziciaDlzky1 = Double.parseDouble(gps1.split(" ")[3]);

                char sirka1 = gps1.split(" ")[0].charAt(0);
                char dlzka1 = gps1.split(" ")[2].charAt(0);

                String gps2Part = gps2.split(" ")[1];
                if (gps2Part.endsWith(";")) {
                    gps2Part = gps2Part.substring(0, gps2Part.length() - 1);
                }
                double poziciaSirky2 = Double.parseDouble(gps2Part);
                double poziciaDlzky2 = Double.parseDouble(gps2.split(" ")[3]);
                char sirka2 = gps2.split(" ")[0].charAt(0);
                char dlzka2 = gps2.split(" ")[2].charAt(0);

                GPS pozicia1 = new GPS(sirka1, poziciaSirky1, dlzka1, poziciaDlzky1);
                GPS pozicia2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);

                if (view.getGeografickyObjekt(selectedRow).equals("Nehnuteľnosť")) {
                    model.upravNehnutelnost(objektPred, Integer.parseInt(supisneCislo), popis, pozicia1, pozicia2);
                } else {
                    model.upravParcelu(objektPred, Integer.parseInt(supisneCislo), popis, pozicia1, pozicia2);
                }
                String geoObjekt = view.getGeografickyObjekt(selectedRow);
                view.clearResults();
                view.addResult(geoObjekt, gps1, gps2, supisneCislo, popis);
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(view, "Naozaj chcete vymazať tento záznam?", "Potvrdenie", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    vymazZaznam(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Prosím, vyberte riadok na vymazanie.");
            }
        }

        private void vymazZaznam(int selectedRow) {
            String geografickyObjekt = view.getGeografickyObjekt(selectedRow);
            String gps1 = view.getGps1(selectedRow);
            String gps2 = view.getGps2(selectedRow);
            String supisneCislo = view.getSupisneCislo(selectedRow);
            String popis = view.getPopis(selectedRow);

            String objektPred = geografickyObjekt + "," + gps1 + "," + gps2 + "," + supisneCislo + "," + popis;

            if (geografickyObjekt.equals("Nehnuteľnosť")) {
                model.odstranNehnutelnost(objektPred);
            } else {
                model.odstranParcelu(objektPred);
            }
            view.removeRow(selectedRow);
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

                String gps1 = gpsPositions.getFirst().toString();
                String gps2 = gpsPositions.getLast().toString();

                view.addResult("Parcela", gps1, gps2, String.valueOf(supisneCislo), popis);
            } catch (NumberFormatException ex) {
                view.addResult("Chyba", "N/A", "N/A", "N/A", "Nesprávny vstup!");
            }
        }
    }

    private class SearchParcelaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.clearResults();

                ArrayList<GPS> gpsPositions = getGps();

                ArrayList<Vrchol<Parcela>> results = model.vyhladajParcelu(gpsPositions);

                if (!results.isEmpty()) {
                    ArrayList<Vrchol<Parcela>> duplicity = new ArrayList<>();
                    for (Vrchol<Parcela> parcelaVrchol : results) {
                        if (parcelaVrchol != null) {
                            duplicity.addAll(parcelaVrchol.getDuplicity());
                        }
                    }
                    results.addAll(duplicity);
                }

                if (results.isEmpty()) {
                    view.addResult("Chyba", "N/A", "N/A", "N/A", "Parcela nebola nájdená!");
                } else {
                    for (Vrchol<Parcela> parcelaVrchol : results) {
                        Parcela parcela = parcelaVrchol.getData();
                        view.addResult("Parcela", parcela.getGPSsuradnice().toString(), parcela.getReferenciaNaRovnakuParceluSInymiGPS().getGPSsuradnice().toString(), String.valueOf(parcela.getCisloParcely()), parcela.getPopis());
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

                ArrayList<GPS> gpsPositions = getGps();

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