package view.gallery;

import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.like_plant.LikePlantController;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryState;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import interface_adapter.public_plant_info.PublicPlantInfoController;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

public class PublicGalleryView extends JPanel implements PropertyChangeListener {
    private static final int NUM_OF_COLUMNS = 5;
    private static final int NUM_OF_ROWS = 3;
    private final String viewName = "public gallery";

    private PublicGalleryController controller;
    private PublicPlantInfoController plantViewController;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;
    private final JLabel pageLabel;

    private Consumer<Plant> displayPlantMap;
    private LikePlantController likePlantController;

    public PublicGalleryView(PublicGalleryViewModel publicGalleryViewModel, Consumer<Plant> displayPlantMap) {
        publicGalleryViewModel.addPropertyChangeListener(this);
        this.displayPlantMap = displayPlantMap;

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
  
    public void setLikePlantController(LikePlantController likePlantController) {
        this.likePlantController = likePlantController;
    }
    public void displayImages(List<BufferedImage> images, List<ObjectId> ids) {
        imagesGrid.removeAll();

        // Add each image with buttons below it to the grid
        for (int i = 0; i < NUM_OF_ROWS * NUM_OF_COLUMNS; i++) {
            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(new Color(236, 245, 233));
            imagePanel.setLayout(new GridBagLayout());

            JLabel imageLabel = new JLabel();
            if (images != null && i < images.size()) {
                BufferedImage image = images.get(i);
                ObjectId id = ids.get(i);
                Image scaledImage = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(new Color(236, 245, 233));

                JButton infoButton = new JButton("Info");
                infoButton.setBackground(new Color(224, 242, 213));

                MongoPlantDataAccessObject plantAccess = MongoPlantDataAccessObject.getInstance();
                infoButton.addActionListener(e -> this.displayPlantMap.accept(plantAccess.fetchPlantByID(id)));
                buttonPanel.add(infoButton);

                JButton likeButton = new JButton("Like");
                likeButton.setBackground(new Color(224, 242, 213));
                likeButton.addActionListener(e -> {
                    this.likePlantController.execute(id);
                });

                buttonPanel.add(likeButton);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 2, 0);
                imagePanel.add(imageLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(2, 0, 0, 0);
                imagePanel.add(buttonPanel, gbc);
            }
            else if (images == null || images.isEmpty()) {
                JPanel placeholderPanel = new JPanel();
                placeholderPanel.setPreferredSize(new Dimension(160, 200));
                placeholderPanel.setBackground(new Color(236, 245, 233));
                imagePanel.add(placeholderPanel);
            }

            imagesGrid.add(imagePanel);
        }

        // Refresh the grid layout to show the images
        imagesGrid.revalidate();
        imagesGrid.repaint();
    }

    private void updateNavigation(int currentPage, int totalPages) {
        int displayedTotalPages = Math.max(totalPages, 1);
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < displayedTotalPages - 1);
        pageLabel.setText("Page: " + (currentPage + 1) + " / " + displayedTotalPages);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        displayImages(state.getPlantImages(), state.getPlantID());
        updateNavigation(state.getCurrentPage(), state.getTotalPages());
    }

    public void refresh() {
        controller.loadPage(0);
    }
}
