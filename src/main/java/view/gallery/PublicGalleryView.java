package view.gallery;

import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryState;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PublicGalleryView extends JPanel implements PropertyChangeListener {
    private static final int NUM_OF_COLUMNS = 5;
    private static final int NUM_OF_ROWS = 3;
    private final String viewName = "public gallery";

    private PublicGalleryController controller;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;
    private final JLabel pageLabel;

    public PublicGalleryView(PublicGalleryViewModel publicGalleryViewModel) {
        publicGalleryViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(NUM_OF_ROWS, NUM_OF_COLUMNS, 5, 5));
        add(imagesGrid, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");
        pageLabel = new JLabel();

        previousPageButton.addActionListener(e -> {
            if (controller != null) {
                controller.loadPreviousPage();
            }
        });

        nextPageButton.addActionListener(e -> {
            if (controller != null) {
                controller.loadNextPage();
            }
        });

        navigationPanel.add(previousPageButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);

        updateNavigation(0, 1);
    }

    public void setPublicGalleryController(PublicGalleryController controller) {
        this.controller = controller;
    }

    public void displayImages(List<BufferedImage> images) {
        // System.out.println("Displaying " + images.size() + " images for page " + currentPage);
        imagesGrid.removeAll();

        // Check if images is null or empty
        if (images == null || images.isEmpty()) {
            imagesGrid.revalidate();
            imagesGrid.repaint();
            return; // Exit if there are no images to display
        }

        // Add each image with buttons below it to the grid
        for (int i = 0; i < NUM_OF_ROWS * NUM_OF_COLUMNS; i++) {
            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(new Color(236, 245, 233));
            imagePanel.setLayout(new GridBagLayout());

            JLabel imageLabel = new JLabel();
            if (i < images.size()) {
                BufferedImage image = images.get(i);
                Image scaledImage = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(new Color(236, 245, 233));

                JButton infoButton = new JButton("Info");
                infoButton.setBackground(new Color(224, 242, 213));
                                infoButton.addActionListener(e -> {
                    System.out.println("Info button clicked");
                });
                buttonPanel.add(infoButton);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 2, 0);
                imagePanel.add(imageLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(2, 0, 0, 0);
                imagePanel.add(buttonPanel, gbc);
            }

            imagesGrid.add(imagePanel);
        }

        // Refresh the grid layout to show the images
        imagesGrid.revalidate();
        imagesGrid.repaint();
    }

    private void updateNavigation(int currentPage, int totalPages) {
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
        pageLabel.setText("Page: " + (currentPage + 1) + " / " + totalPages);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        displayImages(state.getPlantImages());
        updateNavigation(state.getCurrentPage(), state.getTotalPages());
    }
}
