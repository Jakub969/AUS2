package US.KdStrom;

import rozhrania.IKluc;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorOperacii2<T extends IKluc<T>> {
    private final KdStrom<T> strom;
    private final int pocetOperacii;
    private final int maxRozsah;
    private ArrayList<Vrchol<T>> zoznamVlozenychVrcholov;
    private final Random random;

    public GeneratorOperacii2(int parPocetOperacii, int parMaxRozsah) {
        this.strom = new KdStrom<>(4);
        this.pocetOperacii = parPocetOperacii;
        this.maxRozsah = parMaxRozsah;
        this.zoznamVlozenychVrcholov = new ArrayList<>();
        this.random = new Random(System.nanoTime());
        generujOperacie();
    }

    private void generujOperacie() {
        for (int i = 0; i < pocetOperacii; i++) {
            double rand = random.nextDouble();
            if (rand < 0.60) {
                metodaVkladania();
            } else if (rand < 0.85) {
                metodaMazania();
            } else {
                metodaVyhladavania();
            }
        }
    }

    public void metodaVkladania() {
        double atributA = Math.random() * this.maxRozsah;
        String atributB = RandomStringGenerator.generateRandomString(10);
        int atributC = random.nextInt(this.maxRozsah);
        double atributD = Math.random() * this.maxRozsah;
        T data = (T) new GenerovaneData(atributA, atributB, atributC, atributD);
        Vrchol<T> vrchol = new Vrchol<>(data);
        System.out.println("Vkladam vrchol: " + vrchol.getData().toString());
        this.strom.vloz(vrchol);
        this.zoznamVlozenychVrcholov.add(vrchol);
        double rand = random.nextDouble();
        if (rand < 0.5) {
            T data2 = (T) new GenerovaneData(atributA, atributB, atributC, atributD);
            Vrchol<T> vrchol2 = new Vrchol<>(data2);
            System.out.println("Vkladam duplicitu: " + vrchol2.getData().toString());
            this.strom.vloz(vrchol2);
            this.zoznamVlozenychVrcholov.add(vrchol2);
        }
    }

    public void metodaMazania() {
        if (this.zoznamVlozenychVrcholov.isEmpty()) {
            return;
        }
        int index = (random.nextInt(this.zoznamVlozenychVrcholov.size()));
        Vrchol<T> vrchol = this.zoznamVlozenychVrcholov.get(index);
        System.out.println("Vymazavam vrchol: " + vrchol.getData().toString());
        this.strom.vyrad(vrchol);
        this.zoznamVlozenychVrcholov.remove(index);
    }

    public void metodaVyhladavania() {
        if (this.zoznamVlozenychVrcholov.isEmpty()) {
            return;
        }
        int index = (random.nextInt(this.zoznamVlozenychVrcholov.size()));
        Vrchol<T> vrchol = this.zoznamVlozenychVrcholov.get(index);
        System.out.println("Vyhladavam vrchol: " + vrchol.getData().toString());
        ArrayList<Vrchol<T>> vysledok = this.strom.vyhladaj(vrchol.getData(), vrchol.getData());
        if (vysledok.isEmpty()) {
            System.out.println("Vrchol nebol najdeny");
        } else {
            for (Vrchol<T> tVrchol : vysledok) {
                System.out.println("Vyhladaný vrchol: " + tVrchol.getData().toString());
            }
        }
    }

    public class RandomStringGenerator {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final Random RANDOM = new Random();

        public static String generateRandomString(int length) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = RANDOM.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            return sb.toString();
        }
    }
}