package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Parcela implements IKluc<Parcela> {
    private final int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private Parcela referenciaNaRovnakuParceluSInymiGPS;
    private GPS GPSsuradnice;
    private final String uuid;

    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, Parcela referenciaNaRovnakuParceluSInymiGPS, GPS GPSsuradnice) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
        this.GPSsuradnice = GPSsuradnice;
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

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    public Parcela getReferenciaNaRovnakuParceluSInymiGPS() {
        return referenciaNaRovnakuParceluSInymiGPS;
    }

    public void setReferenciaNaRovnakuParceluSInymiGPS(Parcela referenciaNaRovnakuParceluSInymiGPS) {
        this.referenciaNaRovnakuParceluSInymiGPS = referenciaNaRovnakuParceluSInymiGPS;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean zhodneUuid(Parcela objekt) {
        return this.uuid.equals(objekt.getUuid());
    }

    @Override
    public int porovnaj(Parcela objekt, int poradieKluca) {
        if (objekt instanceof Parcela dataParcela) {
            if (poradieKluca == 0) {
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataParcela.getGPSsuradnice().getPoziciaDlzky());
            } else {
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataParcela.getGPSsuradnice().getPoziciaSirky());
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
