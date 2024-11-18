package view.plant_view;

import data_access.*;
import entity.Plant;
import interface_adapter.publicplantview.PublicPlantViewController;
import org.bson.types.ObjectId;
import use_case.publicplant.PublicPlantInteractor;

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

        // set up main view -- a simple canvas with a "View" button
        upload = new JButton("View");
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
            //  MainController -> MainInteractor -> MainPresenter.overlayPublicPlantView(), which signals a property
            //  change to a class of the developer's choice.
            //  Here, we cut straight to the overlayUploadView method.
//            overlayPublicPlantview();
            overlayPublicPlantView();
        });

        // create display frame
        JFrame frame = new JFrame(viewName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        frame.add(mainView);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void overlayPublicPlantView() {
        // NOTE: we extend CardLayout so that whenever the top card is swapped, the
        // ENTIRE view is redrawn, and not just the region the card occupied.
        // This is because cards may be of variant dimensions. We would otherwise
        // have artefacts from previous cards if they were of larger dimensions.

        ImageDataAccessObject imageAccess = new MongoImageDataAccessObject();
        PlantDataAccessObject plantAccess = new MongoPlantDataAccessObject();
        Plant plant = plantAccess.fetchPlantByID(new ObjectId("673bbfbd92d5217c01829e1d"));

        JPanel overlay = new JPanel();
        PublicPlantView view = new PublicPlantView(plant, imageAccess.getImageFromID(plant.getImageID()));
        overlay.add(view, imageAccess);

        PublicPlantInteractor interactor = new PublicPlantInteractor(
                imageAccess,
                plantAccess
        );
        interactor.setPlant(plant);
        PublicPlantViewController controller = new PublicPlantViewController(interactor);
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