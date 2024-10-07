package US.KdStrom;

import rozhrania.IKluc;

public class Vrchol<T> {
    private Vrchol<T> lavySyn;
    private Vrchol<T> pravySyn;
    private Vrchol<T> rodic;
    private final IKluc<T>[] kluce;
    private final int pocetKlucov;
    private final T data;

    public Vrchol(IKluc<T>[] kluce, T data) {
        this.kluce = kluce;
        this.data = data;
        this.pocetKlucov = kluce.length;
    }

    public Vrchol<T> getLavySyn() {
        return lavySyn;
    }

    public void setLavySyn(Vrchol<T> lavySyn) {
        this.lavySyn = lavySyn;
    }

    public Vrchol<T> getPravySyn() {
        return pravySyn;
    }

    public void setPravySyn(Vrchol<T> pravySyn) {
        this.pravySyn = pravySyn;
    }

    public Vrchol<T> getRodic() {
        return rodic;
    }

    public void setRodic(Vrchol<T> rodic) {
        this.rodic = rodic;
    }

    public IKluc<T>[] getKluce() {
        return kluce;
    }

    public T getData() {
        return data;
    }

    public int getPocetKlucov() {
        return pocetKlucov;
    }
}
