package testy;

import rozhrania.IKluc;

public class GenerovaneData implements IKluc<GenerovaneData>, Comparable<GenerovaneData> {
    private final int atributA;
    private final int atributB;
    private String uuid;

    public GenerovaneData(int parAtributA, int parAtributB, String parUuid) {
        this.atributA = parAtributA;
        this.atributB = parAtributB;
        this.uuid = parUuid;
    }

    public int getAtributA() {
        return atributA;
    }

    public int getAtributB() {
        return atributB;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "GenerovaneData{" +
                "atributA=" + atributA +
                ", atributB='" + atributB + '\'' +
                '}';
    }

    @Override
    public int porovnaj(IKluc objekt, int poradieKluca) {
        if (objekt instanceof GenerovaneData generovaneDataObjekt) {
            if (poradieKluca == 0) {
                return Integer.compare(this.atributA, generovaneDataObjekt.getAtributA());
            } else {
                return Integer.compare(this.atributB, generovaneDataObjekt.getAtributB());
            }
        } else {
            return -2;
        }
    }

    @Override
    public int compareTo(GenerovaneData o) {
        if (this.uuid.equals(o.getUuid())) {
            return 0;
        } else {
            return -1;
        }
    }
}
