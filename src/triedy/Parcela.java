package triedy;

import rozhrania.IKluc;

public class Parcela implements IKluc<Parcela> {
    private final int cisloParcely;
    private String popis;
    private Nehnutelnost[] zoznamNehnutelnosti;
    private GPS GPSsuradnice;

    public Parcela(int cisloParcely, String popis, Nehnutelnost[] zoznamNehnutelnosti, GPS GPSsuradnice) {
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

    public Nehnutelnost[] getZoznamNehnutelnosti() {
        return zoznamNehnutelnosti;
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    @Override
    public void vyhladaj(GPS GPSsuradnice) {

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
    public int porovnaj(IKluc<Parcela> kluc) {
        if (kluc instanceof Parcela klucParcela) {
            if (this.GPSsuradnice.getPoziciaDlzky() == klucParcela.GPSsuradnice.getPoziciaDlzky()) {
                return 0;
            } else if (this.GPSsuradnice.getPoziciaDlzky() < klucParcela.GPSsuradnice.getPoziciaDlzky()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return -2;
        }
    }
}
