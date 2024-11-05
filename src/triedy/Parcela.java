package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Parcela implements IKluc<Parcela> {
    private int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> zoznamNehnutelnosti;
    private Parcela referenciaNaRovnakuParceluSInymiGPS;
    private GPS GPSsuradnice;
    private final String uuid;

    public Parcela(int cisloParcely, String popis, ArrayList<Nehnutelnost> zoznamNehnutelnosti, Parcela referenciaNaRovnakuParceluSInymiGPS, GPS GPSsuradnice) {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.zoznamNehnutelnosti = zoznamNehnutelnosti;
        if (zoznamNehnutelnosti == null) {
            this.zoznamNehnutelnosti = new ArrayList<>();
        }
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

    public void setCisloParcely(int cisloParcely) {
        this.cisloParcely = cisloParcely;
    }

    public void setPopis(String popis) {
        this.popis = popis;
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

    public void addNehnutelnost(Nehnutelnost nehnutelnost) {
        this.zoznamNehnutelnosti.add(nehnutelnost);
    }

    @Override
    public boolean zhodneUuid(Parcela objekt) {
        return this.uuid.equals(objekt.getUuid());
    }

    @Override
    public int porovnaj(Parcela objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof Parcela dataParcela) {
            if (poradieKluca == 0) {
                if (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - dataParcela.getGPSsuradnice().getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataParcela.getGPSsuradnice().getPoziciaDlzky());
            } else {
                if (Math.abs(this.GPSsuradnice.getPoziciaSirky() - dataParcela.getGPSsuradnice().getPoziciaSirky()) <= tolerancia) {
                    return 0;
                }
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
