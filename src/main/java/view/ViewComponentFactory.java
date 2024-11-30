package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * A factory class for building View Components with a consistent style.
 */
public final class ViewComponentFactory {

    private ViewComponentFactory() {

    }

    /**
     * Builds a stylized JButton.
     * @param text the text to go in the button
     * @return the button
     */
    public static JButton buildButton(String text) {
        final JButton button = new JButton(text);
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
    }

    public static BufferedImage buildCroppedImage(String imagePath) {
        BufferedImage image = null;
        try {
            // Crop the image so that it is has square aspect ratio
            final BufferedImage sourceImg = ImageIO.read(new File(imagePath));
            final int thumbWidth = Math.min(sourceImg.getWidth(), sourceImg.getHeight());
            image = sourceImg.getSubimage(
                    (sourceImg.getWidth() - thumbWidth) / 2,
                    (sourceImg.getHeight() - thumbWidth) / 2,
                    thumbWidth, thumbWidth);

        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return image;
    }

    /**
     * Sets the preferred, minimum, and maximum size for the given button.
     * This ensures that the button has a consistent size regardless of layout constraints.
     *
     * @param button the button whose size is to be set
     * @param buttonSize the size to apply to the button
     */
    public static void setButtonSize(AbstractButton button, Dimension buttonSize) {
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
    }
}
