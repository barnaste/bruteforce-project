package use_case.upload;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: note to self, if you dynamically add or remove a component, you must revalidate and repaint!

class MainViewDemo {
    private final int OVERLAY_COLOR = 0x30a7c080;
    private final int BACKGROUND_COLOR = 0xffffffff;

    private final int DISPLAY_WIDTH = 800;
    private final int DISPLAY_HEIGHT = 600;

    // class attributes
    private final String viewName = "Main View Demo";
    private final JLayeredPane mainView;
    private final JButton upload;

    public MainViewDemo() {
        // TODO: this is just a demo setup so that the use case can be launched --
        //  it is expected that the MainView class will overwrite everything here
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
        mainView.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        // attach use case to the button
        upload.addActionListener(e -> {
            // TODO: this is where the use case is called
            createPopupPanel();
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

    private void createPopupPanel() {
        // override painting functionality so that our overlay is semi-transparent
        JPanel popupPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(OVERLAY_COLOR, true)); // overlay color with transparency
                g.fillRect(0, 0, getWidth(), getHeight()); // fill the panel area with color
            }
        };
        popupPanel.setOpaque(false);

        JLabel label = new JLabel("Please Upload an Image");
        popupPanel.add(label, BorderLayout.CENTER);

        JButton popupCloseButton = new JButton("Close");
        popupCloseButton.setBorderPainted(true);
        popupCloseButton.setContentAreaFilled(false);
        popupCloseButton.setFocusPainted(false);
        popupPanel.add(popupCloseButton, BorderLayout.SOUTH);

        popupCloseButton.addActionListener(e -> {
            upload.setEnabled(true);
            mainView.remove(popupPanel);
            mainView.revalidate();
            mainView.repaint();
        });

        mainView.add(popupPanel, JLayeredPane.PALETTE_LAYER);
        mainView.revalidate();
        mainView.repaint();
    }

    public static void main(String[] args) {
        new MainViewDemo();
    }
}