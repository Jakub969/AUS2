package triedy;

import rozhrania.IKluc;

public class GeografickyObjekt implements IKluc<GeografickyObjekt> {
    private final GPS GPSsuradnice;
    private final String uuid;
    Nehnutelnost nehnutelnost;
    Parcela parcela;

    public GeografickyObjekt(GPS GPSsuradnice, Nehnutelnost nehnutelnost, Parcela parcela) {
        this.GPSsuradnice = GPSsuradnice;
        this.uuid = java.util.UUID.randomUUID().toString();
        this.nehnutelnost = nehnutelnost;
        this.parcela = parcela;
    }

    public GPS getGPSsuradnice() {
        return GPSsuradnice;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public int porovnaj(GeografickyObjekt objekt, int poradieKluca) {
        if (objekt.nehnutelnost instanceof Nehnutelnost dataNehnutelnost) {
            if (poradieKluca == 0) {
                return Double.compare(this.nehnutelnost.getGPSsuradnice().getPoziciaDlzky(), dataNehnutelnost.getGPSsuradnice().getPoziciaDlzky());
            } else {
                return Double.compare(this.nehnutelnost.getGPSsuradnice().getPoziciaSirky(), dataNehnutelnost.getGPSsuradnice().getPoziciaSirky());
            }
        } else if (objekt.parcela instanceof Parcela dataParcela) {
            if (poradieKluca == 0) {
                return Double.compare(this.parcela.getGPSsuradnice().getPoziciaDlzky(), dataParcela.getGPSsuradnice().getPoziciaDlzky());
            } else {
                return Double.compare(this.parcela.getGPSsuradnice().getPoziciaSirky(), dataParcela.getGPSsuradnice().getPoziciaSirky());
            }
        } else {
            return -2;
        }
    }

    @Override
    public boolean zhodneUuid(GeografickyObjekt objekt) {
        return this.uuid.equals(objekt.getUuid());
    }
}
