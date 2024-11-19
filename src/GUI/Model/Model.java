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

    /**
     * Konstruktor triedy Model
     * */
    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        this.vkladanieNehnutelnosti = new VkladanieGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        this.vkladanieParciel = new VkladanieGeografickychObjektov<>(this.kdStromParciel);
        this.kdStromGeografickychObjektov = new KdStrom<>(2);
        this.vkladanieGeografickychObjektov = new VkladanieGeografickychObjektov<>(this.kdStromGeografickychObjektov);
    }

    /**
     * Metoda vyhladania nehnutelnosti
     * @param gpsPositions GPS suradnice
     * @return ArrayList<Vrchol<Nehnutelnost>> - zoznam vrcholov nehnutelnosti
     * */
    public ArrayList<Vrchol<Nehnutelnost>> vyhladajNehnutelnost(ArrayList<GPS> gpsPositions) {

        Nehnutelnost vyhladavanaNehnutelnost = new Nehnutelnost(0, "", null, gpsPositions.getFirst(), gpsPositions.getLast());

        Vrchol<Nehnutelnost> vrcholVyhladavania = new Vrchol<Nehnutelnost>(gpsPositions.getFirst() ,vyhladavanaNehnutelnost);
        Vrchol<Nehnutelnost> vrcholVyhladavania2 = new Vrchol<Nehnutelnost>(gpsPositions.getLast(), vyhladavanaNehnutelnost);
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania);
        kluce.add(vrcholVyhladavania2);
        return kdStromNehnutelnosti.bodoveVyhladavanie(kluce);
    }

    /**
     * Metoda pridania nehnutelnosti
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param gpsPositions GPS suradnice
     * */
    public void pridajNehnutelnost(int supisneCislo, String popis, ArrayList<GPS> gpsPositions) {
        Nehnutelnost newNehnutelnost1 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getFirst());
        Nehnutelnost newNehnutelnost2 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Nehnutelnost>> vysledok = vkladanieNehnutelnosti.metodaVkladania(newNehnutelnost1, newNehnutelnost2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), vysledok.getFirst().getData(), null);
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), vysledok.getLast().getData(), null);
        vkladanieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    /**
     * Metoda vyhladania parciel
     * @param gpsPositions GPS suradnice
     * @return ArrayList<Vrchol<Parcela>> - zoznam vrcholov parciel
     * */
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

    /**
     * Metoda pridania parciel
     * @param cisloParcely cislo parcely
     * @param popis popis parcely
     * @param gpsPositions GPS suradnice
     * */
    public void pridajParcelu(int cisloParcely, String popis, ArrayList<GPS> gpsPositions) {
        Parcela newParcela1 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getFirst());
        Parcela newParcela2 = new Parcela(cisloParcely, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Parcela>> vysledok = vkladanieParciel.metodaVkladania(newParcela1, newParcela2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), null, vysledok.getFirst().getData());
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), null, vysledok.getLast().getData());
        vkladanieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    /**
     * Metoda generovania dat
     * @param pocetNehnutelnosti pocet nehnutelnosti
     * @param pocetParciel pocet parciel
     * @param pravdepodobnostPrekrytia pravdepodobnost prekrytia
     * */
    public void generujData(int pocetNehnutelnosti, int pocetParciel, double pravdepodobnostPrekrytia) {
        Random random = new Random(System.nanoTime());
        int vygenerovanychNehnutelnosti = 0;
        for (int i = 0; i < pocetParciel; i++) {
            char sirka = 'N';
            double poziciaSirky1 = random.nextDouble(100);
            char dlzka = 'E';
            double poziciaDlzky1 = random.nextDouble(100);
            GPS GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
            double poziciaSirky2 = random.nextDouble(100);
            double poziciaDlzky2 = random.nextDouble(100);
            GPS GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
            pridajParcelu(i, "Parcela " + i, new ArrayList<>(List.of(GPSsuradnice1, GPSsuradnice2)));
            if (vygenerovanychNehnutelnosti < pocetNehnutelnosti) {
                if (random.nextDouble() < pravdepodobnostPrekrytia) {
                    vygenerovanychNehnutelnosti = getVygenerovanychNehnutelnosti(random, vygenerovanychNehnutelnosti, i, sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                } else {
                    poziciaSirky1 = random.nextDouble(100);
                    poziciaDlzky1 = random.nextDouble(100);
                    vygenerovanychNehnutelnosti = getVygenerovanychNehnutelnosti(random, vygenerovanychNehnutelnosti, i, sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                }
            }
        }
    }

    private int getVygenerovanychNehnutelnosti(Random random, int vygenerovanychNehnutelnosti, int i, char sirka, double poziciaSirky1, char dlzka, double poziciaDlzky1) {
        GPS GPSsuradnice1;
        double poziciaSirky2;
        double poziciaDlzky2;
        GPS GPSsuradnice2;
        GPSsuradnice1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
        poziciaSirky2 = random.nextDouble(100);
        poziciaDlzky2 = random.nextDouble(100);
        GPSsuradnice2 = new GPS(sirka, poziciaSirky2, dlzka, poziciaDlzky2);
        pridajNehnutelnost(i, "Nehnutelnost " + i, new ArrayList<>(List.of(GPSsuradnice1, GPSsuradnice2)));
        vygenerovanychNehnutelnosti++;
        return vygenerovanychNehnutelnosti;
    }

    /**
     * Metoda ulozenia dat
     * @param path cesta k suboru
     * */
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
        writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice1().getPoziciaSirky() + "," + parcela.getGPSsuradnice1().getPoziciaDlzky() + "\n");
        Parcela referencia = parcela.getReferenciaNaRovnakuParceluSInymiGPS();
        writer.write("Parcela," + referencia.getCisloParcely() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice1().getPoziciaSirky() + "," + referencia.getGPSsuradnice1().getPoziciaDlzky() + "\n");
    }

    private void zapisNehnutelnost(FileWriter writer, Nehnutelnost nehnutelnost) throws IOException {
        writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice1().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice1().getPoziciaDlzky() + "\n");
        Nehnutelnost referencia = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
        writer.write("Nehnutelnost," + referencia.getSupisneCislo() + "," + referencia.getPopis() + "," + referencia.getGPSsuradnice1().getPoziciaSirky() + "," + referencia.getGPSsuradnice1().getPoziciaDlzky() + "\n");
    }

    /**
     * Metoda nacitania dat
     * @param path cesta k suboru
     * */
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

    /**
     * Metoda vyhladania vsetkych geografickych objektov
     * @param gpsPositions GPS suradnice
     * @return ArrayList<Vrchol<GeografickyObjekt>> - zoznam vrcholov geografickych objektov
     * */
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

    /**
     * Metoda vypisu vsetkych geografickych objektov
     * @return ArrayList<Vrchol<GeografickyObjekt>> - zoznam vrcholov geografickych objektov
     * */
    public ArrayList<Vrchol<GeografickyObjekt>> vypisVsetky() {
        return kdStromGeografickychObjektov.preOrderPrehliadka();
    }

    /**
     * Metoda upravy nehnutelnosti
     * @param objektPredEditaciou objekt pred editaciou
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param pozicia1 GPS suradnice 1
     * @param pozicia2 GPS suradnice 2
     * */
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

    /**
     * Metoda upravy parcel
     * @param objektPredEditaciou objekt pred editaciou
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param pozicia1 GPS suradnice 1
     * @param pozicia2 GPS suradnice 2
     * */
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

    /**
     * Metoda odstranenia nehnutelnosti
     * @param objektMazania objekt na mazanie
     * */
    public void odstranNehnutelnost(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Nehnutelnost nehnutelnostNaVymazanie1 = new Nehnutelnost(0, "", null, null, GPSsuradnice1);
        Nehnutelnost nehnutelnostNaVymazanie2 = new Nehnutelnost(0, "", null, null, GPSsuradnice2);
        vyradNehnutelnost(GPSsuradnice1, GPSsuradnice2, nehnutelnostNaVymazanie1, nehnutelnostNaVymazanie2);
    }

    private void vyradNehnutelnost(GPS GPSsuradnice1, GPS GPSsuradnice2, Nehnutelnost nehnutelnostNaVymazanie1, Nehnutelnost nehnutelnostNaVymazanie2) {
        Vrchol<Nehnutelnost> nehnutelnostVrchol = kdStromNehnutelnosti.vyhladaj(new Vrchol<>(nehnutelnostNaVymazanie1));
        Vrchol<GeografickyObjekt> hladanyVrchol = new Vrchol<>(new GeografickyObjekt(GPSsuradnice1, nehnutelnostVrchol.getData(), null));
        Vrchol<Nehnutelnost> nehnutelnostVrcholReferencia = kdStromNehnutelnosti.vyhladaj(new Vrchol<>(nehnutelnostNaVymazanie2));
        Vrchol<GeografickyObjekt> hladanyVrcholReferencia = new Vrchol<>(new GeografickyObjekt(GPSsuradnice2, nehnutelnostVrcholReferencia.getData(), null));
        for (Parcela parcela : nehnutelnostVrchol.getData().getZoznamParciel()) {
            parcela.removeNehnutelnost(nehnutelnostVrchol.getData());
        }
        for (Parcela parcela : nehnutelnostVrcholReferencia.getData().getZoznamParciel()) {
            parcela.removeNehnutelnost(nehnutelnostVrcholReferencia.getData());
        }
        kdStromGeografickychObjektov.vyrad(hladanyVrchol);
        kdStromGeografickychObjektov.vyrad(hladanyVrcholReferencia);
        kdStromNehnutelnosti.vyrad(new Vrchol<>(nehnutelnostNaVymazanie1));
        kdStromNehnutelnosti.vyrad(new Vrchol<>(nehnutelnostNaVymazanie2));
    }

    /**
     * Metoda odstranenia parciel
     * @param objektMazania objekt na mazanie
     * */
    public void odstranParcelu(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Parcela parcelaNaVymazanie1 = new Parcela(0, "", null, null, GPSsuradnice1);
        Parcela parcelaNaVymazanie2 = new Parcela(0, "", null, null, GPSsuradnice2);
        vyradParcelu(GPSsuradnice1, GPSsuradnice2, parcelaNaVymazanie1, parcelaNaVymazanie2);
    }

    private void vyradParcelu(GPS GPSsuradnice1, GPS GPSsuradnice2, Parcela parcelaNaVymazanie1, Parcela parcelaNaVymazanie2) {
        Vrchol<Parcela> parcelaVrchol = kdStromParciel.vyhladaj(new Vrchol<>(parcelaNaVymazanie1));
        Vrchol<GeografickyObjekt> hladanyVrchol = new Vrchol<>(new GeografickyObjekt(GPSsuradnice1, null, parcelaVrchol.getData()));
        Vrchol<Parcela> parcelaVrcholReferencia = kdStromParciel.vyhladaj(new Vrchol<>(parcelaNaVymazanie2));
        Vrchol<GeografickyObjekt> hladanyVrcholReferencia = new Vrchol<>(new GeografickyObjekt(GPSsuradnice2, null, parcelaVrcholReferencia.getData()));
        for (Nehnutelnost nehnutelnost : parcelaVrchol.getData().getZoznamNehnutelnosti()) {
            nehnutelnost.removeParcela(parcelaVrchol.getData());
        }
        for (Nehnutelnost nehnutelnost : parcelaVrcholReferencia.getData().getZoznamNehnutelnosti()) {
            nehnutelnost.removeParcela(parcelaVrcholReferencia.getData());
        }
        kdStromGeografickychObjektov.vyrad(hladanyVrchol);
        kdStromGeografickychObjektov.vyrad(hladanyVrcholReferencia);
        kdStromParciel.vyrad(new Vrchol<>(parcelaNaVymazanie1));
        kdStromParciel.vyrad(new Vrchol<>(parcelaNaVymazanie2));
    }
}
