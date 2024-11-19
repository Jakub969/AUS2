package triedy;

import rozhrania.IKluc;

public class GPS implements IKluc {

    private char dlzka;
    private double poziciaDlzky;
    private char sirka;
    private double poziciaSirky;

    /**
     * Konstruktor triedy GPS
     * @param sirka - smer zemepisnej sirky
     * @param poziciaSirky - pozicia zemepisnej sirky
     * @param dlzka - smer zemepisnej dlzky
     * @param poziciaDlzky - pozicia zemepisnej dlzky
     * */
    public GPS(char sirka, double poziciaSirky, char dlzka, double poziciaDlzky) {
        if (sirka != 'N' && sirka != 'S') {
            throw new IllegalArgumentException("Sirka musí byť 'N' alebo 'S'");
        }
        this.sirka = sirka;
        this.poziciaSirky = poziciaSirky;
        if (dlzka != 'E' && dlzka != 'W') {
            throw new IllegalArgumentException("Dlzka musí byť 'E' alebo 'W'");
        }
        this.dlzka = dlzka;
        this.poziciaDlzky = poziciaDlzky;
    }

    public char getSirka() {
        return sirka;
    }

    public double getPoziciaSirky() {
        if (sirka == 'N') {
            return poziciaSirky;
        } else {
            return -poziciaSirky;
        }
    }

    public char getDlzka() {
        return dlzka;
    }

    public double getPoziciaDlzky() {
        if (dlzka == 'E') {
            return poziciaDlzky;
        } else {
            return -poziciaDlzky;
        }
    }

    @Override
    public String toString() {
        return this.sirka + " " + this.poziciaSirky + "; " + this.dlzka + " " + this.poziciaDlzky;
    }

    @Override
    public int porovnaj(IKluc objekt, int poradieKluca) {
        if (objekt instanceof GPS GPSobjekt) {
            double tolerancia = 0.000001;
            if (poradieKluca == 0) {
                if (Math.abs(this.getPoziciaDlzky() - GPSobjekt.getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.getPoziciaDlzky(), GPSobjekt.getPoziciaDlzky());
                }
            } else {
                if (Math.abs(this.getPoziciaSirky() - GPSobjekt.getPoziciaSirky()) <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.getPoziciaSirky(), GPSobjekt.getPoziciaSirky());
                }
            }
        } else {
            return -2;
        }
    }
}
