package triedy;

import rozhrania.IKluc;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private int supisneCislo;
    private String popis;
    private Parcela[] zoznamParcel;
    private GPS GPSsuradnice;

    public Nehnutelnost(int supisneCislo, String popis, Parcela[] zoznamParcel, GPS GPSsuradnice) {
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

    public Parcela[] getZoznamParcel() {
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
    public int porovnaj(IKluc<Nehnutelnost> kluc) {
        if (kluc instanceof Nehnutelnost klucNehnutelnost) {
            if (this.supisneCislo == klucNehnutelnost.supisneCislo) {
                return 0;
            } else if (this.supisneCislo < klucNehnutelnost.supisneCislo) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return -2;
        }
    }
}
