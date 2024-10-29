package testy;

import rozhrania.IKluc;

public class GenerovaneData implements IKluc<GenerovaneData> {
    private final double atributA;
    private final String atributB;
    private final int atributC;
    private final double atributD;
    private String uuid;

    public GenerovaneData(double parAtributA, String parAtributB, int parAtributC, double parAtributD) {
        this.atributA = parAtributA;
        this.atributB = parAtributB;
        this.atributC = parAtributC;
        this.atributD = parAtributD;
        this.uuid = java.util.UUID.randomUUID().toString();
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

    public String getUuid() {
        return uuid;
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

    @Override
    public boolean zhodneUuid(GenerovaneData objekt) {
        return this.uuid.equals(objekt.getUuid());
    }
}
