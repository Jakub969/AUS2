package triedy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import rozhrania.IKluc;

import java.util.ArrayList;

public class VkladanieGeografickychObjektov<T extends IKluc<T>> {
    private final KdStrom<T> strom;

    /**
     * Konstruktor triedy VkladanieGeografickychObjektov
     * @param kdStrom - instanciu triedy KdStrom
     * */
    public VkladanieGeografickychObjektov(KdStrom<T> kdStrom) {
        this.strom = kdStrom;
    }

    /**
     * Metoda vkladania geografickych objektov
     * @param objekt1 - prvy objekt, ktory sa ma vlozit
     * @param objekt2 - druhy objekt, ktory sa ma vlozit
     * @return ArrayList<Vrchol<T>> - zoznam vrcholov, ktore boli vlozene
     * */
    public ArrayList<Vrchol<T>> metodaVkladania(T objekt1, T objekt2) {
        if (objekt1 instanceof Nehnutelnost dataNehnutelnost1 && objekt2 instanceof Nehnutelnost dataNehnutelnost2) {
            dataNehnutelnost1.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost2);
            dataNehnutelnost2.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
            vrcholy.add(vrchol1);
            vrcholy.add(vrchol2);
            return vrcholy;
        } else if (objekt1 instanceof Parcela dataParcela1 && objekt2 instanceof Parcela dataParcela2) {
            dataParcela1.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela2);
            dataParcela2.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
            vrcholy.add(vrchol1);
            vrcholy.add(vrchol2);
            return vrcholy;
        } else if (objekt1 instanceof GeografickyObjekt dataGeografickyObjekt1 && objekt2 instanceof GeografickyObjekt dataGeografickyObjekt2) {
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            if (vrchol1.isJeDuplicita()) {
                Nehnutelnost nehnutelnost = dataGeografickyObjekt1.getNehnutelnost();
                Parcela parcela = dataGeografickyObjekt1.getParcela();
                Vrchol<T> duplicita = this.strom.vyhladaj(vrchol1);
                GeografickyObjekt geografickyObjekt = (GeografickyObjekt) duplicita.getData();
                if (parcela != null) {
                    parcela.addNehnutelnost(geografickyObjekt.getNehnutelnost());
                    geografickyObjekt.getNehnutelnost().addParcela(parcela);
                } else if (nehnutelnost != null) {
                    nehnutelnost.addParcela(geografickyObjekt.getParcela());
                    geografickyObjekt.getParcela().addNehnutelnost(nehnutelnost);
                }
            }
            if (vrchol2.isJeDuplicita()) {
                Nehnutelnost nehnutelnost = dataGeografickyObjekt2.getNehnutelnost();
                Parcela parcela = dataGeografickyObjekt2.getParcela();
                Vrchol<T> duplicita = this.strom.vyhladaj(vrchol2);
                GeografickyObjekt geografickyObjekt = (GeografickyObjekt) duplicita.getData();
                if (parcela != null) {
                    parcela.addNehnutelnost(geografickyObjekt.getNehnutelnost());
                    geografickyObjekt.getNehnutelnost().addParcela(parcela);
                } else if (nehnutelnost != null) {
                    nehnutelnost.addParcela(geografickyObjekt.getParcela());
                    geografickyObjekt.getParcela().addNehnutelnost(nehnutelnost);
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
