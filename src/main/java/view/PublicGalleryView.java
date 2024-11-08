package view;

import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PublicGalleryView extends JPanel {
    private final String viewName = "public gallery";
    private int pageSize = 16;
    private int currentPage = 0;

    private PublicGalleryController controller;
    private final PublicGalleryViewModel viewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;

    public PublicGalleryView(PublicGalleryViewModel viewModel) {
        this.viewModel = viewModel;

        // Set up the layout
        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(4, 4, 5, 5)); // 16x16 grid with spacing
        add(new JScrollPane(imagesGrid), BorderLayout.CENTER);

        // Navigation buttons panel
        JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPreviousPage();
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNextPage();
            }
        });

        navigationPanel.add(previousPageButton);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);

        // Initial page load
        loadPage(currentPage);
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
        // TODO: implement
        // int totalPages = viewModel.getTotalPages();
        int totalPages = 3;
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
    }

    public String getViewName() {
        return viewName;
    }

}
