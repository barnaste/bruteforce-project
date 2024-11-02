package use_case.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadPresenter;
import interface_adapter.upload.UploadResultViewModel;
import interface_adapter.upload.UploadSelectorViewModel;
import view.upload.UploadResultView;
import view.upload.UploadSelectorView;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

// TODO: note to self, if you dynamically add or remove a component, you must revalidate and repaint!

class MainViewDemo {
    private final int OVERLAY_COLOR = 0x30a7c080;
    private final int BACKGROUND_COLOR = 0xfffffbef;

    private final int DISPLAY_WIDTH = 1200;
    private final int DISPLAY_HEIGHT = 750;

    // class attributes
    private final String viewName = "Main View Demo";
    private final JLayeredPane mainView;
    private final JButton upload;

    public MainViewDemo() {
        // TODO: this is just a demo setup so that the use case can be launched --
        //  it is expected that the MainView class will overwrite what is found here.
        //  (if looking for inspiration this is not a bad place to start though)
        mainView = new JLayeredPane();
        mainView.setLayout(new OverlayLayout(mainView));

        // set up main view -- a simple canvas with an "Upload" button
        upload = new JButton("Upload");
        upload.setBorderPainted(true);
        upload.setContentAreaFilled(false);
        upload.setFocusPainted(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(upload, new GridBagConstraints());
        mainPanel.setBackground(new Color(BACKGROUND_COLOR, true));
        mainView.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        // attach use case to the button
        upload.addActionListener(e -> {
            // NOTICE: this is where the use case is called.
            //  Once the main display and CA classes are implemented, the call path will be along the lines of
            //  MainController -> MainInteractor -> MainPresenter.overlayUploadView(), which signals a property
            //  change to a class of the developer's choice.
            //  Here, we cut straight to the overlayUploadView method.
            overlayUploadView();
            upload.setEnabled(false);
        });

        // create display frame
        JFrame frame = new JFrame(viewName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        frame.add(mainView);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void overlayUploadView() {
        // TIP: this may need to be deferred to a factory method of sorts
        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        UploadSelectorViewModel selectorViewModel = new UploadSelectorViewModel();
        UploadSelectorView selectorView = new UploadSelectorView(selectorViewModel);
        cardPanel.add(selectorView, selectorView.getViewName());
        // NOTE: because we're adding selectorView first, it will be the initial display in the cardPanel

        UploadResultViewModel resultViewModel = new UploadResultViewModel();
        UploadResultView resultView = new UploadResultView(resultViewModel);
        cardPanel.add(resultView, resultView.getViewName());

        UploadOutputBoundary uploadOutputBoundary = new UploadPresenter(selectorViewModel, resultViewModel);
        UploadInputBoundary uploadInteractor = new UploadInteractor(uploadOutputBoundary);
        UploadController controller = new UploadController(uploadInteractor);

        selectorView.setUploadController(controller);
        resultView.setUploadController(controller);
        // create an overlay with the created cardPanel as the popup
        overlay(cardPanel, uploadInteractor::setEscapeMap);
    }

    public void overlay(JPanel overlayPanel, Consumer<Runnable> setOverlayEscape) {
        //  1. accept system to be displayed on top of main view
        //  2. overlay default (semitransparent layer)
        //  3. display the system
        //  4. feed the system a method to kill itself (exit signal)

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
            upload.setEnabled(true);

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