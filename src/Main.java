import GUI.Model.Model;
import GUI.View.View;
import GUI.Controller.Controller;
import US.KdStrom.KdStrom;
import testy.GeneratorOperacii;
import testy.GenerovaneData;

public class Main {
    public static void main(String[] args) {
        GeneratorOperacii<GenerovaneData> generatorOperacii = new GeneratorOperacii<>(new KdStrom<GenerovaneData>(2), 10, 50);

        /*Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.setVisible(true);*/
    }
}
