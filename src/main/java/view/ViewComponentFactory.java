package view;

import javax.swing.*;

public class ViewComponentFactory {

    public static JButton buildButton(String text) {
        JButton button = new JButton(text);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}
