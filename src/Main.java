import US.KdStrom.GeneratorOperacii;
import US.KdStrom.GeneratorOperacii2;
import US.KdStrom.GenerovaneData;
import US.KdStrom.KdStrom;
import triedy.Nehnutelnost;
import triedy.Parcela;
import GUI.Model.Model;
import GUI.View.View;
import GUI.Controller.Controller;

public class Main {
    public static void main(String[] args) {
        /*long startTime1 = System.nanoTime();
        GeneratorOperacii<Nehnutelnost> generatorOperacii = new GeneratorOperacii<>(new KdStrom<Nehnutelnost>(2),10, 4, 5, 10, Nehnutelnost.class);
        long endTime1 = System.nanoTime();
        double seconds1 = (double) (endTime1 - startTime1) / 1_000_000_000.0;
        generatorOperacii.vypisVrcholy();
        generatorOperacii.vypisVrcholy2();
        System.out.println("Čas vkladania: " + seconds1);
        System.out.println("Hĺbka: " + generatorOperacii.getHlbka());
        System.out.println("Počet vrcholov: " + generatorOperacii.getPocetVrcholov());*/
        /*long startTime2 = System.nanoTime();
        GeneratorOperacii<Parcela> generatorOperacii1 = new GeneratorOperacii<>(10, 10000, 5,10, Parcela.class);
        long endTime2 = System.nanoTime();
        double seconds2 = (double) (endTime2 - startTime2) / 1_000_000_000.0;
        //generatorOperacii1.vypisVrcholy();
        System.out.println("Čas vkladania: " + seconds2);
        System.out.println("Hĺbka: " + generatorOperacii1.getHlbka());
        System.out.println("Počet vrcholov: " + generatorOperacii1.getPocetVrcholov());*/
        //GeneratorOperacii2<GenerovaneData> generatorOperacii2 = new GeneratorOperacii2<>(new KdStrom<GenerovaneData>(4), 20, 10);
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        view.setVisible(true);
    }
}
