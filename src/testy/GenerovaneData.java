package testy;

import rozhrania.IKluc;

public class GenerovaneData implements IKluc<GenerovaneData> {
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
    public int porovnaj(GenerovaneData objekt, int poradieKluca) {
        if (poradieKluca == 0) {
            return Integer.compare(this.atributA, objekt.getAtributA());
        } else {
            return Integer.compare(this.atributB, objekt.getAtributB());
        }
    }

    @Override
    public boolean zhodneUuid(GenerovaneData objekt) {
        return this.uuid.equals(objekt.getUuid());
    }
}
