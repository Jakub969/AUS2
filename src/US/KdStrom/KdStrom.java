package US.KdStrom;


import rozhrania.IKluc;

public class KdStrom<T extends IKluc<T>> {
    private Koren<T> koren;
    private int hlbka;
    private final int pocetKlucov;
    private int pocetVrcholov;

    public KdStrom(Koren<T> koren, int pocetKlucov) {
        this.koren = koren;
        this.pocetKlucov = pocetKlucov;
        this.hlbka = 0;
        this.pocetVrcholov = 1;
    }

    public void vloz(Vrchol<T> vrchol) {
        Vrchol<T> aktualny = this.koren.getKoren();
        Vrchol<T> rodic = null;
        boolean lavy = true;
        int lokalnaHlbka = 0;
        while (aktualny != null) {
            rodic = aktualny;
            int poradieKluca = this.hlbka % this.pocetKlucov;
            int porovnanie = aktualny.getData().porovnaj(vrchol.getData(), poradieKluca);
            if (porovnanie == 0 || porovnanie == -1) {
                aktualny = aktualny.getLavySyn();
                lavy = true;
            } else if (porovnanie == 1) {
                aktualny = aktualny.getPravySyn();
                lavy = false;
            } else {
                throw new IllegalStateException("Porovnanie klucov zlyhalo");
            }
            lokalnaHlbka++;
        }
        vrchol.setRodic(rodic);
        if (rodic == null) {
            this.koren = new Koren<>(vrchol);
        } else if (lavy) {
            rodic.setLavySyn(vrchol);
            pocetVrcholov++;
        } else {
            rodic.setPravySyn(vrchol);
            pocetVrcholov++;
        }

        if (lokalnaHlbka > this.hlbka) {
            this.hlbka = lokalnaHlbka;
        }
    }

    public Koren<T> getKoren() {
        return koren;
    }

    public int getHlbka() {
        return hlbka;
    }

    public int getPocetVrcholov() {
        return pocetVrcholov;
    }
}
