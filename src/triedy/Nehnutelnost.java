package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private final int supisneCislo;
    private final String popis;
    private final ArrayList<Parcela> zoznamParcel;
    private Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS;
    private final GPS GPSsuradnice;

    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS, GPS GPSsuradnice) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParcel = zoznamParcel;
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
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

    public Nehnutelnost getReferenciaNaRovnakuNehnutelnostSInymiGPS() {
        return referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    public void setReferenciaNaRovnakuNehnutelnostSInymiGPS(Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS) {
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    @Override
    public int vyhladaj(Nehnutelnost objekt1, Nehnutelnost objekt2, int poradieKluca) {
        if (objekt1 instanceof Nehnutelnost GPSsuradnice1 && objekt2 instanceof Nehnutelnost GPSsuradnice2) {
            double tolerancia = 0.000001;
            if (poradieKluca == 0) {
                double min = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
                double max = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
                if ((min < this.getGPSsuradnice().getPoziciaDlzky() && this.getGPSsuradnice().getPoziciaDlzky() < max) || (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - min) <= tolerancia) || (Math.abs(max - this.GPSsuradnice.getPoziciaDlzky()) <= tolerancia)) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                double min = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
                double max = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
                if (min < this.getGPSsuradnice().getPoziciaSirky() && this.getGPSsuradnice().getPoziciaSirky() < max  || (Math.abs(this.GPSsuradnice.getPoziciaSirky() - min) <= tolerancia) || (Math.abs(max - this.GPSsuradnice.getPoziciaSirky()) <= tolerancia)) {
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
    public void pridaj(Nehnutelnost objekt) {

    }

    @Override
    public void edituj(Nehnutelnost objekt) {

    }

    @Override
    public void vyrad(Nehnutelnost objekt) {

    }

    @Override
    public int porovnaj(Nehnutelnost objekt, int poradieKluca) {
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
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
