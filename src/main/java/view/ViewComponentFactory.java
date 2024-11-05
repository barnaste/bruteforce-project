package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewComponentFactory {

    public static JButton buildButton(String text) {
        JButton button = new JButton(text);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

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
