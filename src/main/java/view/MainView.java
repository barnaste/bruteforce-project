package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadPresenter;
import interface_adapter.upload.confirm.UploadConfirmViewModel;
import interface_adapter.upload.result.UploadResultViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;
import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInteractor;
import use_case.upload.UploadOutputBoundary;
import view.upload.UploadConfirmView;
import view.upload.UploadResultView;
import view.upload.UploadSelectView;

/**
 * The Main View, for when the user is logged into the program.
 */
public class MainView extends JLayeredPane implements PropertyChangeListener {
    private final int OVERLAY_COLOR = 0x40829181;
    private final int DISPLAY_WIDTH = 1080;
    private final int DISPLAY_HEIGHT = 720;

    // class attributes
    private final String viewName = "main view";
    private final MainViewModel mainViewModel;

    private PublicGalleryView publicGalleryView;
    private PublicGalleryController publicGalleryController;
    private final JPanel galleryPanel;

    private LogoutController logoutController;

    private String currentUser = "";
    private final JLabel userLabel = new JLabel();

    private final JButton logOut;
    private final JButton upload;

    private final JTextField passwordInputField = new JTextField(15);

    public MainView(MainViewModel mainViewModel, PublicGalleryViewModel publicGalleryViewModel) {
        this.mainViewModel = mainViewModel;
        this.mainViewModel.addPropertyChangeListener(this);

        publicGalleryView = new PublicGalleryView(publicGalleryViewModel);
        publicGalleryView.setPublicGalleryController(publicGalleryController);

        this.setLayout(new OverlayLayout(this));
        this.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // TODO: what is the purpose of this passwordInputField? do we need it?
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MainState currentState = mainViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                mainViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        final JLabel title = new JLabel("Main View");
        final JPanel header = ViewComponentFactory.buildHorizontalPanel(List.of(title, userLabel));
        header.setOpaque(false);

        upload = ViewComponentFactory.buildButton("Upload");
        upload.addActionListener(evt -> overlayUploadView());

        logOut = ViewComponentFactory.buildButton("Log Out");
        logOut.addActionListener( e -> logoutController.execute(mainViewModel.getState().getUsername()));

        final JPanel buttons = ViewComponentFactory.buildVerticalPanel(List.of(upload, logOut));

        galleryPanel = new JPanel(new CardLayout());
        galleryPanel.setPreferredSize(new Dimension(800, 500));
        galleryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        // Add PublicGalleryView as a component in the CardLayout panel
        galleryPanel.add(publicGalleryView, publicGalleryView.getViewName());

        final JPanel body = ViewComponentFactory.buildHorizontalPanel(List.of(buttons, galleryPanel));
        mainPanel.add(ViewComponentFactory.buildVerticalPanel(List.of(header, body)));
        this.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private void switchToPublicGallery() {
        // Cast galleryPanel to JPanel and get the CardLayout to switch views
        CardLayout cl = (CardLayout) galleryPanel.getLayout();
        cl.show(galleryPanel, publicGalleryView.getViewName());
    }

    private void disableInteraction() {
        logOut.setEnabled(false);
        upload.setEnabled(false);
    }

    private void enableInteraction() {
        logOut.setEnabled(true);
        upload.setEnabled(true);
    }

    public void overlayUploadView() {
        JPanel cardPanel = new JPanel();
        // NOTE: we extend CardLayout so that whenever the top card is swapped, the
        // ENTIRE view is redrawn, and not just the region the card occupied.
        // This is because cards may be of variant dimensions. We would otherwise
        // have artefacts from previous cards if they were of larger dimensions.
        CardLayout cardLayout = new CardLayout() {
            @Override
            public void show(Container parent, String name) {
                super.show(parent, name);
                revalidate();
                repaint();
            }
        };
        cardPanel.setLayout(cardLayout);

        UploadSelectViewModel selectorViewModel = new UploadSelectViewModel();
        UploadSelectView selectorView = new UploadSelectView(selectorViewModel);
        cardPanel.add(selectorView, selectorView.getViewName());

        UploadConfirmViewModel confirmViewModel = new UploadConfirmViewModel();
        UploadConfirmView confirmView = new UploadConfirmView(confirmViewModel);
        cardPanel.add(confirmView, confirmView.getViewName());

        UploadResultViewModel resultViewModel = new UploadResultViewModel();
        UploadResultView resultView = new UploadResultView(resultViewModel);
        cardPanel.add(resultView, resultView.getViewName());

        ViewManagerModel uploadManagerModel = new ViewManagerModel();
        new ViewManager(cardPanel, cardLayout, uploadManagerModel);

        UploadOutputBoundary uploadOutputBoundary = new UploadPresenter(
                uploadManagerModel,
                selectorViewModel,
                confirmViewModel,
                resultViewModel
        );
        // TODO: this is a makeshift setup -- the userDataAccessObject should already exist and
        //  is expected to be injected into the main view, or accessible by the main view somehow
        UserDataAccessObject userDataAccessObject = new MongoUserDataAccessObject();
        userDataAccessObject.setCurrentUsername(this.currentUser);
        UploadInputBoundary uploadInteractor = new UploadInteractor(
                uploadOutputBoundary,
                new MongoImageDataAccessObject(),
                new MongoPlantDataAccessObject(),
                userDataAccessObject
        );
        UploadController controller = new UploadController(uploadInteractor);

        selectorView.setController(controller);
        confirmView.setController(controller);
        resultView.setController(controller);

        uploadManagerModel.setState(selectorView.getViewName());
        uploadManagerModel.firePropertyChanged();

        // create an overlay with the created cardPanel as the popup
        overlay(cardPanel, uploadInteractor::setEscapeMap);
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

            this.remove(backgroundPanel);
            this.revalidate();
            this.repaint();
        });

        this.add(backgroundPanel, JLayeredPane.PALETTE_LAYER);
        this.revalidate();
        this.repaint();
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setPublicGalleryController(PublicGalleryController publicGalleryController) {
        this.publicGalleryController = publicGalleryController;
        publicGalleryView.setPublicGalleryController(publicGalleryController);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final MainState state = (MainState) evt.getNewValue();
            currentUser = state.getUsername();
            userLabel.setText("Currently logged in: " + this.currentUser);

            if (state.isPublic()) {
                switchToPublicGallery();
                publicGalleryController.loadPage(0); // Load first page when switching to public gallery
            }
        }

    }
}