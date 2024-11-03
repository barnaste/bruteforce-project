package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadResultViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UploadResultView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload result";

    private final UploadResultViewModel viewModel;
    private UploadController controller;

    private final JButton returnBtn;
    private final JButton saveBtn;
    private final JButton discardBtn;

    public UploadResultView(UploadResultViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadResultViewModel.BACKGROUND_COLOR));

        this.returnBtn = new JButton(UploadResultViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(true);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);

        returnBtn.addActionListener((e) -> controller.switchToSelectView());

        this.saveBtn = new JButton(UploadResultViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setBorderPainted(true);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);

        saveBtn.addActionListener((e) -> controller.save());

        this.discardBtn = new JButton(UploadResultViewModel.DISCARD_BUTTON_LABEL);
        discardBtn.setBorderPainted(true);
        discardBtn.setContentAreaFilled(false);
        discardBtn.setFocusPainted(false);

        discardBtn.addActionListener((e) -> controller.escape());

        // position each component nicely within the view area using a GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(returnBtn, constraints);

        // TODO: these belong in an actionPanel together for better styling
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(saveBtn, constraints);

        constraints.gridy = 2;
        this.add(discardBtn, constraints);
    }

    public void setUploadController(UploadController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // final UploadState state = (UploadState) evt.getSource();
        // TODO: fill this in
    }
}