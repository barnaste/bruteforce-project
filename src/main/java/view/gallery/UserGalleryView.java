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
import javax.swing.JScrollPane;

import org.bson.types.ObjectId;

import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.load_user_gallery.UserGalleryController;
import interface_adapter.load_user_gallery.UserGalleryState;
import interface_adapter.load_user_gallery.UserGalleryViewModel;

/**
 * Represents the user gallery view in the application, displaying a paginated grid of user plant images.
 * It allows navigation through pages and interaction with plant information through buttons under each image.
 * The view listens for property changes from the view model and updates the displayed images and navigation accordingly.
 */
public class UserGalleryView extends JPanel implements PropertyChangeListener {
    private static final int NUM_OF_COLUMNS = 5;
    private static final int NUM_OF_ROWS = 3;
    private final String viewName = "user gallery";

    private UserGalleryController userGalleryController;

    private final JPanel imagesGrid;
    private final JButton nextPageButton;
    private final JButton previousPageButton;
    private final JLabel pageLabel;

    private Consumer<Plant> displayPlantMap;

    public UserGalleryView(UserGalleryViewModel userGalleryViewModel, Consumer<Plant> displayPlantMap) {
        userGalleryViewModel.addPropertyChangeListener(this);
        this.displayPlantMap = displayPlantMap;

        setLayout(new BorderLayout());
        imagesGrid = new JPanel(new GridLayout(NUM_OF_ROWS, NUM_OF_COLUMNS, 5, 5));
        add(new JScrollPane(imagesGrid), BorderLayout.CENTER);

        final JPanel navigationPanel = new JPanel();
        previousPageButton = new JButton("Previous Page");
        nextPageButton = new JButton("Next Page");
        pageLabel = new JLabel();

        previousPageButton.addActionListener(evt -> {
            if (userGalleryController != null) {
                userGalleryController.loadPreviousPage();
            }
        });

        nextPageButton.addActionListener(evt -> {
            if (userGalleryController != null) {
                userGalleryController.loadNextPage();
            }
        });

        navigationPanel.add(previousPageButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextPageButton);
        add(navigationPanel, BorderLayout.SOUTH);

        updateNavigation(0, 1);
    }

    public void setUserGalleryController(UserGalleryController userGalleryController) {
        this.userGalleryController = userGalleryController;
    }

    /**
     * Displays a grid of images in the gallery. Each image is accompanied by an "Info & Edit" button.
     * The method updates the gallery by creating a grid of image panels, each containing an image and
     * a button that, when clicked, displays more information about the selected plant.
     *
     * If the `images` list is empty or null, the method will display empty placeholder panels.
     *
     * @param images A list of `BufferedImage` objects representing the images to be displayed in the gallery.
     * @param ids A list of `ObjectId` objects associated with each plant's image. Each image corresponds to a plant that can be edited.
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

                final JButton editButton = new JButton("Info & Edit");
                editButton.setBackground(new Color(224, 242, 213));

                final MongoPlantDataAccessObject plantAccess = MongoPlantDataAccessObject.getInstance();
                editButton.addActionListener(evt -> this.displayPlantMap.accept(plantAccess.fetchPlantByID(id)));
                buttonPanel.add(editButton);

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
        final UserGalleryState state = (UserGalleryState) evt.getNewValue();
        displayImages(state.getPlantImages(), state.getPlantID());
        updateNavigation(state.getCurrentPage(), state.getTotalPages());
    }

    /**
     * Refreshes the user gallery view by loading the first page of the gallery.
     * This method is typically used to reset the view to its initial state or
     * to reload the gallery when changes occur (e.g., after adding or deleting plants).
     */
    public void refresh() {
        userGalleryController.loadPage(0);
    }
}
