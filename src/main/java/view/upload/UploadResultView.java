package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.result.UploadResultState;
import interface_adapter.upload.result.UploadResultViewModel;
import view.ViewComponentFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class UploadResultView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload result";

    private UploadController controller;

    private final JLabel nameLabel = new JLabel();
    private final JLabel scientificNameLabel = new JLabel();
    private final JLabel familyLabel = new JLabel();
    private final JLabel certaintyLabel = new JLabel();
    private final JTextArea notesField = new JTextArea();

    private String imagePath = "";
    private BufferedImage image;

    public UploadResultView(UploadResultViewModel viewModel) {
        viewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadResultViewModel.TRANSPARENT, true));

        // position each component nicely within the view area using a GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        this.add(createTopPanel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(createImagePanel(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.ipady = 20;
        constraints.weighty = 1;
        this.add(createContentPanel(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipadx = 20;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.SOUTH;
        this.add(createActionPanel(), constraints);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        contentPanel.setLayout(layout);
        contentPanel.setBackground(new Color(UploadResultViewModel.CONTENT_PANEL_COLOR, true));

        Font font = nameLabel.getFont();
        nameLabel.setFont(font.deriveFont(Font.BOLD).deriveFont(20f));
        layout.putConstraint(SpringLayout.WEST, nameLabel, 20, SpringLayout.WEST, contentPanel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 20, SpringLayout.NORTH, contentPanel);
        contentPanel.add(nameLabel);

        scientificNameLabel.setFont(font.deriveFont(Font.ITALIC).deriveFont(16f));
        layout.putConstraint(SpringLayout.WEST, scientificNameLabel, 20, SpringLayout.WEST, contentPanel);
        layout.putConstraint(SpringLayout.NORTH, scientificNameLabel, 5, SpringLayout.SOUTH, nameLabel);
        contentPanel.add(scientificNameLabel);

        familyLabel.setFont(font.deriveFont(Font.PLAIN).deriveFont(14f));
        layout.putConstraint(SpringLayout.WEST, familyLabel, 20, SpringLayout.WEST, contentPanel);
        layout.putConstraint(SpringLayout.NORTH, familyLabel, 5, SpringLayout.SOUTH, scientificNameLabel);
        contentPanel.add(familyLabel);

        certaintyLabel.setFont(font.deriveFont(Font.PLAIN).deriveFont(14f));
        layout.putConstraint(SpringLayout.WEST, certaintyLabel, 20, SpringLayout.WEST, contentPanel);
        layout.putConstraint(SpringLayout.NORTH, certaintyLabel, 15, SpringLayout.SOUTH, familyLabel);
        contentPanel.add(certaintyLabel);

        notesField.setRows(10);
        notesField.setFont(font.deriveFont(Font.PLAIN).deriveFont(12f));
        notesField.setLineWrap(true);
        notesField.setWrapStyleWord(true);
        notesField.setMargin(new Insets(10, 10, 10, 10));
        notesField.setBackground(new Color(UploadResultViewModel.CONTENT_PANEL_COLOR, true));
        JScrollPane notesScrollPane = new JScrollPane(notesField);

        layout.putConstraint(SpringLayout.WEST, notesScrollPane, 20, SpringLayout.WEST, contentPanel);
        layout.putConstraint(SpringLayout.EAST, notesScrollPane, -20, SpringLayout.EAST, contentPanel);
        layout.putConstraint(SpringLayout.NORTH, notesScrollPane, 35, SpringLayout.SOUTH, certaintyLabel);
        contentPanel.add(notesScrollPane);

        return contentPanel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(UploadResultViewModel.TOP_PANEL_COLOR, true));
        topPanel.setPreferredSize(new Dimension(
                UploadResultViewModel.PANEL_WIDTH,
                UploadResultViewModel.TOP_PANEL_HEIGHT
        ));

        JButton returnBtn = ViewComponentFactory.buildButton(UploadResultViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.switchToSelectView());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
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
                    g.drawImage(thumbnail, 0, 0,
                            UploadResultViewModel.IMAGE_WIDTH,
                            UploadResultViewModel.IMAGE_HEIGHT,
                            this
                    );
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(
                UploadResultViewModel.IMAGE_WIDTH,
                UploadResultViewModel.IMAGE_HEIGHT
        ));
        return imagePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridBagLayout());
        actionPanel.setBackground(new Color(UploadResultViewModel.ACTION_PANEL_COLOR));

        JButton saveBtn = ViewComponentFactory.buildButton(UploadResultViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setPreferredSize(new Dimension(100, 30));

        saveBtn.addActionListener((e) -> controller.saveUpload(imagePath));

        JButton discardBtn = ViewComponentFactory.buildButton(UploadResultViewModel.DISCARD_BUTTON_LABEL);
        discardBtn.setPreferredSize(new Dimension(100, 30));

        discardBtn.addActionListener((e) -> controller.escape());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        actionPanel.add(discardBtn, constraints);

        constraints.gridx = 1;
        actionPanel.add(saveBtn, constraints);
        return actionPanel;
    }

    public void setController(UploadController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    private void setFields(UploadResultState state) {
        this.imagePath = state.getImagePath();
        this.setImage(imagePath);
        this.nameLabel.setText(state.getName());
        this.scientificNameLabel.setText(state.getScientificName());
        this.familyLabel.setText(state.getFamily());

        DecimalFormat roundingFormat = new DecimalFormat("###.##");
        this.certaintyLabel.setText(Double.valueOf(roundingFormat.format(state.getCertainty() * 100)) +
                "% certainty");

        this.notesField.setText("My notes...");
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
         final UploadResultState state = (UploadResultState) evt.getNewValue();
         this.setFields(state);
    }
}