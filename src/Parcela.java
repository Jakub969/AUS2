public class Parcela implements IOperacie<Parcela> {
    int cisloParcely;
    String popis;
    Nehnutelnost[] zoznamNehnutelnosti;
    GPS GPSsuradnice;

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
    public void edituj(Parcela objekt) {

    }

    @Override
    public void vyrad(Parcela objekt) {

    }
}
