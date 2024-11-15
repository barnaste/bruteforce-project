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
    private final int NUM_OF_COLUMNS = 5;
    private final int NUM_OF_ROWS = 3;
    private final String viewName = "public gallery";
    private int currentPage = 0;
    private int totalPages;

    private PublicGalleryController controller;
    private final PublicGalleryViewModel publicGalleryViewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;
    private final JLabel pageLabel;

    public PublicGalleryView(PublicGalleryViewModel publicGalleryViewModel, int totalPages) {
        this.publicGalleryViewModel = publicGalleryViewModel;
        this.totalPages = totalPages;
        publicGalleryViewModel.addPropertyChangeListener(this);

        // Set up the layout
        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(NUM_OF_ROWS, NUM_OF_COLUMNS, 5, 5));
        imagesGrid.setPreferredSize(new Dimension(835, 650));
        imagesGrid.setBackground(new Color(236, 245, 233));
        add(new JScrollPane(imagesGrid), BorderLayout.CENTER);

        // Navigation buttons panel
        JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");
        pageLabel = new JLabel("Page: " + (currentPage + 1) + " / " + totalPages);

        previousPageButton.addActionListener(e -> loadPreviousPage());
        nextPageButton.addActionListener(e -> loadNextPage());

        navigationPanel.add(previousPageButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);
        updateNavigationButtons();
    }

    public void setPublicGalleryController(PublicGalleryController controller) {
        this.controller = controller;
    }

    private void loadPage(int pageNumber) {
        if (controller != null) {
            controller.loadPage(pageNumber);
        }
        updateNavigationButtons();
    }


    private void loadNextPage() {
        currentPage++;
        loadPage(currentPage);
    }

    private void loadPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPage(currentPage);
        }
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
//                JButton editButton = new JButton("Edit");
                JButton infoButton = new JButton("Info");

//                editButton.setBackground(new Color(224, 242, 213));
                infoButton.setBackground(new Color(224, 242, 213));

//                editButton.addActionListener(e -> {
//                    System.out.println("Edit button clicked");
//                });
                infoButton.addActionListener(e -> {
                    System.out.println("Info button clicked");
                });

//                buttonPanel.add(editButton);
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

    private void updateNavigationButtons() {
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
        pageLabel.setText("Page: " + (currentPage + 1) + " / " + totalPages);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // System.out.println("Property change triggered for page: " + currentPage);
        PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        currentPage = state.getCurrentPage();
        totalPages = state.getTotalPages();
        displayImages(state.getPlantImages());
    }
}
