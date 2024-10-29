import GUI.Model.Model;
import GUI.View.View;
import GUI.Controller.Controller;
import US.KdStrom.KdStrom;
import testy.VkladanieGeografickychObjektov;
import triedy.GPS;
import triedy.Nehnutelnost;

public class Main {
    public static void main(String[] args) {
        VkladanieGeografickychObjektov<Nehnutelnost> vkladanieGeografickychObjektov = new VkladanieGeografickychObjektov<>(new KdStrom<Nehnutelnost>(2));
        GPS gps1 = new GPS('N', 1.0, 'E', 1.0);
        GPS gps2 = new GPS('N', 2.0, 'E', 2.0);
        Nehnutelnost nehnutelnost1 = new Nehnutelnost(1, "Zahrada", null, null, gps1);
        Nehnutelnost nehnutelnost2 = new Nehnutelnost(1, "Zahrada", null, null, gps2);
        vkladanieGeografickychObjektov.metodaVkladania(nehnutelnost1, nehnutelnost2);
        /*Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.setVisible(true);*/
    }
}
