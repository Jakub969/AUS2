package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private int supisneCislo;
    private String popis;
    private ArrayList<Parcela> zoznamParciel;
    private Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS;
    private final GPS GPSsuradnice;
    private final String uuid;

    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS, GPS GPSsuradnice) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParciel = zoznamParcel;
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

    public ArrayList<Parcela> getZoznamParciel() {
        return zoznamParciel;
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    public Nehnutelnost getReferenciaNaRovnakuNehnutelnostSInymiGPS() {
        return referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    public void setSupisneCislo(int supisneCislo) {
        this.supisneCislo = supisneCislo;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setReferenciaNaRovnakuNehnutelnostSInymiGPS(Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS) {
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    public void addParcela(Parcela parcela) {
        this.zoznamParciel.add(parcela);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public int porovnaj(Nehnutelnost objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
            if (poradieKluca == 0) {
                if (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - dataNehnutelnost.getGPSsuradnice().getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataNehnutelnost.getGPSsuradnice().getPoziciaDlzky());
            } else {
                if (Math.abs(this.GPSsuradnice.getPoziciaSirky() - dataNehnutelnost.getGPSsuradnice().getPoziciaSirky()) <= tolerancia) {
                    return 0;
                }
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
