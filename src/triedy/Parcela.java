package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Parcela implements IKluc<Parcela> {
    private final int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private GPS GPSsuradnice;

    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, GPS GPSsuradnice) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        this.GPSsuradnice = GPSsuradnice;
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

    @Override
    public boolean vyhladaj(GPS GPSsuradnice1, GPS GPSsuradnice2) {
        return GPSsuradnice1.getPoziciaSirky() <= this.GPSsuradnice.getPoziciaSirky() && this.GPSsuradnice.getPoziciaSirky() <= GPSsuradnice2.getPoziciaSirky() &&
                GPSsuradnice1.getPoziciaDlzky() <= this.GPSsuradnice.getPoziciaDlzky() && this.GPSsuradnice.getPoziciaDlzky() <= GPSsuradnice2.getPoziciaDlzky();

    }

    @Override
    public void pridaj(Parcela objekt) {

    }

    @Override
    public void edituj(GPS GPSsuradnice) {

    }

    @Override
    public void vyrad(GPS GPSsuradnice) {

    }

    @Override
    public int porovnaj(Parcela data, int poradieKluca) {
        if (data instanceof Parcela dataParcela) {
            if (poradieKluca == 0) {
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataParcela.GPSsuradnice.getPoziciaDlzky());
            } else {
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataParcela.GPSsuradnice.getPoziciaSirky());
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
