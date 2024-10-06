package US.KdStrom;

public class Koren<T> {
    private final Vrchol<T> koren;

    public Koren(Vrchol<T> koren) {
        this.koren = koren;
    }

    public Vrchol<T> getKoren() {
        return koren;
    }
}
