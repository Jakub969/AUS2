package GUI.Model;

import triedy.VkladanieGeografickychObjektov;
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
    private final KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private final KdStrom<Parcela> kdStromParciel;
    private final KdStrom<GeografickyObjekt> kdStromGeografickychObjektov;
    private final VkladanieGeografickychObjektov<Nehnutelnost> vkladanieNehnutelnosti;
    private final VkladanieGeografickychObjektov<Parcela> vkladanieParciel;
    private final VkladanieGeografickychObjektov<GeografickyObjekt> vkladanieGeografickychObjektov;

    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        this.vkladanieNehnutelnosti = new VkladanieGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        this.vkladanieParciel = new VkladanieGeografickychObjektov<>(this.kdStromParciel);
        this.kdStromGeografickychObjektov = new KdStrom<>(2);
        this.vkladanieGeografickychObjektov = new VkladanieGeografickychObjektov<>(this.kdStromGeografickychObjektov);
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
        ArrayList<Vrchol<Nehnutelnost>> vysledok = vkladanieNehnutelnosti.metodaVkladania(newNehnutelnost1, newNehnutelnost2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), vysledok.getFirst().getData(), null);
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), vysledok.getLast().getData(), null);
        vkladanieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    public ArrayList<Vrchol<Parcela>> vyhladajParcelu(ArrayList<GPS> gpsPositions) {
        Parcela vyhladavanaParcela1 = new Parcela(0, "", null, null, gpsPositions.getFirst());
        Parcela vyhladavanaParcela2 = new Parcela(0, "", null, null, gpsPositions.getLast());

        Vrchol<Parcela> vrcholVyhladavania1 = new Vrchol<>(vyhladavanaParcela1);
        Vrchol<Parcela> vrcholVyhladavania2 = new Vrchol<>(vyhladavanaParcela2);
        ArrayList<Vrchol<Parcela>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania1);
        kluce.add(vrcholVyhladavania2);
        return kdStromParciel.bodoveVyhladavanie(kluce);
    }

    public void pridajParcelu(int cisloParcely, String popis, List<GPS> gpsPositions) {
        Parcela newParcela1 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getFirst());
        Parcela newParcela2 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Parcela>> vysledok = vkladanieParciel.metodaVkladania(newParcela1, newParcela2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), null, vysledok.getFirst().getData());
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), null, vysledok.getLast().getData());
        vkladanieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
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
            vkladanieParciel.metodaVkladania(parcela1, parcela2);
            GeografickyObjekt geografickyObjekt1 = new GeografickyObjekt(GPSsuradnice1, null, parcela1);
            GeografickyObjekt geografickyObjekt2 = new GeografickyObjekt(GPSsuradnice2, null, parcela2);
            vkladanieGeografickychObjektov.metodaVkladania(geografickyObjekt1, geografickyObjekt2);
            if (vygenerovanychNehnutelnosti < pocetNehnutelnosti) {
                if (random.nextDouble() < pravdepodobnostPrekrytia) {
                    GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                    Nehnutelnost nehnutelnost1 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice1);
                    poziciaSirky2 = random.nextDouble(100);
                    poziciaDlzky2 = random.nextDouble(100);
                    GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
                    Nehnutelnost nehnutelnost2 = new Nehnutelnost(i, "Nehnutelnost " + i, null, null, GPSsuradnice2);
                    vkladanieNehnutelnosti.metodaVkladania(nehnutelnost1, nehnutelnost2);
                    GeografickyObjekt geografickyObjekt3 = new GeografickyObjekt(GPSsuradnice1, nehnutelnost1, null);
                    GeografickyObjekt geografickyObjekt4 = new GeografickyObjekt(GPSsuradnice2, nehnutelnost2, null);
                    vkladanieGeografickychObjektov.metodaVkladania(geografickyObjekt3, geografickyObjekt4);
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
                    vkladanieNehnutelnosti.metodaVkladania(nehnutelnost1, nehnutelnost2);
                    GeografickyObjekt geografickyObjekt3 = new GeografickyObjekt(GPSsuradnice1, nehnutelnost1, null);
                    GeografickyObjekt geografickyObjekt4 = new GeografickyObjekt(GPSsuradnice2, nehnutelnost2, null);
                    vkladanieGeografickychObjektov.metodaVkladania(geografickyObjekt3, geografickyObjekt4);
                    vygenerovanychNehnutelnosti++;
                }
            }
        }
    }

    public void saveData(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            ArrayList<Vrchol<Nehnutelnost>> nehnutelnosti = kdStromNehnutelnosti.preOrderPrehliadka();
            ArrayList<String> uuid = new ArrayList<>();
            if (nehnutelnosti != null) {
                for (Vrchol<Nehnutelnost> nehnutelnostVrchol : nehnutelnosti) {
                    if (uuid.contains(nehnutelnostVrchol.getData().getUuid())) {
                        continue;
                    } else {
                        uuid.add(nehnutelnostVrchol.getData().getUuid());
                        uuid.add(nehnutelnostVrchol.getData().getReferenciaNaRovnakuNehnutelnostSInymiGPS().getUuid());
                    }
                    Nehnutelnost nehnutelnost = nehnutelnostVrchol.getData();
                    ArrayList<Vrchol<Nehnutelnost>> duplicity = nehnutelnostVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    zapisNehnutelnost(writer, nehnutelnost);
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Nehnutelnost> duplicityVrchol : duplicity) {
                            Nehnutelnost duplicityNehnutelnost = duplicityVrchol.getData();
                            zapisNehnutelnost(writer, duplicityNehnutelnost);
                        }
                    }
                }
            }
            ArrayList<Vrchol<Parcela>> parciel = kdStromParciel.preOrderPrehliadka();
            ArrayList<String> uuidParcela = new ArrayList<>();
            if (parciel != null) {
                for (Vrchol<Parcela> parcelaVrchol : parciel) {
                    if (uuidParcela.contains(parcelaVrchol.getData().getUuid())) {
                        continue;
                    } else {
                        uuidParcela.add(parcelaVrchol.getData().getUuid());
                        uuidParcela.add(parcelaVrchol.getData().getReferenciaNaRovnakuParceluSInymiGPS().getUuid());
                    }
                    Parcela parcela = parcelaVrchol.getData();
                    ArrayList<Vrchol<Parcela>> duplicity = parcelaVrchol.getDuplicity();
                    int pocetDuplicity = duplicity != null ? duplicity.size() : 0;
                    zapisParcelu(writer, parcela);
                    if (pocetDuplicity != 0) {
                        for (Vrchol<Parcela> duplicityVrchol : duplicity) {
                            Parcela duplicityParcela = duplicityVrchol.getData();
                            zapisParcelu(writer, duplicityParcela);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zapisParcelu(FileWriter writer, Parcela parcela) throws IOException {
        writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice().getPoziciaSirky() + "," + parcela.getGPSsuradnice().getPoziciaDlzky() + "\n");
        Parcela referencia = parcela.getReferenciaNaRovnakuParceluSInymiGPS();
        writer.write("Parcela," + referencia.getCisloParcely() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
    }

    private void zapisNehnutelnost(FileWriter writer, Nehnutelnost nehnutelnost) throws IOException {
        writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice().getPoziciaDlzky() + "\n");
        Nehnutelnost referencia = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
        writer.write("Nehnutelnost," + referencia.getSupisneCislo() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice().getPoziciaSirky() + "," + referencia.getGPSsuradnice().getPoziciaDlzky() + "\n");
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
                    vkladanieNehnutelnosti.metodaVkladania(nehnutelnost, nehnutelnostReferencia);
                    GeografickyObjekt geografickyObjekt = new GeografickyObjekt(gps, nehnutelnost, null);
                    GeografickyObjekt geografickyObjektReferencia = new GeografickyObjekt(gpsReferencie, nehnutelnostReferencia, null);
                    vkladanieGeografickychObjektov.metodaVkladania(geografickyObjekt, geografickyObjektReferencia);
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
                    vkladanieParciel.metodaVkladania(parcela, parcelaReferencia);
                    GeografickyObjekt geografickyObjekt = new GeografickyObjekt(gps, null, parcela);
                    GeografickyObjekt geografickyObjektReferencia = new GeografickyObjekt(gpsReferencie, null, parcelaReferencia);
                    vkladanieGeografickychObjektov.metodaVkladania(geografickyObjekt, geografickyObjektReferencia);
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

    public void upravNehnutelnost(String objektPredEditaciou, int supisneCislo, String popis, GPS pozicia1, GPS pozicia2) {
        GPS[] suradnice = vytvorGPSsuradnice(objektPredEditaciou);
        GPS staraGPSsuradnice1 = suradnice[0];
        GPS staraGPSsuradnice2 = suradnice[1];
        double tolerancia = 0.000001;
        if ((Math.abs(staraGPSsuradnice1.getPoziciaSirky() - pozicia1.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice1.getPoziciaDlzky() - pozicia1.getPoziciaDlzky()) <= tolerancia)
                || (Math.abs(staraGPSsuradnice2.getPoziciaSirky() - pozicia2.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice2.getPoziciaDlzky() - pozicia2.getPoziciaDlzky()) <= tolerancia)) {
            Vrchol<Nehnutelnost> vrchol = kdStromNehnutelnosti.vyhladaj(new Vrchol<>(new Nehnutelnost(supisneCislo, "", null, null, staraGPSsuradnice1)));
            Nehnutelnost nehnutelnost = vrchol.getData();
            nehnutelnost.setPopis(popis);
            nehnutelnost.setSupisneCislo(supisneCislo);
        } else {
            Nehnutelnost nehnutelnostNaVymazanie1 = new Nehnutelnost(supisneCislo, "", null, null, staraGPSsuradnice1);
            Nehnutelnost nehnutelnostNaVymazanie2 = new Nehnutelnost(supisneCislo, "", null, null, staraGPSsuradnice2);
            vyradNehnutelnost(staraGPSsuradnice1, staraGPSsuradnice2, nehnutelnostNaVymazanie1, nehnutelnostNaVymazanie2);
            Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis, null, null, pozicia1);
            Nehnutelnost nehnutelnostReferencia = new Nehnutelnost(supisneCislo, popis, null, nehnutelnost, pozicia2);
            vkladanieNehnutelnosti.metodaVkladania(nehnutelnost, nehnutelnostReferencia);
            vkladanieGeografickychObjektov.metodaVkladania(new GeografickyObjekt(pozicia1, nehnutelnost, null), new GeografickyObjekt(pozicia2, nehnutelnostReferencia, null));
        }
    }

    private GPS[] vytvorGPSsuradnice(String objektPredEditaciou) {
        String staraGPS1 = objektPredEditaciou.split(",")[1];
        String staraGPS2 = objektPredEditaciou.split(",")[2];

        char sirka1 = staraGPS1.charAt(0);
        String gps1Part = staraGPS1.split(" ")[1];
        if (gps1Part.endsWith(";")) {
            gps1Part = gps1Part.substring(0, gps1Part.length() - 1);
        }
        double poziciaSirky1 = Double.parseDouble(gps1Part);
        double poziciaDlzky1 = Double.parseDouble(staraGPS1.split(" ")[3]);
        char dlzka1 = staraGPS1.split(" ")[2].charAt(0);

        char sirka2 = staraGPS2.charAt(0);
        String gps2Part = staraGPS2.split(" ")[1];
        if (gps2Part.endsWith(";")) {
            gps2Part = gps2Part.substring(0, gps2Part.length() - 1);
        }
        double poziciaSirky2 = Double.parseDouble(gps2Part);
        double poziciaDlzky2 = Double.parseDouble(staraGPS2.split(" ")[3]);
        char dlzka2 = staraGPS2.split(" ")[2].charAt(0);

        GPS staraGPSsuradnice1 = new GPS(sirka1, poziciaSirky1, dlzka1, poziciaDlzky1);
        GPS staraGPSsuradnice2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);
        return new GPS[]{staraGPSsuradnice1, staraGPSsuradnice2};
    }

    public void upravParcelu(String objektPredEditaciou, int supisneCislo, String popis, GPS pozicia1, GPS pozicia2) {
        GPS[] suradnice = vytvorGPSsuradnice(objektPredEditaciou);
        GPS staraGPSsuradnice1 = suradnice[0];
        GPS staraGPSsuradnice2 = suradnice[1];
        double tolerancia = 0.000001;
        if ((Math.abs(staraGPSsuradnice1.getPoziciaSirky() - pozicia1.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice1.getPoziciaDlzky() - pozicia1.getPoziciaDlzky()) <= tolerancia)
                || (Math.abs(staraGPSsuradnice2.getPoziciaSirky() - pozicia2.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice2.getPoziciaDlzky() - pozicia2.getPoziciaDlzky()) <= tolerancia)) {
            Vrchol<Parcela> vrchol = kdStromParciel.vyhladaj(new Vrchol<>(new Parcela(supisneCislo, "", null, null, staraGPSsuradnice1)));
            Parcela parcela = vrchol.getData();
            parcela.setPopis(popis);
            parcela.setCisloParcely(supisneCislo);
        } else {
            Parcela parcelaNaVymazanie1 = new Parcela(supisneCislo, "", null, null, staraGPSsuradnice1);
            Parcela parcelaNaVymazanie2 = new Parcela(supisneCislo, "", null, null, staraGPSsuradnice2);
            vyradParcelu(staraGPSsuradnice1, staraGPSsuradnice2, parcelaNaVymazanie1, parcelaNaVymazanie2);
            Parcela parcela = new Parcela(supisneCislo, popis, null, null, pozicia1);
            Parcela parcelaReferencia = new Parcela(supisneCislo, popis, null, parcela, pozicia2);
            vkladanieParciel.metodaVkladania(parcela, parcelaReferencia);
            vkladanieGeografickychObjektov.metodaVkladania(new GeografickyObjekt(pozicia1, null, parcela), new GeografickyObjekt(pozicia2, null, parcelaReferencia));
        }
    }

    public void odstranNehnutelnost(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Nehnutelnost nehnutelnostNaVymazanie1 = new Nehnutelnost(0, "", null, null, GPSsuradnice1);
        Nehnutelnost nehnutelnostNaVymazanie2 = new Nehnutelnost(0, "", null, null, GPSsuradnice2);
        vyradNehnutelnost(GPSsuradnice1, GPSsuradnice2, nehnutelnostNaVymazanie1, nehnutelnostNaVymazanie2);
    }

    private void vyradNehnutelnost(GPS GPSsuradnice1, GPS GPSsuradnice2, Nehnutelnost nehnutelnostNaVymazanie1, Nehnutelnost nehnutelnostNaVymazanie2) {
        ArrayList<Vrchol<GeografickyObjekt>> geografickeObjekty = kdStromGeografickychObjektov.preOrderPrehliadka();
        ArrayList<Vrchol<GeografickyObjekt>> duplicity = new ArrayList<>();
        for (Vrchol<GeografickyObjekt> geografickyObjektVrchol : geografickeObjekty) {
            duplicity.addAll(geografickyObjektVrchol.getDuplicity());
        }
        geografickeObjekty.addAll(duplicity);
        System.out.println("Počet geografických objektov pred vymazaním: " + geografickeObjekty.size());
        kdStromGeografickychObjektov.vyrad(new Vrchol<>(new GeografickyObjekt(GPSsuradnice1, nehnutelnostNaVymazanie1, null)));
        kdStromGeografickychObjektov.vyrad(new Vrchol<>(new GeografickyObjekt(GPSsuradnice2, nehnutelnostNaVymazanie2, null)));
        geografickeObjekty = kdStromGeografickychObjektov.preOrderPrehliadka();
        duplicity.clear();
        for (Vrchol<GeografickyObjekt> geografickyObjektVrchol : geografickeObjekty) {
            duplicity.addAll(geografickyObjektVrchol.getDuplicity());
        }
        geografickeObjekty.addAll(duplicity);
        System.out.println("Počet geografických objektov po vymazaní: " + geografickeObjekty.size());
        System.out.println("Počet nehnuteľností pred vymazaním: " + kdStromNehnutelnosti.preOrderPrehliadka().size());
        kdStromNehnutelnosti.vyrad(new Vrchol<>(nehnutelnostNaVymazanie1));
        kdStromNehnutelnosti.vyrad(new Vrchol<>(nehnutelnostNaVymazanie2));
        System.out.println("Počet nehnuteľností po vymazaní: " + kdStromNehnutelnosti.preOrderPrehliadka().size());

    }

    public void odstranParcelu(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Parcela parcelaNaVymazanie1 = new Parcela(0, "", null, null, GPSsuradnice1);
        Parcela parcelaNaVymazanie2 = new Parcela(0, "", null, null, GPSsuradnice2);
        vyradParcelu(GPSsuradnice1, GPSsuradnice2, parcelaNaVymazanie1, parcelaNaVymazanie2);
    }

    private void vyradParcelu(GPS GPSsuradnice1, GPS GPSsuradnice2, Parcela parcelaNaVymazanie1, Parcela parcelaNaVymazanie2) {
        kdStromParciel.vyrad(new Vrchol<>(parcelaNaVymazanie1));
        kdStromParciel.vyrad(new Vrchol<>(parcelaNaVymazanie2));
        kdStromGeografickychObjektov.vyrad(new Vrchol<>(new GeografickyObjekt(GPSsuradnice1, null, parcelaNaVymazanie1)));
        kdStromGeografickychObjektov.vyrad(new Vrchol<>(new GeografickyObjekt(GPSsuradnice2, null, parcelaNaVymazanie2)));
    }
}
