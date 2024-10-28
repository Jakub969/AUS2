package GUI.View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton button;
    private JLabel label;

    public View() {
        button = new JButton("Increment");
        label = new JLabel("Counter: 0");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLayout(new java.awt.FlowLayout());
        this.add(label);
        this.add(button);
    }

    public void setLabelText(String text) {
        label.setText(text);
    }

    public void addButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }
}
