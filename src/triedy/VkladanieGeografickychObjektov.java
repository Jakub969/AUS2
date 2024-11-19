package triedy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import rozhrania.IZhoda;

public class VkladanieGeografickychObjektov<T extends IZhoda<T>> {
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
