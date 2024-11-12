package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Parcela implements IKluc<Parcela> {
    private int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private Parcela referenciaNaRovnakuParceluSInymiGPS;
    private GPS GPSsuradnice;
    private final String uuid;

    /**
     * Konstruktor triedy Parcela
     * @param cisloParcely cislo parcely
     * @param popis popis parcely
     * @param zoznamNehnutelnosti zoznam nehnutelnosti na parcele
     * @param referenciaNaRovnakuParceluSInymiGPS referencia na rovnaku parcelu s inymi GPS
     * @param GPSsuradnice GPS suradnice parcely
     */
    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, Parcela referenciaNaRovnakuParceluSInymiGPS, GPS GPSsuradnice) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        if (zoznamNehnutelnosti == null) {
            this.zoznamNehnutelnosti = new ArrayList<>();
        }
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
        this.GPSsuradnice = GPSsuradnice;
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public int getCisloParcely() {
        return cisloParcely;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Nehnutelnost> getZoznamNehnutelnosti() {
        return zoznamNehnutelnosti;
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    public void setCisloParcely(int cisloParcely) {
        this.cisloParcely = cisloParcely;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Parcela getReferenciaNaRovnakuParceluSInymiGPS() {
        return referenciaNaRovnakuParceluSInymiGPS;
    }

    public void setReferenciaNaRovnakuParceluSInymiGPS(Parcela referenciaNaRovnakuParceluSInymiGPS) {
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
    }

    public String getUuid() {
        return uuid;
    }

    public void addNehnutelnost(Nehnutelnost nehnutelnost) {
        this.zoznamNehnutelnosti.add(nehnutelnost);
    }

    public void removeNehnutelnost(Nehnutelnost nehnutelnost) {
        this.zoznamNehnutelnosti.remove(nehnutelnost);
    }
    /**
     * Metoda na zistenie, ci sa dve parcely zhoduju podla UUID
     * @param objekt parcela, s ktorou sa ma porovnat
     * @return boolean - true, ak sa zhoduju, inak false
     * */
    @Override
    public boolean zhodneUuid(Parcela objekt) {
        return this.uuid.equals(objekt.getUuid());
    }

    /**
     * Metoda na porovnanie dvoch parcel podla GPS suradnic
     * @param objekt parcela, s ktorou sa ma porovnat
     * @param poradieKluca poradie kluca, podla ktoreho sa ma porovnat
     * @return int - 0, ak sa zhoduju, inak -1 alebo 1
     * */
    @Override
    public int porovnaj(Parcela objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof Parcela dataParcela) {
            if (poradieKluca == 0) {
                if (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - dataParcela.getGPSsuradnice().getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataParcela.getGPSsuradnice().getPoziciaDlzky());
            } else {
                if (Math.abs(this.GPSsuradnice.getPoziciaSirky() - dataParcela.getGPSsuradnice().getPoziciaSirky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataParcela.getGPSsuradnice().getPoziciaSirky());
            }
        } else {
            return -2;
        }
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "cisloParcely=" + cisloParcely +
                ", popis='" + popis + '\'' +
                ", GPSsuradnice="  + GPSsuradnice.getDlzka() + ": " + GPSsuradnice.getPoziciaDlzky() + " | " + GPSsuradnice.getSirka() + ": " + GPSsuradnice.getPoziciaSirky() +
                '}';
    }
}
