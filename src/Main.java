import US.KdStrom.KdStrom;
import US.KdStrom.Koren;
import US.KdStrom.Vrchol;
import triedy.GPS;
import triedy.Nehnutelnost;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        KdStrom<Nehnutelnost> kdStromNehnutelnosti = new KdStrom<>(2);
        Koren<Nehnutelnost> koren = new Koren<>(new Vrchol<>(new Nehnutelnost(3, "popis", null, new GPS('E', 3.0, 'S', 3.0))));
        kdStromNehnutelnosti.vloz(koren);
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
            System.out.println("Supisne číslo rodiča: " + vrchol1.getRodic().getData().getSupisneCislo() + " Supisne číslo vrcholu: " + vrchol1.getData().getSupisneCislo());
            System.out.println();
        }
        System.out.println(kdStromNehnutelnosti.getHlbka());
        System.out.println(kdStromNehnutelnosti.getPocetVrcholov());
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = kdStromNehnutelnosti.inOrderPrehliadka();
        vrcholy.forEach(vrchol -> System.out.println("Vrchol: " + vrchol.getData().getSupisneCislo()));
    }
}
