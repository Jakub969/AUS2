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
    public int vyhladaj(Nehnutelnost objekt1, Nehnutelnost objekt2) {
        if (objekt1 instanceof Nehnutelnost GPSsuradnice1 && objekt2 instanceof Nehnutelnost GPSsuradnice2) {
            double tolerancia = 0.000001;

            // Kontrola súradníc dĺžky
            double minDlzka = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
            double maxDlzka = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaDlzky(), GPSsuradnice2.getGPSsuradnice().getPoziciaDlzky());
            boolean vRozsahuDlzka = (minDlzka < this.getGPSsuradnice().getPoziciaDlzky() && this.getGPSsuradnice().getPoziciaDlzky() < maxDlzka) ||
                    (Math.abs(this.getGPSsuradnice().getPoziciaDlzky() - minDlzka) <= tolerancia) ||
                    (Math.abs(maxDlzka - this.getGPSsuradnice().getPoziciaDlzky()) <= tolerancia);

            // Kontrola súradníc šírky
            double minSirka = Math.min(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
            double maxSirka = Math.max(GPSsuradnice1.getGPSsuradnice().getPoziciaSirky(), GPSsuradnice2.getGPSsuradnice().getPoziciaSirky());
            boolean vRozsahuSirka = (minSirka < this.getGPSsuradnice().getPoziciaSirky() && this.getGPSsuradnice().getPoziciaSirky() < maxSirka) ||
                    (Math.abs(this.getGPSsuradnice().getPoziciaSirky() - minSirka) <= tolerancia) ||
                    (Math.abs(maxSirka - this.getGPSsuradnice().getPoziciaSirky()) <= tolerancia);

            // Ak sú obe súradnice v rozsahu, vrchol patrí do obdĺžnika
            if (vRozsahuDlzka && vRozsahuSirka) {
                return 0;
            } else {
                return -1;
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
