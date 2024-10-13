package US.KdStrom;

import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;

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
        for (int i = 0; i < this.pocetVlozeni; i++) {
            int x = (int) (Math.random() * this.maxRozsah);
            int y = (int) (Math.random() * this.maxRozsah);
            GPS gps = new GPS('E', x, 'S', y);
            T data;
            Vrchol<T> vrchol;
            try {
                if (instanciaTriedy == Nehnutelnost.class) {
                    data = instanciaTriedy.cast(new Nehnutelnost(i, "popis", null, gps));
                } else if (instanciaTriedy == Parcela.class) {
                    data = instanciaTriedy.cast(new Parcela(i, "popis", null, gps));
                } else {
                    throw new IllegalArgumentException("Unsupported type");
                }
                vrchol = new Vrchol<>(data);
                this.strom.vloz(vrchol);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Type casting error", e);
            }
        }
    }

    public void vypisVrcholy() {
        ArrayList<Vrchol<T>> vrcholy = this.strom.inOrderPrehliadka();
        for (Vrchol<T> vrchol : vrcholy) {
            T data = vrchol.getData();
            if (data instanceof Nehnutelnost) {
                Nehnutelnost nehnutelnost = (Nehnutelnost) data;
                System.out.println("Supisne číslo: " + nehnutelnost.getSupisneCislo());
            } else if (data instanceof Parcela) {
                Parcela parcela = (Parcela) data;
                System.out.println("Číslo parcely: " + parcela.getCisloParcely());
            } else {
                throw new IllegalArgumentException("Unsupported type");
            }
        }
    }
}
