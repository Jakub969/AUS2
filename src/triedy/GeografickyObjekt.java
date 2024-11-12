package triedy;

import rozhrania.IKluc;

public class GeografickyObjekt implements IKluc<GeografickyObjekt> {
    private final GPS GPSsuradnice;
    private final String uuid;
    private Nehnutelnost nehnutelnost;
    private Parcela parcela;

    /**
     * Konstruktor triedy GeografickyObjekt
     * @param GPSsuradnice GPS suradnice geografickeho objektu
     * @param nehnutelnost nehnutelnost geografickeho objektu
     * @param parcela parcela geografickeho objektu
     * */
    public GeografickyObjekt(GPS GPSsuradnice, Nehnutelnost nehnutelnost, Parcela parcela) {
        this.GPSsuradnice = GPSsuradnice;
        this.nehnutelnost = nehnutelnost;
        this.parcela = parcela;
        if (nehnutelnost != null) {
            this.uuid = nehnutelnost.getUuid();
        } else {
            this.uuid = parcela.getUuid();
        }
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    public String getUuid() {
        return uuid;
    }

    public Nehnutelnost getNehnutelnost() {
        return nehnutelnost;
    }

    public Parcela getParcela() {
        return parcela;
    }

    /**
     * Metoda porovnania geografickych objektov
     * @param objekt - geograficky objekt, s ktorym sa porovnava
     * @param poradieKluca - poradie kluca, podla ktoreho sa porovnava
     * @return int - vysledok porovnania 0 ak su rovnake, -1 ak je mensie, 1 ak je vacsie
     * */
    @Override
    public int porovnaj(GeografickyObjekt objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof GeografickyObjekt dataGeografickyObjekt) {
            if (poradieKluca == 0) {
                if (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - dataGeografickyObjekt.getGPSsuradnice().getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataGeografickyObjekt.getGPSsuradnice().getPoziciaDlzky());
                }
            } else {
                if (Math.abs(this.GPSsuradnice.getPoziciaSirky() - dataGeografickyObjekt.getGPSsuradnice().getPoziciaSirky()) <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataGeografickyObjekt.getGPSsuradnice().getPoziciaSirky());
                }
            }
        } else {
            return -2;
        }
    }

    /**
     * Metoda na zistenie, ci sa dva geograficke objekty zhoduju podla UUID
     * @param objekt - geograficky objekt, s ktorym sa ma porovnat
     * @return boolean - true, ak sa zhoduju, inak false
     * */
    @Override
    public boolean zhodneUuid(GeografickyObjekt objekt) {
        return this.uuid.equals(objekt.getUuid());
    }
}
