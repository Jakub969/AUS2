package triedy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;

public class OperacieGeografickychObjektov<T extends Comparable<T>> {
    private final KdStrom<T> strom;

    /**
     * Konstruktor triedy VkladanieGeografickychObjektov
     * @param kdStrom - instanciu triedy KdStrom
     * */
    public OperacieGeografickychObjektov(KdStrom<T> kdStrom) {
        this.strom = kdStrom;
    }

    /**
     * Metoda vkladania geografickych objektov
     * @param objekt - objekt, ktory sa ma vlozit
     * */
    public void metodaVkladania(T objekt) {
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
            vlozObjekt(objekt, dataNehnutelnost.getGPSsuradnice1(), dataNehnutelnost.getGPSsuradnice2());
        } else if (objekt instanceof Parcela dataParcela) {
            vlozObjekt(objekt, dataParcela.getGPSsuradnice1(), dataParcela.getGPSsuradnice2());
        } else if (objekt instanceof GeografickyObjekt dataGeografickyObjekt) {
            vlozObjekt(objekt, dataGeografickyObjekt.getGPSsuradnice1(), dataGeografickyObjekt.getGPSsuradnice2());
        }
    }

    public void metodaMazania(T objekt) {
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
            vymazObjekt(objekt, dataNehnutelnost.getGPSsuradnice1(), dataNehnutelnost.getGPSsuradnice2());
        } else if (objekt instanceof Parcela dataParcela) {
            vymazObjekt(objekt, dataParcela.getGPSsuradnice1(), dataParcela.getGPSsuradnice2());
        } else if (objekt instanceof GeografickyObjekt dataGeografickyObjekt) {
            vymazObjekt(objekt, dataGeografickyObjekt.getGPSsuradnice1(), dataGeografickyObjekt.getGPSsuradnice2());
        }
    }

    private void vymazObjekt(T objekt, GPS gpSsuradnice1, GPS gpSsuradnice2) {
        GPS gps1 = gpSsuradnice1;
        GPS gps2 = gpSsuradnice2;
        Vrchol<T> vrchol1 = new Vrchol<>(gps1, objekt);
        this.strom.vyrad(vrchol1);
        Vrchol<T> vrchol2 = new Vrchol<>(gps2, objekt);
        this.strom.vyrad(vrchol2);
    }

    private void vlozObjekt(T objekt, GPS gpSsuradnice1, GPS gpSsuradnice2) {
        GPS gps1 = gpSsuradnice1;
        GPS gps2 = gpSsuradnice2;
        Vrchol<T> vrchol1 = new Vrchol<>(gps1, objekt);
        this.strom.vloz(vrchol1);
        Vrchol<T> vrchol2 = new Vrchol<>(gps2, objekt);
        this.strom.vloz(vrchol2);
        if (objekt instanceof GeografickyObjekt dataGeografickyObjekt) {
            naplnZoznam(dataGeografickyObjekt, vrchol1);
            naplnZoznam(dataGeografickyObjekt, vrchol2);
        }
    }

    private void naplnZoznam(GeografickyObjekt dataGeografickyObjekt, Vrchol<T> vrchol) {
        if (vrchol.isJeDuplicita()) {
            Nehnutelnost nehnutelnost = dataGeografickyObjekt.getNehnutelnost();
            Parcela parcela = dataGeografickyObjekt.getParcela();
            Vrchol<T> duplicita = this.strom.vyhladaj(vrchol);
            GeografickyObjekt geografickyObjekt = (GeografickyObjekt) duplicita.getData();
            if (parcela != null) {
                parcela.addNehnutelnost(geografickyObjekt.getNehnutelnost());
                geografickyObjekt.getNehnutelnost().addParcela(parcela);
            } else if (nehnutelnost != null) {
                nehnutelnost.addParcela(geografickyObjekt.getParcela());
                geografickyObjekt.getParcela().addNehnutelnost(nehnutelnost);
            }
        }
    }
}
