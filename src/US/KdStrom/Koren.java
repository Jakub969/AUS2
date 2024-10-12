package US.KdStrom;

public class Koren<T> extends Vrchol<T> {
    private final Vrchol<T> koren;

    public Koren(Vrchol<T> koren) {
        super(koren.getData());
        this.koren = koren;
    }

    public Vrchol<T> getKoren() {
        return koren;
    }

    @Override
    public void setRodic(Vrchol<T> rodic) {
        throw new UnsupportedOperationException("Koren nema rodica");
    }
}
