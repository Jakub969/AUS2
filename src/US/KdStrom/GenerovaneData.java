package US.KdStrom;

import rozhrania.IKluc;

public class GenerovaneData implements IKluc<GenerovaneData> {
    private final double atributA;
    private final String atributB;
    private final int atributC;
    private final double atributD;

    public GenerovaneData(double parAtributA, String parAtributB, int parAtributC, double parAtributD) {
        this.atributA = parAtributA;
        this.atributB = parAtributB;
        this.atributC = parAtributC;
        this.atributD = parAtributD;
    }

    public double getAtributA() {
        return atributA;
    }

    public String getAtributB() {
        return atributB;
    }

    public int getAtributC() {
        return atributC;
    }

    public double getAtributD() {
        return atributD;
    }

    @Override
    public String toString() {
        return "GenerovaneData{" +
                "atributA=" + atributA +
                ", atributB='" + atributB + '\'' +
                ", atributC=" + atributC +
                ", atributD=" + atributD +
                '}';
    }

    @Override
    public int vyhladaj(GenerovaneData objekt1, GenerovaneData objekt2) {
        double tolerancia = 0.000001;
        return (Math.abs(this.atributA - objekt1.atributA) <= tolerancia) && this.atributB.equals(objekt1.getAtributB()) &&
                this.atributC == objekt1.getAtributC() && (Math.abs(this.atributA - objekt2.atributA) <= tolerancia) ? 0 : -1;
    }

    @Override
    public void pridaj(GenerovaneData objekt) {

    }

    @Override
    public void edituj(GenerovaneData objekt) {

    }

    @Override
    public boolean vyrad(GenerovaneData objekt) {
        return false;
    }

    /*
    * Vetvenie na úrovni 1:
    najskôr sa porovná atribút A - double, ak sa rovnajú porovná sa atribút B - string

    Vetvenie na úrovni 2:
    atribút C - integer

    Vetvenie na úrovni 3:
    atribút D - double

    Vetvenie na úrovni 4:
    najskôr sa porovná sa atribút B - string, ak sa rovnajú porovná sa atribút C - integer
    * */
    @Override
    public int porovnaj(GenerovaneData objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        switch (poradieKluca) {
            case 0:
                if (Math.abs(this.atributA - objekt.getAtributA()) <= tolerancia) {
                    return Integer.signum(this.atributB.compareTo(objekt.getAtributB()));
                } else {
                    return Double.compare(this.atributA, objekt.getAtributA());
                }
            case 1:
                return Integer.compare(this.atributC, objekt.getAtributC());
            case 2:
                if (Math.abs(this.atributD - objekt.getAtributD()) <= tolerancia) {
                    return 0;
                } else {
                    return Double.compare(this.atributD, objekt.getAtributD());
                }
            case 3:
                if (this.atributB.equals(objekt.getAtributB())) {
                    return Integer.compare(this.atributC, objekt.getAtributC());
                } else {
                    return Integer.signum(this.atributB.compareTo(objekt.getAtributB()));
                }
            default:
                return -1;
        }
    }
}
