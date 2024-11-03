package triedy;

import rozhrania.IKluc;

public class GeografickyObjekt implements IKluc<GeografickyObjekt> {
    private final GPS GPSsuradnice;
    private final String uuid;
    private Nehnutelnost nehnutelnost;
    private Parcela parcela;

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

    public Nehnutelnost getNehnutelnost() {
        return nehnutelnost;
    }

    public Parcela getParcela() {
        return parcela;
    }

    @Override
    public int porovnaj(GeografickyObjekt objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof GeografickyObjekt dataGeografickyObjekt) {
            if (poradieKluca == 0) {
                if (this.GPSsuradnice.getPoziciaDlzky() - dataGeografickyObjekt.getGPSsuradnice().getPoziciaDlzky() <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataGeografickyObjekt.getGPSsuradnice().getPoziciaDlzky());
                }
            } else {
                if (this.GPSsuradnice.getPoziciaSirky() - dataGeografickyObjekt.getGPSsuradnice().getPoziciaSirky() <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataGeografickyObjekt.getGPSsuradnice().getPoziciaSirky());
                }
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
