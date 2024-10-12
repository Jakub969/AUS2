package US.KdStrom;

public class Vrchol<T> {
    private Vrchol<T> lavySyn;
    private Vrchol<T> pravySyn;
    private Vrchol<T> rodic;
    private final T data;

    public Vrchol(T data) {
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

    public T getData() {
        return data;
    }
}
