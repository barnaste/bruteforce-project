package view;

import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryState;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import interface_adapter.upload.result.UploadResultState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PublicGalleryView extends JPanel implements PropertyChangeListener {
    private final String viewName = "public gallery";
    private int currentPage = 0;
    private int totalPages = 0;

    private PublicGalleryController controller;
    private final PublicGalleryViewModel viewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;

    public PublicGalleryView(PublicGalleryViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        // Set up the layout
        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(4, 4, 5, 5)); // 16x16 grid with spacing
        imagesGrid.setPreferredSize(new Dimension(835, 640)); // Make grid larger
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

        // Initial page load
        loadPage(currentPage);
    }

    public void setPublicGalleryController(PublicGalleryController controller)
    {
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
        imagesGrid.removeAll();

        // Display each image as a JLabel with an ImageIcon
        for (BufferedImage image : images) {
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imagesGrid.add(imageLabel);
        }

        // Refresh the grid to show new images
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
        PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        totalPages = state.getTotalPages();
        displayImages(state.getPlantImages());
    }
}
