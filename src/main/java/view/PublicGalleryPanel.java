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

public class PublicGalleryPanel extends JPanel {
    private final String viewName = "public gallery";
    private int pageSize = 16;
    private int currentPage = 0;

    private PublicGalleryController controller;
    private final PublicGalleryViewModel viewModel;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;

    public PublicGalleryPanel(PublicGalleryViewModel viewModel) {
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
        // Only use controller if it's set
        if (controller != null) {
            controller.loadPage(pageNumber);
        }

        // Assume images are provided by the viewModel when loaded
        updateImageGrid();
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

    private void updateImageGrid() {
        imagesGrid.removeAll();

        // Retrieve images from the view model
        // TODO: implement
        // List<BufferedImage> images = viewModel.getBufferedImages();
        List<BufferedImage> images = new ArrayList<BufferedImage>();

        // Display each image as a JLabel with an ImageIcon
        for (BufferedImage image : images) {
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imagesGrid.add(imageLabel);
        }

        // Refresh the grid to show new images
        imagesGrid.revalidate();
        imagesGrid.repaint();
    }

    public String getViewName() {
        return viewName;
    }

}
