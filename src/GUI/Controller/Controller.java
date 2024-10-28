package GUI.Controller;

import GUI.Model.Model;
import GUI.View.View;
import triedy.GPS;
import triedy.Nehnutelnost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.view.addSearchButtonListener(new SearchButtonListener());
    }

    class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double dlzka = Double.parseDouble(view.getDlzka());
                double sirka = Double.parseDouble(view.getSirka());
                List<Nehnutelnost> results = model.searchNehnutelnosti(dlzka, sirka);
                StringBuilder resultText = new StringBuilder();
                for (Nehnutelnost nehnutelnost : results) {
                    resultText.append(nehnutelnost.toString()).append("\n");
                }
                view.setResultText(resultText.toString());
            } catch (NumberFormatException ex) {
                view.setResultText("Invalid GPS coordinates.");
            }
        }
    }
}
