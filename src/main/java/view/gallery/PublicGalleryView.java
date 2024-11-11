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
    private final int NUM_OF_ROWS = 4;
    private final String viewName = "public gallery";
    private int currentPage = 0;
    private int totalPages = 5;

    private PublicGalleryController controller;
    private final PublicGalleryViewModel publicGalleryViewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;

    public PublicGalleryView(PublicGalleryViewModel publicGalleryViewModel) {
        this.publicGalleryViewModel = publicGalleryViewModel;
        publicGalleryViewModel.addPropertyChangeListener(this);

        // Set up the layout
        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(NUM_OF_ROWS, NUM_OF_COLUMNS, 5, 5)); // 16x16 grid with spacing
        imagesGrid.setPreferredSize(new Dimension(835, 650)); // Make grid larger
        add(new JScrollPane(imagesGrid), BorderLayout.CENTER);

        // Navigation buttons panel
        JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");

        previousPageButton.addActionListener(e -> loadPreviousPage());
        nextPageButton.addActionListener(e -> loadNextPage());

        navigationPanel.add(previousPageButton);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);
        updateNavigationButtons();

        // Initial page load
        // loadPage(currentPage);
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
        // TODO: for debugging
        System.out.println("Displaying " + images.size() + " images for page " + currentPage);
        imagesGrid.removeAll();

        for (int i = 0; i < NUM_OF_ROWS * NUM_OF_COLUMNS; i++) {
            imagesGrid.add(new JLabel());
        }

        // Check if images is null or empty
        if (images == null || images.isEmpty()) {
            imagesGrid.revalidate();
            imagesGrid.repaint();
            return; // Exit the method if there are no images to display
        }

        // Add each image to the grid in row-major order
        for (int i = 0; i < images.size(); i++) {

            BufferedImage image = images.get(i);
            Image scaledImage = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH);

            Component component = imagesGrid.getComponent(i);  // Get the correct grid cell
            if (component instanceof JLabel) {
                ((JLabel) component).setIcon(new ImageIcon(scaledImage));  // Set the image at this index
            }
        }

        // Refresh the grid layout to show the images
        imagesGrid.revalidate();
        imagesGrid.repaint();
    }

    private void updateNavigationButtons() {
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: for debugging
        System.out.println("Property change triggered for page: " + currentPage);
        PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        currentPage = state.getCurrentPage();
        totalPages = state.getTotalPages();
        displayImages(state.getPlantImages());
        // TODO: this needs to be fixed.
    }
}
