import US.KdStrom.TesterVkladania;
import triedy.Nehnutelnost;
import triedy.Parcela;

public class Main {
    public static void main(String[] args) {
        long startTime1 = System.nanoTime();
        TesterVkladania<Nehnutelnost> testerVkladania = new TesterVkladania<>(1000000, 1000, Nehnutelnost.class);
        long endTime1 = System.nanoTime();
        double seconds1 = (double) (endTime1 - startTime1) / 1_000_000_000.0;
        //testerVkladania.vypisVrcholy();
        System.out.println("Čas vkladania: " + seconds1);
        System.out.println("Hĺbka: " + testerVkladania.getHlbka());
        System.out.println("Počet vrcholov: " + testerVkladania.getPocetVrcholov());
        long startTime2 = System.nanoTime();
        TesterVkladania<Parcela> testerVkladania1 = new TesterVkladania<>(1000000, 1000, Parcela.class);
        long endTime2 = System.nanoTime();
        double seconds2 = (double) (endTime2 - startTime2) / 1_000_000_000.0;
        //testerVkladania1.vypisVrcholy();
        System.out.println("Čas vkladania: " + seconds2);
        System.out.println("Hĺbka: " + testerVkladania1.getHlbka());
        System.out.println("Počet vrcholov: " + testerVkladania1.getPocetVrcholov());
    }
}
