package US.KdStrom;

import rozhrania.IKluc;

public class Vrchol<T> {
    private Vrchol<T> lavySyn;
    private Vrchol<T> pravySyn;
    private Vrchol<T> rodic;
    private final IKluc<T> kluc;
    private T data;

    public Vrchol(IKluc<T> kluc, T data) {
        this.kluc = kluc;
        this.data = data;
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

    public IKluc<T> getKluc() {
        return kluc;
    }

    public T getData() {
        return data;
    }
}
