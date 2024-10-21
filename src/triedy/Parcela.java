package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Parcela implements IKluc<Parcela> {
    private final int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private Parcela referenciaNaRovnakuParceluSInymiGPS;
    private GPS GPSsuradnice;

    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, Parcela referenciaNaRovnakuParceluSInymiGPS, GPS GPSsuradnice) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
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

    public Parcela getReferenciaNaRovnakuParceluSInymiGPS() {
        return referenciaNaRovnakuParceluSInymiGPS;
    }

    public void setReferenciaNaRovnakuParceluSInymiGPS(Parcela referenciaNaRovnakuParceluSInymiGPS) {
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
    }

    @Override
    public int vyhladaj(Parcela objekt1, Parcela objekt2, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt1 instanceof Parcela GPSsuradnice1 && objekt2 instanceof Parcela GPSsuradnice2) {
            if (poradieKluca == 0) {
                double min = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
                double max = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
                if (min <= this.getGPSsuradnice().getPoziciaDlzky() && this.getGPSsuradnice().getPoziciaDlzky() <= max || (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - min) <= tolerancia) || (Math.abs(max - this.GPSsuradnice.getPoziciaDlzky()) <= tolerancia)) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                double min = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
                double max = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
                if (min <= this.getGPSsuradnice().getPoziciaSirky() && this.getGPSsuradnice().getPoziciaSirky() <= max || (Math.abs(this.GPSsuradnice.getPoziciaSirky() - min) <= tolerancia) || (Math.abs(max - this.GPSsuradnice.getPoziciaSirky()) <= tolerancia)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        } else {
            return -2;
        }
    }

    @Override
    public void pridaj(Parcela objekt) {

    }

    @Override
    public void edituj(Parcela objekt) {

    }

    @Override
    public void vyrad(Parcela objekt) {

    }

    @Override
    public int porovnaj(Parcela objekt, int poradieKluca) {
        if (objekt instanceof Parcela dataParcela) {
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
