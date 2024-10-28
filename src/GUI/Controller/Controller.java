package GUI.Controller;

import GUI.Model.Model;
import GUI.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.view.addButtonListener(new IncrementButtonListener());
    }

    class IncrementButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.incrementCounter();
            view.setLabelText("Counter: " + model.getCounter());
        }
    }
}
