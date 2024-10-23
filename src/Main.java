import US.KdStrom.GeneratorOperacii;
import triedy.Nehnutelnost;
import triedy.Parcela;

public class Main {
    public static void main(String[] args) {
        long startTime1 = System.nanoTime();
        GeneratorOperacii<Nehnutelnost> generatorOperacii = new GeneratorOperacii<>(10, 4, 5, 10, Nehnutelnost.class);
        long endTime1 = System.nanoTime();
        double seconds1 = (double) (endTime1 - startTime1) / 1_000_000_000.0;
        //generatorOperacii.vypisVrcholy();
        System.out.println("Čas vkladania: " + seconds1);
        System.out.println("Hĺbka: " + generatorOperacii.getHlbka());
        System.out.println("Počet vrcholov: " + generatorOperacii.getPocetVrcholov());
        /*long startTime2 = System.nanoTime();
        GeneratorOperacii<Parcela> generatorOperacii1 = new GeneratorOperacii<>(10, 10000, 5,10, Parcela.class);
        long endTime2 = System.nanoTime();
        double seconds2 = (double) (endTime2 - startTime2) / 1_000_000_000.0;
        //generatorOperacii1.vypisVrcholy();
        System.out.println("Čas vkladania: " + seconds2);
        System.out.println("Hĺbka: " + generatorOperacii1.getHlbka());
        System.out.println("Počet vrcholov: " + generatorOperacii1.getPocetVrcholov());*/
    }
}
