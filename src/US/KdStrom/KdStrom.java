package US.KdStrom;

public class KdStrom<T> {
    private Koren<T> koren;

    public KdStrom(Koren<T> koren) {
        this.koren = koren;
    }

    public Koren<T> getKoren() {
        return koren;
    }

    public void vloz(Vrchol<T> vrchol) {
        Vrchol<T> aktualny = koren.getKoren();
        Vrchol<T> rodic = null;
        boolean lavy = true;
        while (aktualny != null) {
            rodic = aktualny;
            if (aktualny.getKluc().porovnaj(vrchol.getKluc()) >= 0) {
                aktualny = aktualny.getLavySyn();
                lavy = true;
            } else {
                aktualny = aktualny.getPravySyn();
                lavy = false;
            }
        }
        vrchol.setRodic(rodic);
        if (rodic == null) {
            koren = new Koren<>(vrchol);
        } else if (lavy) {
            rodic.setLavySyn(vrchol);
        } else {
            rodic.setPravySyn(vrchol);
        }
    }
}
