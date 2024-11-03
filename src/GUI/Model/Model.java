package GUI.Model;

import triedy.OperacieGeografickychObjektov;
import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import triedy.GPS;
import triedy.GeografickyObjekt;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private KdStrom<Parcela> kdStromParciel;
    private KdStrom<GeografickyObjekt> kdStromGeografickychObjektov;
    OperacieGeografickychObjektov<Nehnutelnost> operacieNehnutelnosti;
    OperacieGeografickychObjektov<Parcela> operacieParciel;
    OperacieGeografickychObjektov<GeografickyObjekt> operacieGeografickychObjektov;

    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        this.operacieNehnutelnosti = new OperacieGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        this.operacieParciel = new OperacieGeografickychObjektov<>(this.kdStromParciel);
        this.kdStromGeografickychObjektov = new KdStrom<>(2);
        this.operacieGeografickychObjektov = new OperacieGeografickychObjektov<>(this.kdStromGeografickychObjektov);
    }

    public ArrayList<Vrchol<Nehnutelnost>> vyhladajNehnutelnost(ArrayList<GPS> gpsPositions) {

        Nehnutelnost vyhladavanaNehnutelnost1 = new Nehnutelnost(0, "", null, null, gpsPositions.getFirst());
        Nehnutelnost vyhladavanaNehnutelnost2 = new Nehnutelnost(0, "", null, null, gpsPositions.getLast());

        Vrchol<Nehnutelnost> vrcholVyhladavania = new Vrchol<>(vyhladavanaNehnutelnost1);
        Vrchol<Nehnutelnost> vrcholVyhladavania2 = new Vrchol<>(vyhladavanaNehnutelnost2);
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania);
        kluce.add(vrcholVyhladavania2);
        return kdStromNehnutelnosti.bodoveVyhladavanie(kluce);
    }

    public void pridajNehnutelnost(int supisneCislo, String popis, List<GPS> gpsPositions) {
        Nehnutelnost newNehnutelnost1 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getFirst());
        Nehnutelnost newNehnutelnost2 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Nehnutelnost>> vysledok = operacieNehnutelnosti.metodaVkladania(newNehnutelnost1, newNehnutelnost2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), vysledok.getFirst().getData(), null);
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), vysledok.getLast().getData(), null);
        operacieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    public ArrayList<Vrchol<Parcela>> vyhladajParcelu(ArrayList<GPS> gpsPositions) {
        Parcela vyhladavanaParcela1 = new Parcela(0, "", null, null, gpsPositions.getFirst());
        Parcela vyhladavanaParcela2 = new Parcela(0, "", null, null, gpsPositions.getLast());

        Vrchol<Parcela> vrcholVyhladavania1 = new Vrchol<>(vyhladavanaParcela1);
        Vrchol<Parcela> vrcholVyhladavania2 = new Vrchol<>(vyhladavanaParcela2);
        ArrayList<Vrchol<Parcela>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania1);
        kluce.add(vrcholVyhladavania2);
        ArrayList<Vrchol<Parcela>> results = kdStromParciel.bodoveVyhladavanie(kluce);

        return results;
    }

    public void pridajParcelu(int cisloParcely, String popis, List<GPS> gpsPositions) {
        Parcela newParcela1 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getFirst());
        Parcela newParcela2 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Parcela>> vysledok = operacieParciel.metodaVkladania(newParcela1, newParcela2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), null, vysledok.getFirst().getData());
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), null, vysledok.getLast().getData());
        operacieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    public void generujData(int pocetNehnutelnosti, int pocetParciel, double pravdepodobnostPrekrytia) {
        Random random = new Random(System.nanoTime());
        int vygenerovanychNehnutelnosti = 0;
        for (int i = 0; i < pocetParciel; i++) {
            char sirka = 'N';
            double poziciaSirky1 = random.nextDouble(100);
            char dlzka = 'E';
            double poziciaDlzky1 = random.nextDouble(100);
            GPS GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
            Parcela parcela1 = new Parcela(i, "Parcela " + i, null, null, GPSsuradnice1);
            double poziciaSirky2 = random.nextDouble(100);
            double poziciaDlzky2 = random.nextDouble(100);
            GPS GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
            Parcela parcela2 = new Parcela(i, "Parcela " + i, null, null, GPSsuradnice2);
            operacieParciel.metodaVkladania(parcela1, parcela2);
            GeografickyObjekt geografickyObjekt1 = new GeografickyObjekt(GPSsuradnice1, null, parcela1);
            GeografickyObjekt geografickyObjekt2 = new GeografickyObjekt(GPSsuradnice2, null, parcela2);
            operacieGeografickychObjektov.metodaVkladania(geografickyObjekt1, geografickyObjekt2);
            if (vygenerovanychNehnutelnosti < pocetNehnutelnosti) {
                if (random.nextDouble() < pravdepodobnostPrekrytia) {
                    GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                    Nehnutelnost nehnutelnost1 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice1);
                    poziciaSirky2 = random.nextDouble(100);
                    poziciaDlzky2 = random.nextDouble(100);
                    GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
                    Nehnutelnost nehnutelnost2 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice2);
                    operacieNehnutelnosti.metodaVkladania(nehnutelnost1, nehnutelnost2);
                    GeografickyObjekt geografickyObjekt3 = new GeografickyObjekt(GPSsuradnice1, nehnutelnost1, null);
                    GeografickyObjekt geografickyObjekt4 = new GeografickyObjekt(GPSsuradnice2, nehnutelnost2, null);
                    operacieGeografickychObjektov.metodaVkladania(geografickyObjekt3, geografickyObjekt4);
                    vygenerovanychNehnutelnosti++;
                } else {
                    poziciaSirky1 = random.nextDouble(100);
                    poziciaDlzky1 = random.nextDouble(100);
                    GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                    Nehnutelnost nehnutelnost1 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice1);
                    poziciaSirky2 = random.nextDouble(100);
                    poziciaDlzky2 = random.nextDouble(100);
                    GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
                    Nehnutelnost nehnutelnost2 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice2);
                    operacieNehnutelnosti.metodaVkladania(nehnutelnost1, nehnutelnost2);
                    GeografickyObjekt geografickyObjekt3 = new GeografickyObjekt(GPSsuradnice1, nehnutelnost1, null);
                    GeografickyObjekt geografickyObjekt4 = new GeografickyObjekt(GPSsuradnice2, nehnutelnost2, null);
                    operacieGeografickychObjektov.metodaVkladania(geografickyObjekt3, geografickyObjekt4);
                    vygenerovanychNehnutelnosti++;
                }
            }
        }
    }

    public void saveData(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            ArrayList<Vrchol<Nehnutelnost>> nehnutelnosti = kdStromNehnutelnosti.preOrderPrehliadka();
            if (nehnutelnosti != null) {
                for (Vrchol<Nehnutelnost> nehnutelnostVrchol : nehnutelnosti) {
                    Nehnutelnost nehnutelnost = nehnutelnostVrchol.getData();
                    int maReferenciu = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS() != null ? 1 : 0;
                    int pocetParciel = nehnutelnost.getZoznamParciel() != null ? nehnutelnost.getZoznamParciel().size() : 0;
                    ArrayList<Vrchol<Nehnutelnost>> duplicity = nehnutelnostVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "," + maReferenciu  + "," + pocetParciel + "," + pocetDuplicity + "\n");
                    if (maReferenciu == 1) {
                        Nehnutelnost referencia = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
                        writer.write("Nehnutelnost," + referencia.getSupisneCislo() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    }
                    if (pocetParciel != 0) {
                        for (Parcela parcela : nehnutelnost.getZoznamParciel()) {
                            writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice().getPoziciaSirky() + "," + parcela.getGPSsuradnice().getPoziciaDlzky() + "\n");
                        }
                    }
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Nehnutelnost> duplicityVrchol : duplicity) {
                            Nehnutelnost duplicityNehnutelnost = duplicityVrchol.getData();
                            writer.write("Nehnutelnost," + duplicityNehnutelnost.getSupisneCislo() + "," + duplicityNehnutelnost.getPopis() + "," + duplicityNehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + duplicityNehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "\n");
                        }
                    }
                }
            }
            ArrayList<Vrchol<Parcela>> parciel = kdStromParciel.preOrderPrehliadka();
            if (parciel != null) {
                for (Vrchol<Parcela> parcelaVrchol : parciel) {
                    Parcela parcela = parcelaVrchol.getData();
                    int maReferenciu = parcela.getReferenciaNaRovnakuParceluSInymiGPS() != null ? 1 : 0;
                    int pocetNehnutelnosti = parcela.getZoznamNehnutelnosti() != null ? parcela.getZoznamNehnutelnosti().size() : 0;
                    ArrayList<Vrchol<Parcela>> duplicity = parcelaVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice().getPoziciaSirky() + "," + parcela.getGPSsuradnice().getPoziciaDlzky() + "," + maReferenciu + "," + pocetNehnutelnosti + "," + pocetDuplicity + "\n");
                    if (maReferenciu == 1) {
                        Parcela referencia = parcela.getReferenciaNaRovnakuParceluSInymiGPS();
                        writer.write("Parcela," + referencia.getCisloParcely() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    }
                    if (pocetNehnutelnosti != 0) {
                        for (Nehnutelnost nehnutelnost : parcela.getZoznamNehnutelnosti()) {
                            writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "\n");
                        }
                    }
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Parcela> duplicityVrchol : duplicity) {
                            Parcela duplicityParcela = duplicityVrchol.getData();
                            writer.write("Parcela," + duplicityParcela.getCisloParcely() + "," + duplicityParcela.getPopis() + "," + duplicityParcela.getGPSsuradnice().getPoziciaSirky() + "," + duplicityParcela.getGPSsuradnice().getPoziciaDlzky() + "\n");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals("Nehnutelnost")) {
                int supisneCislo = Integer.parseInt(parts[1]);
                String popis = parts[2];
                double poziciaSirky = Double.parseDouble(parts[3]);
                char sirka = poziciaSirky < 0 ? 'S' : 'N';
                double poziciaDlzky = Double.parseDouble(parts[4]);
                char dlzka = poziciaDlzky < 0 ? 'W' : 'E';
                GPS gps = new GPS(sirka, poziciaSirky, dlzka, poziciaDlzky);
                int maReferenciu = Integer.parseInt(parts[5]);
                Nehnutelnost nehnutelnostReferencia = null;
                int pocetParciel = Integer.parseInt(parts[6]);
                int pocetDuplicity = Integer.parseInt(parts[7]);
                if (maReferenciu == 1) {
                    line = reader.readLine();
                    parts = line.split(",");
                    int supisneCisloReferencie = Integer.parseInt(parts[1]);
                    String popisReferencie = parts[2];
                    double poziciaSirkyReferencie = Double.parseDouble(parts[3]);
                    char sirkaReferencie = poziciaSirkyReferencie < 0 ? 'S' : 'N';
                    double poziciaDlzkyReferencie = Double.parseDouble(parts[4]);
                    char dlzkaReferencie = poziciaDlzkyReferencie < 0 ? 'W' : 'E';
                    GPS gpsReferencie = new GPS(sirkaReferencie, poziciaSirkyReferencie, dlzkaReferencie, poziciaDlzkyReferencie);
                    nehnutelnostReferencia = new Nehnutelnost(supisneCisloReferencie, popisReferencie, null, null, gpsReferencie);
                }
                ArrayList<Parcela> zoznamParciel = new ArrayList<>();
                if (pocetParciel != 0) {
                    for (int i = 0; i < pocetParciel; i++) {
                        line = reader.readLine();
                        parts = line.split(",");
                        int cisloParcely = Integer.parseInt(parts[1]);
                        String popisParcely = parts[2];
                        double poziciaSirkyParcely = Double.parseDouble(parts[3]);
                        char sirkaParcely = poziciaSirkyParcely < 0 ? 'S' : 'N';
                        double poziciaDlzkyParcely = Double.parseDouble(parts[4]);
                        char dlzkaParcely = poziciaDlzkyParcely < 0 ? 'W' : 'E';
                        GPS gpsParcely = new GPS(sirkaParcely, poziciaSirkyParcely, dlzkaParcely, poziciaDlzkyParcely);
                        Parcela parcela = new Parcela(cisloParcely, popisParcely, null, null, gpsParcely);
                        zoznamParciel.add(parcela);
                    }
                }
                ArrayList<Nehnutelnost> duplicity = new ArrayList<>();
                if (pocetDuplicity != 0) {
                    for (int i = 0; i < pocetDuplicity; i++) {
                        line = reader.readLine();
                        parts = line.split(",");
                        int supisneCisloDuplicity = Integer.parseInt(parts[1]);
                        String popisDuplicity = parts[2];
                        double poziciaSirkyDuplicity = Double.parseDouble(parts[3]);
                        char sirkaDuplicity = poziciaSirkyDuplicity < 0 ? 'S' : 'N';
                        double poziciaDlzkyDuplicity = Double.parseDouble(parts[4]);
                        char dlzkaDuplicity = poziciaDlzkyDuplicity < 0 ? 'W' : 'E';
                        GPS gpsDuplicity = new GPS(sirkaDuplicity, poziciaSirkyDuplicity, dlzkaDuplicity, poziciaDlzkyDuplicity);
                        Nehnutelnost duplicita = new Nehnutelnost(supisneCisloDuplicity, popisDuplicity, null, null, gpsDuplicity);
                        duplicity.add(duplicita);
                    }
                }
                Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis, zoznamParciel, nehnutelnostReferencia, gps);
                Vrchol<Nehnutelnost> vrchol = new Vrchol<>(nehnutelnost);
                kdStromNehnutelnosti.vloz(vrchol);
                if (pocetDuplicity != 0) {
                    for (Nehnutelnost duplicita : duplicity) {
                        Vrchol<Nehnutelnost> vrcholDuplicity = new Vrchol<>(duplicita);
                        kdStromNehnutelnosti.vloz(vrcholDuplicity);
                    }
                }
                Vrchol<GeografickyObjekt> vrcholGeografickyObjekt = new Vrchol<>(new GeografickyObjekt(gps, nehnutelnost, null));
                kdStromGeografickychObjektov.vloz(vrcholGeografickyObjekt);
            } else if (parts[0].equals("Parcela")) {
                int cisloParcely = Integer.parseInt(parts[1]);
                String popis = parts[2];
                double poziciaSirky = Double.parseDouble(parts[3]);
                char sirka = poziciaSirky < 0 ? 'S' : 'N';
                double poziciaDlzky = Double.parseDouble(parts[4]);
                char dlzka = poziciaDlzky < 0 ? 'W' : 'E';
                GPS gps = new GPS(sirka, poziciaSirky, dlzka, poziciaDlzky);
                int maReferenciu = Integer.parseInt(parts[5]);
                Parcela parcelaReferencia = null;
                int pocetNehnutelnosti = Integer.parseInt(parts[6]);
                int pocetDuplicity = Integer.parseInt(parts[7]);
                if (maReferenciu == 1) {
                    line = reader.readLine();
                    parts = line.split(",");
                    int cisloParcelyReferencie = Integer.parseInt(parts[1]);
                    String popisReferencie = parts[2];
                    double poziciaSirkyReferencie = Double.parseDouble(parts[3]);
                    char sirkaReferencie = poziciaSirkyReferencie < 0 ? 'S' : 'N';
                    double poziciaDlzkyReferencie = Double.parseDouble(parts[4]);
                    char dlzkaReferencie = poziciaDlzkyReferencie < 0 ? 'W' : 'E';
                    GPS gpsReferencie = new GPS(sirkaReferencie, poziciaSirkyReferencie, dlzkaReferencie, poziciaDlzkyReferencie);
                    parcelaReferencia = new Parcela(cisloParcelyReferencie, popisReferencie, null, null, gpsReferencie);
                }
                ArrayList<Nehnutelnost> zoznamNehnutelnosti = new ArrayList<>();
                if (pocetNehnutelnosti != 0) {
                    for (int i = 0; i < pocetNehnutelnosti; i++) {
                        line = reader.readLine();
                        parts = line.split(",");
                        int supisneCislo = Integer.parseInt(parts[1]);
                        String popisNehnutelnosti = parts[2];
                        double poziciaSirkyNehnutelnosti = Double.parseDouble(parts[3]);
                        char sirkaNehnutelnosti = poziciaSirkyNehnutelnosti < 0 ? 'S' : 'N';
                        double poziciaDlzkyNehnutelnosti = Double.parseDouble(parts[4]);
                        char dlzkaNehnutelnosti = poziciaDlzkyNehnutelnosti < 0 ? 'W' : 'E';
                        GPS gpsNehnutelnosti = new GPS(sirkaNehnutelnosti, poziciaSirkyNehnutelnosti, dlzkaNehnutelnosti, poziciaDlzkyNehnutelnosti);
                        Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popisNehnutelnosti, null, null, gpsNehnutelnosti);
                        zoznamNehnutelnosti.add(nehnutelnost);
                    }
                }
                ArrayList<Parcela> duplicity = new ArrayList<>();
                if (pocetDuplicity != 0) {
                    for (int i = 0; i < pocetDuplicity; i++) {
                        line = reader.readLine();
                        parts = line.split(",");
                        int cisloParcelyDuplicity = Integer.parseInt(parts[1]);
                        String popisDuplicity = parts[2];
                        double poziciaSirkyDuplicity = Double.parseDouble(parts[3]);
                        char sirkaDuplicity = poziciaSirkyDuplicity < 0 ? 'S' : 'N';
                        double poziciaDlzkyDuplicity = Double.parseDouble(parts[4]);
                        char dlzkaDuplicity = poziciaDlzkyDuplicity < 0 ? 'W' : 'E';
                        GPS gpsDuplicity = new GPS(sirkaDuplicity, poziciaSirkyDuplicity, dlzkaDuplicity, poziciaDlzkyDuplicity);
                        Parcela duplicita = new Parcela(cisloParcelyDuplicity, popisDuplicity, null, null, gpsDuplicity);
                        duplicity.add(duplicita);
                    }
                }
                Parcela parcela = new Parcela(cisloParcely, popis, zoznamNehnutelnosti, parcelaReferencia, gps);
                Vrchol<Parcela> vrchol = new Vrchol<>(parcela);
                kdStromParciel.vloz(vrchol);
                if (pocetDuplicity != 0) {
                    for (Parcela duplicita : duplicity) {
                        Vrchol<Parcela> vrcholDuplicity = new Vrchol<>(duplicita);
                        kdStromParciel.vloz(vrcholDuplicity);
                    }
                }
                Vrchol<GeografickyObjekt> vrcholGeografickyObjekt = new Vrchol<>(new GeografickyObjekt(gps, null, parcela));
                kdStromGeografickychObjektov.vloz(vrcholGeografickyObjekt);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public ArrayList<Vrchol<GeografickyObjekt>> vyhladajVsetky(ArrayList<GPS> gpsPositions) {
        GeografickyObjekt vyhladavanyGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), null, null);
        GeografickyObjekt vyhladavanyGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), null, null);

        Vrchol<GeografickyObjekt> vrcholVyhladavania1 = new Vrchol<>(vyhladavanyGeografickyObjekt1);
        Vrchol<GeografickyObjekt> vrcholVyhladavania2 = new Vrchol<>(vyhladavanyGeografickyObjekt2);
        ArrayList<Vrchol<GeografickyObjekt>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania1);
        kluce.add(vrcholVyhladavania2);
        return kdStromGeografickychObjektov.bodoveVyhladavanie(kluce);
    }

    public ArrayList<Vrchol<GeografickyObjekt>> vypisVsetky() {
        return kdStromGeografickychObjektov.preOrderPrehliadka();
    }
}
