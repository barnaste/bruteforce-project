    package view;

    import java.awt.*;
    import java.beans.PropertyChangeEvent;
    import java.beans.PropertyChangeListener;
    import java.util.List;
    import java.util.function.Consumer;

    import javax.swing.*;

    import data_access.MongoImageDataAccessObject;
    import data_access.MongoPlantDataAccessObject;
    import interface_adapter.ViewManagerModel;
    import interface_adapter.load_public_gallery.PublicGalleryController;
    import interface_adapter.load_public_gallery.PublicGalleryPresenter;
    import interface_adapter.load_public_gallery.PublicGalleryViewModel;
    import interface_adapter.logout.LogoutController;
    import interface_adapter.main.MainState;
    import interface_adapter.main.MainViewModel;
    import interface_adapter.mode_switch.ModeSwitchController;
    import interface_adapter.mode_switch.ModeSwitchState;
    import interface_adapter.mode_switch.ModeSwitchViewModel;
    import interface_adapter.upload.UploadController;
    import interface_adapter.upload.UploadPresenter;
    import interface_adapter.upload.confirm.UploadConfirmViewModel;
    import interface_adapter.upload.result.UploadResultViewModel;
    import interface_adapter.upload.select.UploadSelectViewModel;
    import use_case.load_public_gallery.PublicGalleryInputBoundary;
    import use_case.load_public_gallery.PublicGalleryInteractor;
    import use_case.load_public_gallery.PublicGalleryOutputBoundary;
    import use_case.upload.UploadInputBoundary;
    import use_case.upload.UploadInteractor;
    import use_case.upload.UploadOutputBoundary;
    import view.gallery.PublicGalleryView;
    import view.upload.UploadConfirmView;
    import view.upload.UploadResultView;
    import view.upload.UploadSelectView;

    /**
     * The Main View, for when the user is logged into the program.
     */
    public class MainView extends JLayeredPane implements PropertyChangeListener {
        private static final int OVERLAY_COLOR = 0x40829181;
        private static final int DISPLAY_WIDTH = 1080;
        private static final int DISPLAY_HEIGHT = 720;
        final Dimension buttonSize = new Dimension(200, 50);

        private final String viewName = "main view";
        private final MainViewModel mainViewModel;
        private final PublicGalleryViewModel publicGalleryViewModel;
        private final ModeSwitchViewModel modeSwitchViewModel;
        private PublicGalleryView publicGalleryView;

        private LogoutController logoutController;
        private ModeSwitchController modeSwitchController;

        private String currentUser = "";
        private String currentGalleryMode = "";
        private final JPanel currentGalleryPanel;
        private final JLabel userLabel = new JLabel();
        private final JLabel title = new JLabel();

        private final JButton logOut;
        private final JButton upload;
        private final JButton myPlantsButton;
        private final JButton discoverButton;

        private JButton currentGallery;

        public MainView(MainViewModel mainViewModel, PublicGalleryViewModel publicGalleryViewModel, ModeSwitchViewModel modeSwitchViewModel) {
            this.mainViewModel = mainViewModel;
            this.mainViewModel.addPropertyChangeListener(this);

            this.modeSwitchViewModel = modeSwitchViewModel;
            this.modeSwitchViewModel.addPropertyChangeListener(this);

            this.publicGalleryViewModel = publicGalleryViewModel;

            setUpPublicGallery();

            this.setLayout(new OverlayLayout(this));
            this.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());

            currentGalleryMode = "My Plants Gallery";

            title.setText(currentGalleryMode);
            title.setFont(new Font("Arial", Font.BOLD, 18));
            title.setForeground(new Color(0x3C7339));

            final JPanel header = ViewComponentFactory.buildVerticalPanel(List.of(title, userLabel));
            header.setOpaque(false);

            upload = ViewComponentFactory.buildButton("Upload");
            logOut = ViewComponentFactory.buildButton("Log Out");
            myPlantsButton = ViewComponentFactory.buildButton("My Plants");
            discoverButton = ViewComponentFactory.buildButton("Discover");

            upload.addActionListener(evt -> overlayUploadView());
            logOut.addActionListener(e -> logoutController.execute(mainViewModel.getState().getUsername()));

            myPlantsButton.addActionListener(e -> modeSwitchController.switchMode());
            myPlantsButton.setEnabled(false);
            discoverButton.addActionListener(e -> modeSwitchController.switchMode());

            ViewComponentFactory.setButtonSize(myPlantsButton, buttonSize);
            ViewComponentFactory.setButtonSize(logOut, buttonSize);
            ViewComponentFactory.setButtonSize(upload, buttonSize);
            ViewComponentFactory.setButtonSize(discoverButton, buttonSize);

            // Make the logout button red
            logOut.setForeground(new Color(150, 32, 32));

            // Make the panel on the left of the screen (Upload, mode toggle, and Log Out)
            JPanel spacer1 = new JPanel();
            spacer1.setOpaque(false);
            spacer1.setPreferredSize(new Dimension(10, 160));

            JPanel spacer2 = new JPanel();
            spacer2.setOpaque(false);
            spacer2.setPreferredSize(new Dimension(10, 20));

            final JPanel actionPanel = ViewComponentFactory.buildVerticalPanel(List.of(title, header, spacer2, upload, myPlantsButton, discoverButton, spacer1, logOut));

            currentGalleryPanel = new JPanel();
            setMyPlantsPanel();

            mainPanel.add(ViewComponentFactory.buildHorizontalPanel(List.of(actionPanel, currentGalleryPanel)));

            this.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setUpModeSwitch() {}

        /**
         * Disable all interactions within the main view. That is, all components and subcomponents are
         * no longer active after this method is called.
         */
        private void disableInteraction() {
            // if the discover button is currently enabled, the user is currently in the discovery gallery
            currentGallery = !this.discoverButton.isEnabled() ? this.discoverButton : this.myPlantsButton;
            setComponentsEnabled(this, false);
        }

        /**
         * Enables all interactions within the main view. That is, all components and subcomponents are
         * restored after this method is called. The current gallery's corresponding button will be disabled
         * to disallow the user to "activate" an already active gallery.
         */
        private void enableInteraction() {
            setComponentsEnabled(this, true);
            currentGallery.setEnabled(false);
        }

        /**
         * Enables or disables all buttons within the input panel. This includes buttons
         * directly within the input panel and any buttons within components of the input
         * panel, recursively.
         * @param panel the panel to modify the enablement of buttons in
         * @param isEnabled whether buttons should be enabled or disabled
         */
        private void setComponentsEnabled(Container panel, boolean isEnabled) {
            for (Component cp : panel.getComponents()) {
                if (cp instanceof JPanel) {
                    setComponentsEnabled((JPanel) cp, isEnabled);
                }
                cp.setEnabled(isEnabled);
            }
        }

        private void setUpPublicGallery() {
            MongoPlantDataAccessObject plantDataAccessObject = new MongoPlantDataAccessObject();
            MongoImageDataAccessObject imageDataAccessObject = new MongoImageDataAccessObject();
            ViewManagerModel galleryManagerModel = new ViewManagerModel();

            // Set up the PublicGalleryPresenter and PublicGalleryInteractor
            PublicGalleryOutputBoundary galleryPresenter = new PublicGalleryPresenter(publicGalleryViewModel, galleryManagerModel);
            PublicGalleryInputBoundary publicGalleryInteractor = new PublicGalleryInteractor(plantDataAccessObject, galleryPresenter, imageDataAccessObject);

            // Initialize the PublicGalleryController and View
            PublicGalleryController publicGalleryController = new PublicGalleryController(publicGalleryInteractor);
            publicGalleryViewModel.firePropertyChanged();
            this.publicGalleryView = new PublicGalleryView(publicGalleryViewModel);
            publicGalleryView.setPublicGalleryController(publicGalleryController);

            // Load the first page by default
            publicGalleryController.loadPage(0);
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
            UploadInputBoundary uploadInteractor = new UploadInteractor(
                    uploadOutputBoundary,
                    new MongoImageDataAccessObject(),
                    new MongoPlantDataAccessObject(),
                    this.currentUser
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

        public void setModeSwitchController(ModeSwitchController modeSwitchController) {
            this.modeSwitchController = modeSwitchController;
        }

        public String getViewName() {
            return viewName;
        }

        private void updateModeUI(ModeSwitchState.Mode mode) {
            if (currentGalleryPanel != null)
                this.remove(currentGalleryPanel);
            if (mode == ModeSwitchState.Mode.DISCOVER) {
                // Update UI for "Discover" mode
                setDiscoverPanel();
                currentGalleryMode = "Discover Gallery";
                title.setText(currentGalleryMode);
                myPlantsButton.setEnabled(true);
                discoverButton.setEnabled(false);
            } else if (mode == ModeSwitchState.Mode.MY_PLANTS) {
                // Update UI for "My Plants" mode
                setMyPlantsPanel();
                currentGalleryMode = "My Plants Gallery";
                title.setText(currentGalleryMode);
                myPlantsButton.setEnabled(false);
                discoverButton.setEnabled(true);
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("logged_in")) {
                final MainState state = (MainState) evt.getNewValue();
                currentUser = state.getUsername();
                userLabel.setText("Hello " + this.currentUser + "!");
            } if (evt.getPropertyName().equals("mode_switch")) {
                final ModeSwitchState modeSwitchState = (ModeSwitchState) evt.getNewValue();
                updateModeUI(modeSwitchState.getCurrentMode());
            }
        }

        // Create "My Plants" panel
        private void setMyPlantsPanel() {
            currentGalleryPanel.removeAll();

            currentGalleryPanel.setPreferredSize(new Dimension(840, 700));

            // currentGalleryPanel.add(userGalleryView, userGalleryView.getViewName());

            currentGalleryPanel.revalidate();
            currentGalleryPanel.repaint();
        }

        // Create "Discover" panel
        private void setDiscoverPanel() {
            currentGalleryPanel.removeAll();

            currentGalleryPanel.setPreferredSize(new Dimension(840, 700));

            currentGalleryPanel.add(publicGalleryView, publicGalleryView.getViewName());

            currentGalleryPanel.revalidate();
            currentGalleryPanel.repaint();
        }
    }