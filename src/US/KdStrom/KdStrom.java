package US.KdStrom;


import rozhrania.IKluc;

import java.util.ArrayList;

public class KdStrom<T extends IKluc<T>> {
    private Koren<T> koren;
    private int hlbka;
    private final int pocetKlucov;
    private int pocetVrcholov;

    public KdStrom(int pocetKlucov) {
        this.koren = null;
        this.pocetKlucov = pocetKlucov;
        this.hlbka = 0;
        this.pocetVrcholov = 1;
    }

    public void vloz(Vrchol<T> vrchol) {
        if(this.koren == null) {
            this.koren = new Koren<T>(vrchol);
            return;
        }
        Vrchol<T> aktualny = this.koren.getKoren();
        Vrchol<T> rodic = null;
        boolean lavy = true;
        int lokalnaHlbka = 0;
        while (aktualny != null) {
            rodic = aktualny;
            int poradieKluca = this.hlbka % this.pocetKlucov;
            int porovnanie = aktualny.getData().porovnaj(vrchol.getData(), poradieKluca);
            if (porovnanie == 1 || porovnanie == 0) { //TODO: porovnanie == 1 alebo 0?
                aktualny = aktualny.getLavySyn();
                lavy = true;
            } else if (porovnanie == -1) { //TODO: porovnanie == -1 ?
                aktualny = aktualny.getPravySyn();
                lavy = false;
            } else {
                throw new IllegalStateException("Porovnanie klucov zlyhalo");
            }
            lokalnaHlbka++;
        }
        vrchol.setRodic(rodic);
        if (rodic == null) {
            this.koren = new Koren<T>(vrchol);
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

    public ArrayList<Vrchol<T>> inOrderPrehliadka() {
        Vrchol<T> aktualny = this.koren.getKoren();
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        while (aktualny != null) {
            if (aktualny.getLavySyn() == null) {
                vrcholy.add(aktualny);
                aktualny = aktualny.getPravySyn();
            } else {
                Vrchol<T> predchodca = aktualny.getLavySyn();
                while (predchodca.getPravySyn() != null && predchodca.getPravySyn() != aktualny) {
                    predchodca = predchodca.getPravySyn();
                }
                if (predchodca.getPravySyn() == null) {
                    predchodca.setPravySyn(aktualny);
                    aktualny = aktualny.getLavySyn();
                } else {
                    predchodca.setPravySyn(null);
                    vrcholy.add(aktualny);
                    aktualny = aktualny.getPravySyn();
                }
            }
        }
        return vrcholy;
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
