package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private final int supisneCislo;
    private final String popis;
    private final ArrayList<Parcela> zoznamParcel;
    private final GPS GPSsuradnice;

    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, GPS GPSsuradnice) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParcel = zoznamParcel;
        this.GPSsuradnice = GPSsuradnice;
    }

    public int getSupisneCislo() {
        return supisneCislo;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Parcela> getZoznamParcel() {
        return zoznamParcel;
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    @Override
    public boolean vyhladaj(GPS GPSsuradnice1, GPS GPSsuradnice2) {
        return GPSsuradnice1.getPoziciaSirky() <= this.GPSsuradnice.getPoziciaSirky() && this.GPSsuradnice.getPoziciaSirky() <= GPSsuradnice2.getPoziciaSirky() &&
                GPSsuradnice1.getPoziciaDlzky() <= this.GPSsuradnice.getPoziciaDlzky() && this.GPSsuradnice.getPoziciaDlzky() <= GPSsuradnice2.getPoziciaDlzky();
    }

    @Override
    public void pridaj(Nehnutelnost objekt) {

    }

    @Override
    public void edituj(GPS GPSsuradnice) {

    }

    @Override
    public void vyrad(GPS GPSsuradnice) {

    }

    @Override
    public int porovnaj(Nehnutelnost data, int poradieKluca) {
        if (data instanceof Nehnutelnost dataNehnutelnost) {
            if (poradieKluca == 0) {
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataNehnutelnost.GPSsuradnice.getPoziciaDlzky());
            } else {
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataNehnutelnost.GPSsuradnice.getPoziciaSirky());
            }
        } else {
            return -2;
        }
    }

    @Override
    public String toString() {
        return "Nehnutelnost{" +
                "supisneCislo=" + supisneCislo +
                ", popis='" + popis + '\'' +
                ", GPSsuradnice=" + GPSsuradnice.getDlzka() + ": " + GPSsuradnice.getPoziciaDlzky() +  " | " + GPSsuradnice.getSirka() + ": " + GPSsuradnice.getPoziciaSirky() +
                '}';
    }
}
