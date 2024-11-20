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
    private final KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private final KdStrom<Parcela> kdStromParciel;
    private final KdStrom<GeografickyObjekt> kdStromGeografickychObjektov;
    private final OperacieGeografickychObjektov<Nehnutelnost> operacieNehnutelnosti;
    private final OperacieGeografickychObjektov<Parcela> operacieParciel;
    private final OperacieGeografickychObjektov<GeografickyObjekt> operacieGeografickychObjektov;

    /**
     * Konstruktor triedy Model
     * */
    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        this.operacieNehnutelnosti = new OperacieGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        this.operacieParciel = new OperacieGeografickychObjektov<>(this.kdStromParciel);
        this.kdStromGeografickychObjektov = new KdStrom<>(2);
        this.operacieGeografickychObjektov = new OperacieGeografickychObjektov<>(this.kdStromGeografickychObjektov);
    }

    /**
     * Metoda vyhladania nehnutelnosti
     * @param gpsPositions GPS suradnice
     * @return ArrayList<Vrchol<Nehnutelnost>> - zoznam vrcholov nehnutelnosti
     * */
    public ArrayList<Vrchol<Nehnutelnost>> vyhladajNehnutelnost(ArrayList<GPS> gpsPositions) {

        Nehnutelnost vyhladavanaNehnutelnost = new Nehnutelnost(0, "", null, gpsPositions.getFirst(), gpsPositions.getLast());
        Vrchol<Nehnutelnost> vrcholVyhladavania = null;
        if (gpsPositions.getFirst() != null) {
            vrcholVyhladavania = new Vrchol<Nehnutelnost>(gpsPositions.getFirst() ,vyhladavanaNehnutelnost);
        }
        Vrchol<Nehnutelnost> vrcholVyhladavania2 = null;
        if (gpsPositions.getLast() != null) {
            vrcholVyhladavania2 = new Vrchol<Nehnutelnost>(gpsPositions.getLast(), vyhladavanaNehnutelnost);
        }
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        if (vrcholVyhladavania != null) {
            kluce.add(vrcholVyhladavania);
        }
        if (vrcholVyhladavania2 != null) {
            kluce.add(vrcholVyhladavania2);
        }
        return kdStromNehnutelnosti.bodoveVyhladavanie(kluce);
    }

    /**
     * Metoda pridania nehnutelnosti
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param gpsPositions GPS suradnice
     * */
    public void pridajNehnutelnost(int supisneCislo, String popis, ArrayList<GPS> gpsPositions) {
        Nehnutelnost newNehnutelnost1 = new Nehnutelnost(supisneCislo, popis, null, gpsPositions.getFirst(), gpsPositions.getLast());
        operacieNehnutelnosti.metodaVkladania(newNehnutelnost1);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), gpsPositions.getLast(), newNehnutelnost1, null);
        operacieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1);
    }

    /**
     * Metoda vyhladania parciel
     * @param gpsPositions GPS suradnice
     * @return ArrayList<Vrchol<Parcela>> - zoznam vrcholov parciel
     * */
    public ArrayList<Vrchol<Parcela>> vyhladajParcelu(ArrayList<GPS> gpsPositions) {
        Parcela vyhladavanaParcela1 = new Parcela(0, "", null, gpsPositions.getFirst(), gpsPositions.getLast());

        Vrchol<Parcela> vrcholVyhladavania1 = null;
        if (gpsPositions.getFirst() != null) {
            vrcholVyhladavania1 = new Vrchol<Parcela>(gpsPositions.getFirst(), vyhladavanaParcela1);
        }
        Vrchol<Parcela> vrcholVyhladavania2 = null;
        if (gpsPositions.getLast() != null) {
            vrcholVyhladavania2 = new Vrchol<Parcela>(gpsPositions.getLast(), vyhladavanaParcela1);
        }
        ArrayList<Vrchol<Parcela>> kluce = new ArrayList<>();
        if (vrcholVyhladavania1 != null) {
            kluce.add(vrcholVyhladavania1);
        }
        if (vrcholVyhladavania2 != null) {
            kluce.add(vrcholVyhladavania2);
        }
        return kdStromParciel.bodoveVyhladavanie(kluce);
    }

    /**
     * Metoda pridania parciel
     * @param cisloParcely cislo parcely
     * @param popis popis parcely
     * @param gpsPositions GPS suradnice
     * */
    public void pridajParcelu(int cisloParcely, String popis, ArrayList<GPS> gpsPositions) {
        Parcela newParcela1 = new Parcela(cisloParcely, popis, null, gpsPositions.getFirst(), gpsPositions.getLast());
        operacieParciel.metodaVkladania(newParcela1);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), gpsPositions.getLast(), null, newParcela1);
        operacieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1);
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
            ArrayList<Vrchol<Parcela>> parciel = kdStromParciel.preOrderPrehliadka();
            ArrayList<String> uuidParcela = new ArrayList<>();
            if (parciel != null) {
                for (Vrchol<Parcela> parcelaVrchol : parciel) {
                    if (uuidParcela.contains(parcelaVrchol.getData().getUuid())) {
                        continue;
                    } else {
                        uuidParcela.add(parcelaVrchol.getData().getUuid());
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
            ArrayList<Vrchol<Nehnutelnost>> nehnutelnosti = kdStromNehnutelnosti.preOrderPrehliadka();
            ArrayList<String> uuid = new ArrayList<>();
            if (nehnutelnosti != null) {
                for (Vrchol<Nehnutelnost> nehnutelnostVrchol : nehnutelnosti) {
                    if (uuid.contains(nehnutelnostVrchol.getData().getUuid())) {
                        continue;
                    } else {
                        uuid.add(nehnutelnostVrchol.getData().getUuid());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zapisParcelu(FileWriter writer, Parcela parcela) throws IOException {
        writer.write("Parcela," + parcela.getCisloParcely() + "," + parcela.getPopis() + "," + parcela.getGPSsuradnice1().getPoziciaSirky() + "," + parcela.getGPSsuradnice1().getPoziciaDlzky() + "," + parcela.getGPSsuradnice2().getPoziciaSirky() + "," + parcela.getGPSsuradnice2().getPoziciaDlzky() + "\n");
    }

    private void zapisNehnutelnost(FileWriter writer, Nehnutelnost nehnutelnost) throws IOException {
        writer.write("Nehnutelnost," + nehnutelnost.getSupisneCislo() + "," + nehnutelnost.getPopis() + "," + nehnutelnost.getGPSsuradnice1().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice1().getPoziciaDlzky() + "," + nehnutelnost.getGPSsuradnice2().getPoziciaSirky() + "," + nehnutelnost.getGPSsuradnice2().getPoziciaDlzky() +"\n");
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
                    double poziciaSirky1 = Double.parseDouble(parts[3]);
                    char sirka = poziciaSirky1 < 0 ? 'S' : 'N';
                    double poziciaDlzky1 = Double.parseDouble(parts[4]);
                    char dlzka = poziciaDlzky1 < 0 ? 'W' : 'E';
                    GPS gps1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                    double poziciaSirky2 = Double.parseDouble(parts[5]);
                    char sirka2 = poziciaSirky2 < 0 ? 'S' : 'N';
                    double poziciaDlzky2 = Double.parseDouble(parts[6]);
                    char dlzka2 = poziciaDlzky2 < 0 ? 'W' : 'E';
                    GPS gps2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);
                    Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis, null, gps1, gps2);
                    operacieNehnutelnosti.metodaVkladania(nehnutelnost);
                    GeografickyObjekt geografickyObjekt = new GeografickyObjekt(gps1, gps2, nehnutelnost, null);
                    operacieGeografickychObjektov.metodaVkladania(geografickyObjekt);
                } else if (parts[0].equals("Parcela")) {
                    int cisloParcely = Integer.parseInt(parts[1]);
                    String popis = parts[2];
                    double poziciaSirky1 = Double.parseDouble(parts[3]);
                    char sirka = poziciaSirky1 < 0 ? 'S' : 'N';
                    double poziciaDlzky1 = Double.parseDouble(parts[4]);
                    char dlzka = poziciaDlzky1 < 0 ? 'W' : 'E';
                    GPS gps1 = new GPS(sirka, poziciaSirky1, dlzka, poziciaDlzky1);
                    double poziciaSirky2 = Double.parseDouble(parts[5]);
                    char sirka2 = poziciaSirky2 < 0 ? 'S' : 'N';
                    double poziciaDlzky2 = Double.parseDouble(parts[6]);
                    char dlzka2 = poziciaDlzky2 < 0 ? 'W' : 'E';
                    GPS gps2 = new GPS(sirka2, poziciaSirky2, dlzka2, poziciaDlzky2);
                    Parcela parcela = new Parcela(cisloParcely, popis, null, gps1, gps2);
                    operacieParciel.metodaVkladania(parcela);
                    GeografickyObjekt geografickyObjekt = new GeografickyObjekt(gps1, gps2, null, parcela);
                    operacieGeografickychObjektov.metodaVkladania(geografickyObjekt);
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
        GeografickyObjekt vyhladavanyGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), gpsPositions.getLast(), null, null);

        Vrchol<GeografickyObjekt> vrcholVyhladavania1 = new Vrchol<GeografickyObjekt>(gpsPositions.getFirst(), vyhladavanyGeografickyObjekt1);
        Vrchol<GeografickyObjekt> vrcholVyhladavania2 = new Vrchol<GeografickyObjekt>(gpsPositions.getLast(), vyhladavanyGeografickyObjekt1);
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
        int stareSupisneCislo = Integer.parseInt(objektPredEditaciou.split(",")[3]);
        GPS staraGPSsuradnice1 = suradnice[0];
        GPS staraGPSsuradnice2 = suradnice[1];
        double tolerancia = 0.000001;
        if ((Math.abs(staraGPSsuradnice1.getPoziciaSirky() - pozicia1.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice1.getPoziciaDlzky() - pozicia1.getPoziciaDlzky()) <= tolerancia)
                || (Math.abs(staraGPSsuradnice2.getPoziciaSirky() - pozicia2.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice2.getPoziciaDlzky() - pozicia2.getPoziciaDlzky()) <= tolerancia)) {
            Vrchol<Nehnutelnost> vrchol = kdStromNehnutelnosti.vyhladaj(new Vrchol<Nehnutelnost>(staraGPSsuradnice1, new Nehnutelnost(stareSupisneCislo, "", null, staraGPSsuradnice1, staraGPSsuradnice2)));
            Nehnutelnost nehnutelnost = getNehnutelnost(vrchol, stareSupisneCislo);
            nehnutelnost.setPopis(popis);
            nehnutelnost.setSupisneCislo(supisneCislo);
        } else {
            Vrchol<Nehnutelnost> vrchol = kdStromNehnutelnosti.vyhladaj(new Vrchol<Nehnutelnost>(staraGPSsuradnice1, new Nehnutelnost(stareSupisneCislo, "", null, staraGPSsuradnice1, staraGPSsuradnice2)));
            Nehnutelnost nehnutelnostNaVymazanie1 = getNehnutelnost(vrchol, stareSupisneCislo);
            vyradNehnutelnost(staraGPSsuradnice1, staraGPSsuradnice2, nehnutelnostNaVymazanie1);
            Nehnutelnost nehnutelnost = new Nehnutelnost(supisneCislo, popis, null, pozicia1, pozicia2);
            operacieNehnutelnosti.metodaVkladania(nehnutelnost);
            operacieGeografickychObjektov.metodaVkladania(new GeografickyObjekt(pozicia1, pozicia2, nehnutelnost, null));
        }
    }

    private Nehnutelnost getNehnutelnost(Vrchol<Nehnutelnost> vrchol, int stareSupisneCislo) {
        Nehnutelnost nehnutelnost = vrchol.getData();
        if (nehnutelnost.getSupisneCislo() != stareSupisneCislo) {
            ArrayList<Vrchol<Nehnutelnost>> duplicity = vrchol.getDuplicity();
            if (duplicity != null) {
                for (Vrchol<Nehnutelnost> duplicityVrchol : duplicity) {
                    Nehnutelnost duplicityNehnutelnost = duplicityVrchol.getData();
                    if (duplicityNehnutelnost.getSupisneCislo() == stareSupisneCislo) {
                        nehnutelnost = duplicityNehnutelnost;
                    }
                }
            }
        }
        return nehnutelnost;
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
        int stareSupisneCislo = Integer.parseInt(objektPredEditaciou.split(",")[3]);
        GPS staraGPSsuradnice1 = suradnice[0];
        GPS staraGPSsuradnice2 = suradnice[1];
        double tolerancia = 0.000001;
        if ((Math.abs(staraGPSsuradnice1.getPoziciaSirky() - pozicia1.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice1.getPoziciaDlzky() - pozicia1.getPoziciaDlzky()) <= tolerancia)
                || (Math.abs(staraGPSsuradnice2.getPoziciaSirky() - pozicia2.getPoziciaSirky()) <= tolerancia && Math.abs(staraGPSsuradnice2.getPoziciaDlzky() - pozicia2.getPoziciaDlzky()) <= tolerancia)) {
            Vrchol<Parcela> vrchol = kdStromParciel.vyhladaj(new Vrchol<Parcela>(staraGPSsuradnice1,new Parcela(stareSupisneCislo, "", null, staraGPSsuradnice1, staraGPSsuradnice2)));
            Parcela parcela = getParcelu(vrchol, stareSupisneCislo);
            parcela.setPopis(popis);
            parcela.setCisloParcely(supisneCislo);
        } else {
            Vrchol<Parcela> vrchol = kdStromParciel.vyhladaj(new Vrchol<Parcela>(staraGPSsuradnice1, new Parcela(stareSupisneCislo, "", null, staraGPSsuradnice1, staraGPSsuradnice2)));
            Parcela parcelaNaVymazanie1 = getParcelu(vrchol, stareSupisneCislo);
            vyradParcelu(staraGPSsuradnice1, staraGPSsuradnice2, parcelaNaVymazanie1);
            Parcela parcela = new Parcela(supisneCislo, popis, null, pozicia1, pozicia2);
            operacieParciel.metodaVkladania(parcela);
            operacieGeografickychObjektov.metodaVkladania(new GeografickyObjekt(pozicia1, pozicia2, null, parcela));
        }
    }

    private Parcela getParcelu(Vrchol<Parcela> vrchol, int supisneCislo) {
        Parcela parcela = vrchol.getData();
        if (parcela.getCisloParcely() != supisneCislo) {
            ArrayList<Vrchol<Parcela>> duplicity = vrchol.getDuplicity();
            if (duplicity != null) {
                for (Vrchol<Parcela> duplicityVrchol : duplicity) {
                    Parcela duplicityParcela = duplicityVrchol.getData();
                    if (duplicityParcela.getCisloParcely() == supisneCislo) {
                        parcela = duplicityParcela;
                    }
                }
            }
        }
        return parcela;
    }

    /**
     * Metoda odstranenia nehnutelnosti
     * @param objektMazania objekt na mazanie
     * */
    public void odstranNehnutelnost(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        int supisneCislo = Integer.parseInt(objektMazania.split(",")[3]);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Nehnutelnost nehnutelnostNaVymazanie1 = new Nehnutelnost(supisneCislo, "", null, GPSsuradnice1, GPSsuradnice2);
        vyradNehnutelnost(GPSsuradnice1, GPSsuradnice2, nehnutelnostNaVymazanie1);
    }

    private void vyradNehnutelnost(GPS GPSsuradnice1, GPS GPSsuradnice2, Nehnutelnost nehnutelnostNaVymazanie1) {
        Vrchol<Nehnutelnost> nehnutelnostVrchol = kdStromNehnutelnosti.vyhladaj(new Vrchol<Nehnutelnost>(GPSsuradnice1, nehnutelnostNaVymazanie1));
        Nehnutelnost nehnutelnostMazania = getNehnutelnost(nehnutelnostVrchol, nehnutelnostNaVymazanie1.getSupisneCislo());
        Vrchol<GeografickyObjekt> hladanyVrchol = new Vrchol<GeografickyObjekt>(GPSsuradnice1, new GeografickyObjekt(GPSsuradnice1, GPSsuradnice2, nehnutelnostMazania, null));
        for (Parcela parcela : nehnutelnostVrchol.getData().getZoznamParciel()) {
            parcela.removeNehnutelnost(nehnutelnostVrchol.getData());
        }
        operacieGeografickychObjektov.metodaMazania(hladanyVrchol.getData());
        operacieNehnutelnosti.metodaMazania(nehnutelnostMazania);
    }

    /**
     * Metoda odstranenia parciel
     * @param objektMazania objekt na mazanie
     * */
    public void odstranParcelu(String objektMazania) {
        GPS[] suradnice = vytvorGPSsuradnice(objektMazania);
        int cisloParcely = Integer.parseInt(objektMazania.split(",")[3]);
        GPS GPSsuradnice1 = suradnice[0];
        GPS GPSsuradnice2 = suradnice[1];
        Parcela parcelaNaVymazanie1 = new Parcela(cisloParcely, "", null, GPSsuradnice1, GPSsuradnice2);
        vyradParcelu(GPSsuradnice1, GPSsuradnice2, parcelaNaVymazanie1);
    }

    private void vyradParcelu(GPS GPSsuradnice1, GPS GPSsuradnice2, Parcela parcelaNaVymazanie1) {
        Vrchol<Parcela> parcelaVrchol = kdStromParciel.vyhladaj(new Vrchol<Parcela>(GPSsuradnice1, parcelaNaVymazanie1));
        Parcela parcelaMazania = getParcelu(parcelaVrchol, parcelaNaVymazanie1.getCisloParcely());
        Vrchol<GeografickyObjekt> hladanyVrchol = new Vrchol<GeografickyObjekt>(GPSsuradnice1 ,new GeografickyObjekt(GPSsuradnice1, GPSsuradnice2, null, parcelaMazania));
        for (Nehnutelnost nehnutelnost : parcelaVrchol.getData().getZoznamNehnutelnosti()) {
            nehnutelnost.removeParcela(parcelaVrchol.getData());
        }
        operacieGeografickychObjektov.metodaMazania(hladanyVrchol.getData());
        operacieParciel.metodaMazania(parcelaMazania);
    }
}
