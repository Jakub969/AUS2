package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private int supisneCislo;
    private String popis;
    private ArrayList<Parcela> zoznamParcel;
    private GPS GPSsuradnice;

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
    public void vyhladaj(GPS GPSsuradnice) {

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
