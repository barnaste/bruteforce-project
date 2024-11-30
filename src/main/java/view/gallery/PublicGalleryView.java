package view.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bson.types.ObjectId;

import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.like_plant.LikePlantController;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryState;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;

/**
 * Represents the public gallery view in the application, displaying a grid of plant images.
 * Provides navigation for paging through the gallery, and allows interaction with plant images
 * (viewing details or liking plants).
 */
public class PublicGalleryView extends JPanel implements PropertyChangeListener {
    private static final int NUM_OF_COLUMNS = 5;
    private static final int NUM_OF_ROWS = 3;
    private final String viewName = "public gallery";

    private PublicGalleryController publicGalleryController;

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

        final JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");
        pageLabel = new JLabel();

        previousPageButton.addActionListener(evt -> {
            if (publicGalleryController != null) {
                publicGalleryController.loadPreviousPage();
            }
        });

        nextPageButton.addActionListener(evt -> {
            if (publicGalleryController != null) {
                publicGalleryController.loadNextPage();
            }
        });

        navigationPanel.add(previousPageButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);

        updateNavigation(0, 1);
    }

    public void setPublicGalleryController(PublicGalleryController publicGalleryController) {
        this.publicGalleryController = publicGalleryController;
    }

    public void setLikePlantController(LikePlantController likePlantController) {
        this.likePlantController = likePlantController;
    }

    /**
     * Displays a grid of plant images in the public gallery.
     * Each image is accompanied by "Info" and "Like" buttons, which allow the user to view details
     * or like the respective plant.
     * If there are fewer images than available grid slots, empty placeholders are shown.
     *
     * @param images A list of `BufferedImage` objects representing the plant images to be displayed.
     * @param ids    A list of `ObjectId` corresponding to the plant images, used for identifying the
     *               plant in the database.
     */
    public void displayImages(List<BufferedImage> images, List<ObjectId> ids) {
        imagesGrid.removeAll();

        // Add each image with buttons below it to the grid
        for (int i = 0; i < NUM_OF_ROWS * NUM_OF_COLUMNS; i++) {
            final JPanel imagePanel = new JPanel();
            imagePanel.setBackground(new Color(236, 245, 233));
            imagePanel.setLayout(new GridBagLayout());

            final JLabel imageLabel = new JLabel();
            if (images != null && i < images.size()) {
                final BufferedImage image = images.get(i);
                final ObjectId id = ids.get(i);
                final Image scaledImage = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

                final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(new Color(236, 245, 233));

                final MongoPlantDataAccessObject plantAccess = MongoPlantDataAccessObject.getInstance();

                final JButton infoButton = new JButton("Info");
                infoButton.setBackground(new Color(224, 242, 213));
                infoButton.addActionListener(evt -> this.displayPlantMap.accept(plantAccess.fetchPlantByID(id)));
                buttonPanel.add(infoButton);

                final JButton likeButton = new JButton("Like");
                likeButton.setBackground(new Color(224, 242, 213));
                likeButton.addActionListener(evt -> this.likePlantController.execute(plantAccess.fetchPlantByID(id)));
                buttonPanel.add(likeButton);

                final GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 2, 0);
                imagePanel.add(imageLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(2, 0, 0, 0);
                imagePanel.add(buttonPanel, gbc);
            }
            else if (images == null || images.isEmpty()) {
                final JPanel placeholderPanel = new JPanel();
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
        final int displayedTotalPages = Math.max(totalPages, 1);
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < displayedTotalPages - 1);
        pageLabel.setText("Page: " + (currentPage + 1) + " / " + displayedTotalPages);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PublicGalleryState state = (PublicGalleryState) evt.getNewValue();
        displayImages(state.getPlantImages(), state.getPlantID());
        updateNavigation(state.getCurrentPage(), state.getTotalPages());
    }

    /**
     * Refreshes the public gallery by loading the first page of plants.
     * This method triggers the controller to load the initial page (page 0) of the public gallery.
     */
    public void refresh() {
        publicGalleryController.loadPage(0);
    }
}
