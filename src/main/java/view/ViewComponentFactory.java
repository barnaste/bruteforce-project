package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A factory class for building View Components with a consistent style.
 */
public final class ViewComponentFactory {

    private ViewComponentFactory() {}

    /**
     * Builds a stylized JButton.
     * @param text the text to go in the button
     * @return the button
     */
    public static JButton buildButton(String text) {
        JButton button = new JButton(text);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Builds a JPanel containing the given components in left-right order with spacing in between.
     * @param components the components to go in the JPanel
     * @return the JPanel
     */
    public static JPanel buildHorizontalPanel(List<Component> components) {
        final JPanel p = new JPanel();
        for (int i = 0; i < components.size() - 1; i++) {
            p.add(components.get(i));
            p.add(Box.createRigidArea(new Dimension(10, 0)));
        }
        if (!components.isEmpty()) {
            p.add(components.get(components.size() - 1));
        }
        return p;
    }

    /**
     * Builds a JPanel containing the given components in top-down order with spacing in between.
     * @param components the components to go in the JPanel
     * @return the JPanel
     */
    public static JPanel buildVerticalPanel(List<Component> components) {
        final JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        for (int i = 0; i < components.size() - 1; i++) {
            p.add(components.get(i));
            p.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if (!components.isEmpty()) {
            p.add(components.get(components.size() - 1));
        }
        return p;

    public static BufferedImage buildCroppedImage(String imagePath) {
        BufferedImage image = null;
        try {
            // Crop the image so that it is has square aspect ratio
            BufferedImage sourceImg = ImageIO.read(new File(imagePath));
            int thumbWidth = Math.min(sourceImg.getWidth(), sourceImg.getHeight());
            image = sourceImg.getSubimage(
                    (sourceImg.getWidth() - thumbWidth) / 2,
                    (sourceImg.getHeight() - thumbWidth) / 2,
                    thumbWidth, thumbWidth);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return image;

    }
}
