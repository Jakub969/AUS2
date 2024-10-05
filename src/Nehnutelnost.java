public class Nehnutelnost implements IOperacie<Nehnutelnost> {
    int supisneCislo;
    String popis;
    Parcela[] zoznamParcel;
    GPS GPSsuradnice;

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
    public void edituj(Nehnutelnost objekt) {

    }

    @Override
    public void vyrad(Nehnutelnost objekt) {

    }
}
