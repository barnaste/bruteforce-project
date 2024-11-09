package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.result.UploadResultState;
import interface_adapter.upload.result.UploadResultViewModel;
import view.ViewComponentFactory;
import view.plant_view.PlantView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UploadResultView extends PlantView implements PropertyChangeListener {
    private final String viewName = "upload result";

    private UploadController controller;

    public UploadResultView(UploadResultViewModel viewModel) {
        viewModel.addPropertyChangeListener(this);
    }

    protected JPanel createTopPanel() {
        JPanel topPanel = super.createTopPanel();
        JButton returnBtn = ViewComponentFactory.buildButton(UploadResultViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.switchToSelectView());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    protected JPanel createActionPanel() {
        JPanel actionPanel = super.createActionPanel();

        JButton saveBtn = ViewComponentFactory.buildButton(UploadResultViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setPreferredSize(new Dimension(100, 30));

        saveBtn.addActionListener((e) -> controller.saveUpload(
                getImage(),
                getScientificNameLabel().getText(),
                getNotesField().getText(),
                getTogglePublic().isSelected()
        ));
        // NOTE: we negate privacyToggle as the controller needs to know if the image is public,
        //  not if the image is private.
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
        this.setImage(ViewComponentFactory.buildCroppedImage(state.getImagePath()));
        this.getNameLabel().setText(state.getName());
        this.getScientificNameLabel().setText(state.getScientificName());
        this.getFamilyLabel().setText(state.getFamily());

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat roundingFormat = new DecimalFormat("###.##", symbols);
        this.certaintyLabel.setText(Double.valueOf(roundingFormat.format(state.getCertainty() * 100)) +
                "% certainty");

        this.getNotesField().setText("My notes...");

        this.getTogglePublic().setSelected(false);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
         final UploadResultState state = (UploadResultState) evt.getNewValue();
         this.setFields(state);
    }
}