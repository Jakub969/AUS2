package triedy;

import rozhrania.IZhoda;

import java.util.ArrayList;

public class Nehnutelnost implements IZhoda<Nehnutelnost> {
    private int supisneCislo;
    private String popis;
    private ArrayList<Parcela> zoznamParciel;
    private final GPS GPSsuradnice1;
    private final GPS GPSsuradnice2;
    private final String uuid;

    /**
     * Konstruktor triedy Nehnutelnost
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param zoznamParcel zoznam parciel na nehnutelnosti
     * @param GPSsuradnice1 GPS suradnice nehnutelnosti
     * @param GPSsuradnice2 GPS suradnice nehnutelnosti
     */
    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, GPS GPSsuradnice1, GPS GPSsuradnice2) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParciel = zoznamParcel;
        if (zoznamParcel == null) {
            this.zoznamParciel = new ArrayList<>();
        }
        this.GPSsuradnice1 = GPSsuradnice1;
        this.GPSsuradnice2 = GPSsuradnice2;
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public int getSupisneCislo() {
        return supisneCislo;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Parcela> getZoznamParciel() {
        return zoznamParciel;
    }

    public GPS getGPSsuradnice1() {
        return GPSsuradnice1;
    }

    public GPS getGPSsuradnice2() {
        return GPSsuradnice2;
    }

    public void setSupisneCislo(int supisneCislo) {
        this.supisneCislo = supisneCislo;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void addParcela(Parcela parcela) {
        this.zoznamParciel.add(parcela);
    }

    public void removeParcela(Parcela parcela) {
        this.zoznamParciel.remove(parcela);
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * Metoda na zistenie, ci sa dve nehnutelnosti zhoduju podla UUID
     * @param objekt nehnutelnost, s ktorou sa ma porovnat
     * @return boolean - true, ak sa zhoduju, inak false
     */
    @Override
    public boolean zhodneUuid(Nehnutelnost objekt) {
        return this.uuid.equals(objekt.getUuid());
    }

    @Override
    public String toString() {
        return "Nehnutelnost{" +
                "supisneCislo=" + supisneCislo +
                ", popis='" + popis + '\'' +
                ", GPSsuradnice1=" + GPSsuradnice1.getDlzka() + ": " + GPSsuradnice1.getPoziciaDlzky() +  " | " + GPSsuradnice1.getSirka() + ": " + GPSsuradnice1.getPoziciaSirky() +
                ", GPSsuradnice2=" + GPSsuradnice2.getDlzka() + ": " + GPSsuradnice2.getPoziciaDlzky() +  " | " + GPSsuradnice2.getSirka() + ": " + GPSsuradnice2.getPoziciaSirky() +
                '}';
    }
}
