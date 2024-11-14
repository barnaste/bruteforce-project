package view.plant_view;

import data_access.*;
import entity.Plant;
import interface_adapter.edit_plant.EditPlantController;
import use_case.edit_plant.EditPlantInteractor;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

class MainViewDemo {
    private final int OVERLAY_COLOR = 0x40829181;
    private final int BACKGROUND_COLOR = 0xfffffbef;

    private final int DISPLAY_WIDTH = 1200;
    private final int DISPLAY_HEIGHT = 750;

    // class attributes
    private final String viewName = "Main View Demo";
    private final JLayeredPane mainView;
    private final JButton upload;

    public MainViewDemo() {
        mainView = new JLayeredPane();
        mainView.setLayout(new OverlayLayout(mainView));

        // set up main view -- a simple canvas with an "Upload" button
        upload = new JButton("Upload");
        upload.setBorderPainted(true);
        upload.setContentAreaFilled(false);
        upload.setFocusPainted(false);

        JPanel mainPanel = new JPanel();
        mainPanel.add(upload);
        mainPanel.setBackground(new Color(BACKGROUND_COLOR, true));
        mainView.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        // attach use case to the button
        upload.addActionListener(e -> {
            // NOTICE: this is where the use case is called.
            //  Once the main display and CA classes are implemented, the call path will be along the lines of
            //  MainController -> MainInteractor -> MainPresenter.overlayUploadView(), which signals a property
            //  change to a class of the developer's choice.
            //  Here, we cut straight to the overlayUploadView method.
//            overlayUploadView();
            overlayEditPlantView();
        });

        // create display frame
        JFrame frame = new JFrame(viewName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        frame.add(mainView);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void overlayEditPlantView() {
        // NOTE: we extend CardLayout so that whenever the top card is swapped, the
        // ENTIRE view is redrawn, and not just the region the card occupied.
        // This is because cards may be of variant dimensions. We would otherwise
        // have artefacts from previous cards if they were of larger dimensions.

        // TODO: we assume that the plant is somehow passed to this point. For now, we use a placeholder.
        Plant plant = new Plant(
            "6731575d73cd45672d2ee35e",
            "Ctenanthe setose",
            "admin",
            "My notes...",
            false
        );

        ImageDataAccessObject imageAccess = new MongoImageDataAccessObject();
        PlantDataAccessObject plantAccess = new MongoPlantDataAccessObject();

        JPanel overlay = new JPanel();
        EditPlantView view = new EditPlantView(plant, imageAccess.getImageFromID(plant.getImageID()));
        overlay.add(view, imageAccess);

        EditPlantInteractor interactor = new EditPlantInteractor(
                imageAccess,
                plantAccess
        );
        interactor.setPlant(plant);
        EditPlantController controller = new EditPlantController(interactor);
        view.setController(controller);

        this.overlay(view, interactor::setEscapeMap);
    }

    private void disableInteraction() {
        upload.setEnabled(false);
    }

    private void enableInteraction() {
        upload.setEnabled(true);
    }

    public void overlay(JPanel overlayPanel, Consumer<Runnable> setOverlayEscape) {
        // disable any interaction outside the overlay
        this.disableInteraction();

        // create a semi-transparent overlay
        // override painting functionality so that our overlay is semi-transparent
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(OVERLAY_COLOR, true)); // overlay color with transparency
                g.fillRect(0, 0, getWidth(), getHeight()); // fill the panel area with color
            }
        };
        backgroundPanel.setOpaque(false);
        // use a GridBagLayout to keep overlay content centered on the screen
        backgroundPanel.setLayout(new GridBagLayout());

        backgroundPanel.add(overlayPanel);
        setOverlayEscape.accept(() -> {
            // enable interaction outside the overlay once the overlay is removed
            this.enableInteraction();

            mainView.remove(backgroundPanel);
            mainView.revalidate();
            mainView.repaint();
        });

        mainView.add(backgroundPanel, JLayeredPane.PALETTE_LAYER);
        mainView.revalidate();
        mainView.repaint();
    }

    public static void main(String[] args) {
        new MainViewDemo();
    }
}