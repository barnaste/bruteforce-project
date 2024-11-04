package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * GalleryPanel displays a collection of images in a grid layout.
 */
public class UserGalleryPanel extends JPanel {

    private final List<BufferedImage> images; // All images in the gallery
    private int totalUserImages;
    private int currentPage;
    private final int pageSize = 16;

    public UserGalleryPanel() {
        this.images = new ArrayList<>();
        this.setLayout(new GridLayout(4, 4, 10, 10)); // Adjust grid size and spacing as needed
        this.setPreferredSize(new Dimension(500, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Sets the images in the gallery.
     *
     * @param images List of BufferedImages to set
     */
    public void setImages(List<BufferedImage> images) {
        this.images.clear();
        this.images.addAll(images);
        this.currentPage = 0; // Reset to the first page
        showPage();
    }

    /**
     * Displays images on the current page.
     */
    private void showPage() {
        this.removeAll();
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, images.size());

        for (int i = start; i < end; i++) {
            JLabel imageLabel = createClickableImageLabel(images.get(i));
            this.add(imageLabel);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Creates a JLabel for an image that listens for clicks.
     *
     * @param image ImageIcon to display in the label
     * @return a JLabel configured with the image and click handling
     */
    private JLabel createClickableImageLabel(BufferedImage image) {
        // JLabel imageLabel = new JLabel(image);
        JLabel imageLabel = new JLabel();
        imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set hand cursor on hover

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle the click event (e.g., open a larger view or show details)
                onImageClick(image);
            }
        });

        return imageLabel;
    }

    /**
     * Action to perform when an image is clicked.
     *
     * @param image The ImageIcon that was clicked
     */
    private void onImageClick(BufferedImage image) {
        // Example: Show the image in a larger view
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        // JLabel label = new JLabel(image);
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label);

        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    public void setTotalImages(int totalImages) {
        this.totalUserImages = totalImages;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalUserImages / pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int pageIndex) {
        this.currentPage = Math.max(0, Math.min(pageIndex, getTotalPages() - 1));
        showPage();
    }

    public void nextPage() {
        if (currentPage < getTotalPages() - 1) {
            currentPage++;
            showPage();
        }
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            showPage();
        }
    }

}
