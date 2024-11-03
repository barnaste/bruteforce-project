package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadState;
import interface_adapter.upload.UploadConfirmViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class UploadConfirmView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload confirm";

    private final UploadConfirmViewModel viewModel;
    private UploadController controller;

    private String imagePath = "";
    private BufferedImage image;
    private final int IMAGE_WIDTH = 500;

    private final JButton confirmBtn;
    private final JButton returnBtn;

    public UploadConfirmView(UploadConfirmViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadConfirmViewModel.BACKGROUND_COLOR, true));

        this.returnBtn = new JButton(UploadConfirmViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(true);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);

        returnBtn.addActionListener((e) -> controller.switchToSelectView() );

        this.confirmBtn = new JButton(UploadConfirmViewModel.CONFIRM_BUTTON_LABEL);
        confirmBtn.setBorderPainted(true);
        confirmBtn.setContentAreaFilled(false);
        confirmBtn.setFocusPainted(false);

        confirmBtn.addActionListener((e) -> controller.switchToResultView(this.imagePath) );

        // set up an image with an image that can change at runtime
        JPanel imagePanel = createImagePanel();

        // position each component nicely within the view area using GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(returnBtn, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        this.add(confirmBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(imagePanel, constraints);
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    int thumbWidth = Math.min(image.getWidth(), image.getHeight());
                    BufferedImage thumbnail = image.getSubimage(
                            (image.getWidth() - thumbWidth) / 2,
                            (image.getHeight() - thumbWidth) / 2,
                            thumbWidth, thumbWidth);
                    g.drawImage(thumbnail, 0, 0, IMAGE_WIDTH, IMAGE_WIDTH, this);
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_WIDTH));
        return imagePanel;
    }

    public void setController(UploadController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    private void setFields(UploadState state) {
        this.imagePath = state.getImagePath();
        this.setImage(imagePath);
    }

    private void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            this.revalidate();
            this.repaint();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final UploadState state = (UploadState) evt.getNewValue();
        this.setFields(state);
    }
}
