package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private final int supisneCislo;
    private final String popis;
    private final ArrayList<Parcela> zoznamParcel;
    private Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS;
    private final GPS GPSsuradnice;
    private final String uuid;

    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS, GPS GPSsuradnice) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParcel = zoznamParcel;
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
        this.GPSsuradnice = GPSsuradnice;
        this.uuid = java.util.UUID.randomUUID().toString();
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

    public String getUuid() {
        return uuid;
    }

    @Override
    public int porovnaj(Nehnutelnost objekt, int poradieKluca) {
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
            if (poradieKluca == 0) {
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataNehnutelnost.getGPSsuradnice().getPoziciaDlzky());
            } else {
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataNehnutelnost.getGPSsuradnice().getPoziciaSirky());
            }
        } else {
            return -2;
        }
    }

    @Override
    public boolean zhodneUuid(Nehnutelnost objekt) {
        return this.uuid.equals(objekt.getUuid());
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
