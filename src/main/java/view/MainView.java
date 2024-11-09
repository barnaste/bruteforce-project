package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.swap_gallery.SwapGalleryController;
import interface_adapter.swap_gallery.SwapGalleryPresenter;
import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadPresenter;
import interface_adapter.upload.confirm.UploadConfirmViewModel;
import interface_adapter.upload.result.UploadResultViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;
import use_case.swap_gallery.SwapGalleryInputBoundary;
import use_case.swap_gallery.SwapGalleryInteractor;
import use_case.swap_gallery.SwapGalleryOutputBoundary;
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

    final Dimension buttonSize = new Dimension(200, 50);

    private final String viewName = "main view";
    private final MainViewModel mainViewModel;

    private LogoutController logoutController;
    private SwapGalleryController swapGalleryController;

    private String currentUser = "";
    private final JLabel userLabel = new JLabel();

    private final JButton logOut;
    private final JButton upload;

    // Mode toggle buttons (My Plants / Discover)
    private final JToggleButton myPlantsButton;
    private final JToggleButton discoverButton;

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.mainViewModel.addPropertyChangeListener(this);

        this.setLayout(new OverlayLayout(this));
        this.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Header section with title and user label
        final JLabel title = new JLabel("Gallery");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0x123456));

        final JPanel header = ViewComponentFactory.buildVerticalPanel(List.of(title, userLabel));
        header.setOpaque(false);

        // Set up SwapGallery functionality (mode switching)
        SwapGalleryOutputBoundary presenter = new SwapGalleryPresenter(mainViewModel);
        SwapGalleryInputBoundary interactor = new SwapGalleryInteractor(presenter, mainViewModel);
        this.swapGalleryController = new SwapGalleryController(interactor);

        upload = ViewComponentFactory.buildButton("Upload");
        logOut = ViewComponentFactory.buildButton("Log Out");
        myPlantsButton = ViewComponentFactory.buildToggleButton("My Plants");
        discoverButton = ViewComponentFactory.buildToggleButton("Discover");

        upload.addActionListener(evt -> overlayUploadView());
        logOut.addActionListener(e -> logoutController.execute(mainViewModel.getState().getUsername()));
        myPlantsButton.addActionListener(e -> swapGalleryController.switchMode(MainState.Mode.MY_PLANTS));
        discoverButton.addActionListener(e -> swapGalleryController.switchMode(MainState.Mode.DISCOVER));

        ViewComponentFactory.setButtonSize(myPlantsButton, buttonSize);
        ViewComponentFactory.setButtonSize(logOut, buttonSize);
        ViewComponentFactory.setButtonSize(upload, buttonSize);
        ViewComponentFactory.setButtonSize(discoverButton, buttonSize);

        // Make the logout button red
        logOut.setForeground(Color.RED);

        // Make the panel on the left of the screen (Upload, mode toggle, and Log Out)
        JPanel spacer1 = new JPanel();
        spacer1.setOpaque(false);
        spacer1.setPreferredSize(new Dimension(10, 160));

        JPanel spacer2 = new JPanel();
        spacer2.setOpaque(false);
        spacer2.setPreferredSize(new Dimension(10, 20));

        final JPanel actionPanel = ViewComponentFactory.buildVerticalPanel(List.of(title, header, spacer2, upload, myPlantsButton, discoverButton, spacer1, logOut));

        // Gallery panel (to display gallery contents)
        final JPanel gallery = makeGallery();

        // Combine buttons and gallery in the body panel
        final JPanel body = ViewComponentFactory.buildHorizontalPanel(List.of(actionPanel, gallery));

        // Add header and body to the main panel
        mainPanel.add(ViewComponentFactory.buildHorizontalPanel(List.of(actionPanel, gallery)));

        this.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private JPanel makeGallery() {
        JPanel gallery = new JPanel();
        gallery.setPreferredSize(new Dimension(840, 700)); // Set gallery size
        gallery.setBorder(BorderFactory.createLineBorder(Color.black)); // Temporary border for visibility
        return gallery;
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

    public String getViewName() {
        return viewName;
    }

    private void updateModeUI(MainState.Mode mode) {
        if (mode == MainState.Mode.DISCOVER) {
            // Update UI for "Discover" mode
            myPlantsButton.setSelected(false);
            discoverButton.setSelected(true);
        } else if (mode == MainState.Mode.MY_PLANTS) {
            // Update UI for "My Plants" mode
            myPlantsButton.setSelected(true);
            discoverButton.setSelected(false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final MainState state = (MainState) evt.getNewValue();
            currentUser = state.getUsername();
            userLabel.setText("Hello " + this.currentUser + "!");

            // Handle mode change
            updateModeUI(state.getCurrentMode());
        }
    }
}