package testy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import rozhrania.IKluc;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorOperacii<T extends IKluc<T>> {
    private final KdStrom<T> strom;
    private final int pocetOperacii;
    private final int maxRozsah;
    private ArrayList<Vrchol<T>> zoznamVlozenychVrcholov;
    private Random random;
    private long seed;

    public GeneratorOperacii(KdStrom<T> kdStrom, int parPocetOperacii, int parMaxRozsah) {
        this.strom = kdStrom;
        this.pocetOperacii = parPocetOperacii;
        this.maxRozsah = parMaxRozsah;
        this.zoznamVlozenychVrcholov = new ArrayList<>();
        for (int j = 0; j < 1000000; j++) {
            this.seed = 233866529706100L;
            this.random = new Random(seed);
            for (int i = 0; i < 10; i++) {
                metodaVkladania();
            }
            //skontrolujPocetVrcholov();
            boolean uspech = generujOperacie();
            if (!uspech) {
                return;
            }
        }
    }

    private boolean generujOperacie() {
        for (int i = 0; i < pocetOperacii; i++) {
            double rand = random.nextDouble();
            if (true) { //rand < 0.85
                boolean spravne = metodaMazania();
                if (!spravne) {
                    System.out.println("Chyba pri mazani");
                    System.out.println("Seed: " + this.seed);
                    return false;
                }
            } else {
                metodaVyhladavania();
            }
        }
        return true;
    }

    public void metodaVkladania() {
        int atributA = random.nextInt(this.maxRozsah);
        int atributB = random.nextInt(this.maxRozsah);
        String atributC = RandomStringGenerator.generateRandomString(10);
        T data = (T) new GenerovaneData(atributA, atributB, atributC);
        Vrchol<T> vrchol = new Vrchol<>(data);
        this.strom.vloz(vrchol);
        this.zoznamVlozenychVrcholov.add(vrchol);
        double rand = random.nextDouble();
        /*if (rand < 0.5) {
            T data2 = (T) new GenerovaneData(atributA, atributB, atributC);
            Vrchol<T> vrchol2 = new Vrchol<>(data2);
            this.strom.vloz(vrchol2);
            this.zoznamVlozenychVrcholov.add(vrchol2);
        }*/
    }

    public boolean metodaMazania() {
        if (this.zoznamVlozenychVrcholov.isEmpty()) {
            return false;
        }
        int index = (random.nextInt(this.zoznamVlozenychVrcholov.size()));
        Vrchol<T> vrchol = this.zoznamVlozenychVrcholov.get(index);
        this.strom.vyrad(vrchol);
        this.zoznamVlozenychVrcholov.remove(index);
        int pocetVrcholovPreOrder = skontrolujPocetVrcholov();
        return pocetVrcholovPreOrder == this.zoznamVlozenychVrcholov.size();
    }

    public void metodaVyhladavania() {
        if (this.zoznamVlozenychVrcholov.isEmpty()) {
            return;
        }
        int index = (random.nextInt(this.zoznamVlozenychVrcholov.size()));
        Vrchol<T> vrchol = this.zoznamVlozenychVrcholov.get(index);
        System.out.println("Vyhladavam vrchol: " + vrchol.getData().toString());
        ArrayList<Vrchol<T>> kluce = new ArrayList<>();
        kluce.add(vrchol);
        ArrayList<Vrchol<T>> vysledok = this.strom.bodoveVyhladavanie(kluce);
        if (vysledok.isEmpty()) {
            System.out.println("Vrchol nebol najdeny");
        } else {
            for (Vrchol<T> tVrchol : vysledok) {
                System.out.println("Vyhladaný vrchol: " + tVrchol.getData().toString());
            }
        }
    }

    private int skontrolujPocetVrcholov() {
        ArrayList<Vrchol<T>> vsetkyVrcholy = this.strom.inOrderPrehliadka();
        ArrayList<Vrchol<T>> duplikaty = new ArrayList<>();
        for (Vrchol<T> vrchol : vsetkyVrcholy) {
            duplikaty.addAll(vrchol.getDuplicity());
        }
        vsetkyVrcholy.addAll(duplikaty);
        System.out.println("Pocet vrcholov inOrder: " + vsetkyVrcholy.size());
        System.out.println("Pocet vrcholov v pomocnej strukture: " + this.zoznamVlozenychVrcholov.size());
        return vsetkyVrcholy.size();
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