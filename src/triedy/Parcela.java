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
    public int vyhladaj(Parcela objekt1, Parcela objekt2) {
        if (objekt1 instanceof Parcela GPSsuradnice1 && objekt2 instanceof Parcela GPSsuradnice2) {
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
