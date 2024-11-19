package triedy;

import rozhrania.IKluc;
import rozhrania.IZhoda;

import java.util.ArrayList;

public class Parcela implements IZhoda<Parcela> {
    private int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private final GPS GPSsuradnice1;
    private final GPS GPSsuradnice2;
    private final String uuid;

    /**
     * Konstruktor triedy Parcela
     * @param cisloParcely cislo parcely
     * @param popis popis parcely
     * @param zoznamNehnutelnosti zoznam nehnutelnosti na parcele
     * @param GPSsuradnice1 GPS suradnice parcely
     */
    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, GPS GPSsuradnice1, GPS GPSsuradnice2) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        if (zoznamNehnutelnosti == null) {
            this.zoznamNehnutelnosti = new ArrayList<>();
        }
        this.GPSsuradnice1 = GPSsuradnice1;
        this.GPSsuradnice2 = GPSsuradnice2;
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

    public GPS getGPSsuradnice1() {
        return GPSsuradnice1;
    }

    public GPS getGPSsuradnice2() {
        return GPSsuradnice2;
    }

    public void setCisloParcely(int cisloParcely) {
        this.cisloParcely = cisloParcely;
    }

    public void setPopis(String popis) {
        this.popis = popis;
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

    @Override
    public String toString() {
        return "Parcela{" +
                "cisloParcely=" + cisloParcely +
                ", popis='" + popis + '\'' +
                ", GPSsuradnice1="  + GPSsuradnice1.getDlzka() + ": " + GPSsuradnice1.getPoziciaDlzky() + " | " + GPSsuradnice1.getSirka() + ": " + GPSsuradnice1.getPoziciaSirky() +
                ", GPSsuradnice2=" + GPSsuradnice2.getDlzka() + ": " + GPSsuradnice2.getPoziciaDlzky() + " | " + GPSsuradnice2.getSirka() + ": " + GPSsuradnice2.getPoziciaSirky() +
                '}';
    }
}
