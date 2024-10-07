package US.KdStrom;

public class KdStrom<T> {
    private Koren<T> koren;
    private int hlbka;

    public KdStrom(Koren<T> koren) {
        this.koren = koren;
        this.hlbka = 0;
    }

    public Koren<T> getKoren() {
        return koren;
    }

    public void vloz(Vrchol<T> vrchol) {
        Vrchol<T> aktualny = this.koren.getKoren();
        Vrchol<T> rodic = null;
        boolean lavy = true;
        while (aktualny != null) {
            rodic = aktualny;
            int poradieKluca = this.hlbka % aktualny.getPocetKlucov();
            int porovnanie = aktualny.getKluce()[poradieKluca].porovnaj(vrchol.getKluce()[poradieKluca], poradieKluca);
            if (porovnanie == 0 || porovnanie == -1) {
                aktualny = aktualny.getLavySyn();
                lavy = true;
            } else if (porovnanie == 1) {
                aktualny = aktualny.getPravySyn();
                lavy = false;
            } else {
                throw new IllegalStateException("Porovnanie klucov zlyhalo");
            }
        }
        vrchol.setRodic(rodic);
        if (rodic == null) {
            this.koren = new Koren<>(vrchol);
        } else if (lavy) {
            rodic.setLavySyn(vrchol);
            this.hlbka++;
        } else {
            rodic.setPravySyn(vrchol);
            this.hlbka++;
        }
    }
}
