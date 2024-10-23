package US.KdStrom;

import java.util.ArrayList;

public class Vrchol<T> {
    private Vrchol<T> lavySyn;
    private Vrchol<T> pravySyn;
    private Vrchol<T> rodic;
    private final T data;
    private ArrayList<Vrchol<T>> duplicity;

    public Vrchol(T data) {
        this.data = data;
        this.lavySyn = null;
        this.pravySyn = null;
        this.rodic = null;
        this.duplicity = new ArrayList<>();
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

    public ArrayList<Vrchol<T>> getDuplicity() {
        return duplicity;
    }

    public void addDuplicitu(Vrchol<T> duplicita) {
        this.duplicity.add(duplicita);
    }

    public T getData() {
        return data;
    }
}
