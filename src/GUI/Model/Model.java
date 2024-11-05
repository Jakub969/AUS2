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
                    ArrayList<Vrchol<Nehnutelnost>> duplicity = nehnutelnostVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    Nehnutelnost referencia = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
                    writer.write("Nehnutelnost," + referencia.getSupisneCislo() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Nehnutelnost> duplicityVrchol : duplicity) {
                            Nehnutelnost duplicityNehnutelnost = duplicityVrchol.getData();
                            writer.write("Nehnutelnost," + duplicityNehnutelnost.getSupisneCislo() + "," + duplicityNehnutelnost.getPopis() + "," + duplicityNehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + duplicityNehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "\n");
                            Nehnutelnost duplicityReferencia = duplicityNehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
                            writer.write("Nehnutelnost," + duplicityReferencia.getSupisneCislo() + "," + duplicityReferencia.getPopis() + "," + duplicityReferencia.getGPSsuradnice().getPoziciaSirky() + "," + duplicityReferencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
                        }
                    }
                }
            }
            ArrayList<Vrchol<Parcela>> parciel = kdStromParciel.preOrderPrehliadka();
            if (parciel != null) {
                for (Vrchol<Parcela> parcelaVrchol : parciel) {
                    Parcela parcela = parcelaVrchol.getData();
                    ArrayList<Vrchol<Parcela>> duplicity = parcelaVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice().getPoziciaSirky() + "," + parcela.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    Parcela referencia = parcela.getReferenciaNaRovnakuParceluSInymiGPS();
                    writer.write("Parcela," + referencia.getCisloParcely() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Parcela> duplicityVrchol : duplicity) {
                            Parcela duplicityParcela = duplicityVrchol.getData();
                            writer.write("Parcela," + duplicityParcela.getCisloParcely() + "," + duplicityParcela.getPopis() + "," + duplicityParcela.getGPSsuradnice().getPoziciaSirky() + "," + duplicityParcela.getGPSsuradnice().getPoziciaDlzky() + "\n");
                            Parcela duplicityReferencia = duplicityParcela.getReferenciaNaRovnakuParceluSInymiGPS();
                            writer.write("Parcela," + duplicityReferencia.getCisloParcely() + "," + duplicityReferencia.getPopis() + "," + duplicityReferencia.getGPSsuradnice().getPoziciaSirky() + "," + duplicityReferencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
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
                    Nehnutelnost nehnutelnostReferencia = null;
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
                    Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis, null, nehnutelnostReferencia, gps);
                    nehnutelnostReferencia.setReferenciaNaRovnakuNehnutelnostSInymiGPS(nehnutelnost);
                    Vrchol<Nehnutelnost> vrchol = new Vrchol<>(nehnutelnost);
                    Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(nehnutelnostReferencia);
                    kdStromNehnutelnosti.vloz(vrchol);
                    kdStromNehnutelnosti.vloz(vrchol1);
                    Vrchol<GeografickyObjekt> vrcholGeografickyObjekt = new Vrchol<>(new GeografickyObjekt(gps, nehnutelnost, null));
                    Vrchol<GeografickyObjekt> vrcholGeografickyObjektReferencia = new Vrchol<>(new GeografickyObjekt(gpsReferencie, nehnutelnostReferencia, null));
                    kdStromGeografickychObjektov.vloz(vrcholGeografickyObjekt);
                    kdStromGeografickychObjektov.vloz(vrcholGeografickyObjektReferencia);
                } else if (parts[0].equals("Parcela")) {
                    int cisloParcely = Integer.parseInt(parts[1]);
                    String popis = parts[2];
                    double poziciaSirky = Double.parseDouble(parts[3]);
                    char sirka = poziciaSirky < 0 ? 'S' : 'N';
                    double poziciaDlzky = Double.parseDouble(parts[4]);
                    char dlzka = poziciaDlzky < 0 ? 'W' : 'E';
                    GPS gps = new GPS(sirka, poziciaSirky, dlzka, poziciaDlzky);
                    Parcela parcelaReferencia = null;
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
                    Parcela parcela = new Parcela(cisloParcely, popis, null, parcelaReferencia, gps);
                    parcelaReferencia.setReferenciaNaRovnakuParceluSInymiGPS(parcela);
                    Vrchol<Parcela> vrchol = new Vrchol<>(parcela);
                    Vrchol<Parcela> vrchol1 = new Vrchol<>(parcelaReferencia);
                    kdStromParciel.vloz(vrchol);
                    kdStromParciel.vloz(vrchol1);
                    Vrchol<GeografickyObjekt> vrcholGeografickyObjekt = new Vrchol<>(new GeografickyObjekt(gps, null, parcela));
                    Vrchol<GeografickyObjekt> vrcholGeografickyObjektReferencia = new Vrchol<>(new GeografickyObjekt(gpsReferencie, null, parcelaReferencia));
                    kdStromGeografickychObjektov.vloz(vrcholGeografickyObjekt);
                    kdStromGeografickychObjektov.vloz(vrcholGeografickyObjektReferencia);
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
