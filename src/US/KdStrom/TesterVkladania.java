package US.KdStrom;

import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.Random;

public class TesterVkladania<T extends IKluc<T>> {
    private final int pocetVlozeni;
    private final int maxRozsah;
    private final KdStrom<T> strom;
    private final Class<T> instanciaTriedy;

    public TesterVkladania(int pocetVlozeni, int maxRozsah, Class<T> trieda) {
        this.pocetVlozeni = pocetVlozeni;
        this.maxRozsah = maxRozsah;
        this.strom = new KdStrom<>(2);
        this.instanciaTriedy = trieda;
        metodaVkladania();
    }

    private void metodaVkladania() {
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < this.pocetVlozeni; i++) {
            if (i % 100 == 0) {
                random.setSeed(System.nanoTime());
            }
            double x = (random.nextDouble() * this.maxRozsah);
            double y = random.nextDouble() * this.maxRozsah;
            GPS gps = new GPS('N', x, 'E', y);
            T data;
            Vrchol<T> vrchol;
            try {
                if (instanciaTriedy == Nehnutelnost.class) {
                    data = instanciaTriedy.cast(new Nehnutelnost(i, "popis", null, gps));
                } else if (instanciaTriedy == Parcela.class) {
                    data = instanciaTriedy.cast(new Parcela(i, "popis", null, gps));
                } else {
                    throw new IllegalArgumentException("Nepodporovaný typ triedy");
                }
                vrchol = new Vrchol<>(data);
                this.strom.vloz(vrchol);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Error pri pretypovani", e);
            }
        }
    }

    public void vypisVrcholy() {
        ArrayList<Vrchol<T>> vrcholy = this.strom.inOrderPrehliadka();
        for (Vrchol<T> vrchol : vrcholy) {
            T data = vrchol.getData();
            if (data instanceof Nehnutelnost nehnutelnost) {
                if (vrchol.getRodic() == null) {
                    System.out.println("Vrchol zo súpisným číslom " + nehnutelnost.getSupisneCislo() + " je koreňom: " + vrchol.getData().toString());
                } else {
                    System.out.println("Vrchol zo supisným číslom: " + nehnutelnost.getSupisneCislo() + " má rodiča: " + vrchol.getRodic().getData().toString());
                }
                if (vrchol.getPravySyn() != null) {
                    System.out.println("Pravý syn: " + vrchol.getPravySyn().getData().toString());
                }
                if (vrchol.getLavySyn() != null) {
                    System.out.println("Ľavý syn: " + vrchol.getLavySyn().getData().toString());
                }
            } else if (data instanceof Parcela parcela) {
                if (vrchol.getRodic() == null) {
                    System.out.println("Vrchol z číslom parcely " + parcela.getCisloParcely() + " je koreňom " + vrchol.getData().toString());
                } else {
                    System.out.println("Vrchol z číslom parcely: " + parcela.getCisloParcely() + " má rodiča: " + vrchol.getRodic().getData().toString());
                }
                if (vrchol.getPravySyn() != null) {
                    System.out.println("Pravý syn: " + vrchol.getPravySyn().getData().toString());
                }
                if (vrchol.getLavySyn() != null) {
                    System.out.println("Ľavý syn: " + vrchol.getLavySyn().getData().toString());
                }
            } else {
                throw new IllegalArgumentException("Nepodporovaný typ");
            }
        }
    }

    public int getHlbka() {
        return this.strom.getHlbka();
    }

    public int getPocetVrcholov() {
        return this.strom.getPocetVrcholov();
    }
}
