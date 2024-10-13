import US.KdStrom.TesterVkladania;
import triedy.Nehnutelnost;
import triedy.Parcela;

public class Main {
    public static void main(String[] args) {
        TesterVkladania<Nehnutelnost> testerVkladania = new TesterVkladania<>(100000, 1000, Nehnutelnost.class);
        //testerVkladania.vypisVrcholy();
        System.out.println("Hĺbka: " + testerVkladania.getHlbka());
        System.out.println("Počet vrcholov: " + testerVkladania.getPocetVrcholov());
        TesterVkladania<Parcela> testerVkladania1 = new TesterVkladania<>(100000, 1000, Parcela.class);
        //testerVkladania1.vypisVrcholy();
        System.out.println("Hĺbka: " + testerVkladania1.getHlbka());
        System.out.println("Počet vrcholov: " + testerVkladania1.getPocetVrcholov());
    }
}
