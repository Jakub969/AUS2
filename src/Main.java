import US.KdStrom.KdStrom;
import US.KdStrom.Koren;
import US.KdStrom.Vrchol;
import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;

public class Main {
    public static void main(String[] args) {
        Koren<Nehnutelnost> koren = new Koren<>(new Vrchol<>(new Nehnutelnost(1, "popis", null, new GPS('E', 3.0, 'S', 3.0))));
        KdStrom<Nehnutelnost> kdStromNehnutelnosti = new KdStrom<>(koren, 2);
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        System.out.println(kdStromNehnutelnosti.getHlbka());
        System.out.println(kdStromNehnutelnosti.getPocetVrcholov());
    }
}
