package view.plant_view;

import entity.Plant;
import interface_adapter.edit_plant.EditPlantViewModel;
import view.ViewComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditPlantView extends PlantView implements PropertyChangeListener {
    private final String viewName = "plant editor";

    // TODO: add a controller

    public EditPlantView(Plant plant) {

    }

    protected JPanel createTopPanel() {
        JPanel topPanel = super.createTopPanel();
        JButton returnBtn = ViewComponentFactory.buildButton(EditPlantViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        // TODO: add a controller action
        // returnBtn.addActionListener((e) -> controller.switchToSelectView());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    protected JPanel createActionPanel() {
        JPanel actionPanel = super.createActionPanel();

        JButton saveBtn = ViewComponentFactory.buildButton(EditPlantViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setPreferredSize(new Dimension(100, 30));

        // TODO: add a controller action
//        saveBtn.addActionListener((e) -> controller.saveUpload(
//                getImage(),
//                getScientificNameLabel().getText(),
//                getNotesField().getText(),
//                getTogglePublic().isSelected()
//        ));
        // NOTE: we negate privacyToggle as the controller needs to know if the image is public,
        //  not if the image is private.
        JButton discardBtn = ViewComponentFactory.buildButton(EditPlantViewModel.DELETE_BUTTON_LABEL);
        discardBtn.setPreferredSize(new Dimension(100, 30));

        // TODO: add a controller action
//        discardBtn.addActionListener((e) -> controller.escape());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        actionPanel.add(discardBtn, constraints);

        constraints.gridx = 1;
        actionPanel.add(saveBtn, constraints);

        return actionPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
