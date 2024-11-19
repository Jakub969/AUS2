package US.KdStrom;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Vrchol<T extends Comparable<T>> {
    private Vrchol<T> lavySyn;
    private Vrchol<T> pravySyn;
    private Vrchol<T> rodic;
    private final T data;
    private final IKluc<T> kluce;
    private ArrayList<Vrchol<T>> duplicity;
    private boolean jeDuplicita;

    /**
     * Konstruktor triedy Vrchol
     * @param data - data, ktore sa maju ulozit do vrcholu
     * */
    public Vrchol(IKluc<T> kluce,T data) {
        this.kluce = kluce;
        this.data = data;
        this.lavySyn = null;
        this.pravySyn = null;
        this.rodic = null;
        this.duplicity = new ArrayList<>();
        this.jeDuplicita = false;
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

    public void setDuplicity(ArrayList<Vrchol<T>> duplicity) {
        this.duplicity = duplicity;
    }

    public boolean isJeDuplicita() {
        return jeDuplicita;
    }

    public void setJeDuplicita(boolean jeDuplicita) {
        this.jeDuplicita = jeDuplicita;
    }

    public IKluc<T> getKluce() {
        return kluce;
    }
}
