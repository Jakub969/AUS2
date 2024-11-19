package triedy;

import rozhrania.IZhoda;

public class GeografickyObjekt implements IZhoda<GeografickyObjekt> {
    private final GPS GPSsuradnice1;
    private final GPS GPSsuradnice2;
    private final String uuid;
    private Nehnutelnost nehnutelnost;
    private Parcela parcela;

    /**
     * Konstruktor triedy GeografickyObjekt
     * @param GPSsuradnice1 GPS suradnice geografickeho objektu
     * @param GPSsuradnice2 GPS suradnice geografickeho objektu
     * @param nehnutelnost nehnutelnost geografickeho objektu
     * @param parcela parcela geografickeho objektu
     * */
    public GeografickyObjekt(GPS GPSsuradnice1, GPS GPSsuradnice2, Nehnutelnost nehnutelnost, Parcela parcela) {
        this.GPSsuradnice1 = GPSsuradnice1;
        this.GPSsuradnice2 = GPSsuradnice2;
        this.nehnutelnost = nehnutelnost;
        this.parcela = parcela;
        if (nehnutelnost != null) {
            this.uuid = nehnutelnost.getUuid();
        } else {
            this.uuid = parcela.getUuid();
        }
    }

    public GPS getGPSsuradnice1() {
        return GPSsuradnice1;
    }

    public GPS getGPSsuradnice2() {
        return GPSsuradnice2;
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
     * Metoda na zistenie, ci sa dva geograficke objekty zhoduju podla UUID
     * @param objekt - geograficky objekt, s ktorym sa ma porovnat
     * @return boolean - true, ak sa zhoduju, inak false
     * */
    @Override
    public boolean zhodneUuid(GeografickyObjekt objekt) {
        return this.uuid.equals(objekt.getUuid());
    }
}
