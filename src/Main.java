import GUI.Model.Model;
import GUI.View.View;
import GUI.Controller.Controller;
import US.KdStrom.KdStrom;
import testy.VkladanieGeografickychObjektov;
import triedy.GPS;
import triedy.Nehnutelnost;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.setVisible(true);
    }
}
