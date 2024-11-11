package view.gallery;

import interface_adapter.load_user_gallery.UserGalleryController;
import interface_adapter.load_user_gallery.UserGalleryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class UserGalleryView extends JPanel {
    private final String viewName = "public gallery";
    private final int pageSize = 16;
    private int currentPage = 0;

    private UserGalleryController controller;
    private final UserGalleryViewModel viewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;

    public UserGalleryView(UserGalleryViewModel viewModel) {
        this.viewModel = viewModel;

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

    public void setUserGalleryController(UserGalleryController controller) {
        this.controller = controller;
    }

    private void loadPage(int pageNumber) {
        if (controller != null) {
            // TODO: fix this
            String username = "";
            controller.loadPage(username, pageNumber);
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
        // TODO: implement getTotalPages
        int totalPages = 3;
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
    }

    public String getViewName() {
        return viewName;
    }

}
