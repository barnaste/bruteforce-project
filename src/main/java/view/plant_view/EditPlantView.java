package view.plant_view;

import entity.Plant;
import interface_adapter.edit_plant.EditPlantController;
import interface_adapter.edit_plant.EditPlantViewModel;
import view.ViewComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditPlantView extends PlantView implements PropertyChangeListener {
    private EditPlantController controller;

    public EditPlantView(Plant plant, BufferedImage image) {
        this.setImage(image);
        this.getScientificNameLabel().setText(plant.getScientificName());
        this.getNotesField().setText(plant.getComments());
        this.getTogglePublic().setSelected(plant.getIsPublic());
        this.getFamilyLabel().setText(plant.getFamily());

        this.getNameLabel().setText(plant.getSpecies());
        this.getLikesLabel().setText("\uD83D\uDC4D " + plant.getNumOfLikes());
    }

    protected JPanel createTopPanel() {
        JPanel topPanel = super.createTopPanel();
        JButton returnBtn = ViewComponentFactory.buildButton(EditPlantViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.escape());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    protected JPanel createActionPanel() {
        JPanel actionPanel = super.createActionPanel();

        JButton saveBtn = ViewComponentFactory.buildButton(EditPlantViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setPreferredSize(new Dimension(100, 30));

        saveBtn.addActionListener((e) -> controller.savePlant(
                getNotesField().getText(),
                getTogglePublic().isSelected()
        ));
        // NOTE: we negate privacyToggle as the controller needs to know if the image is public,
        //  not if the image is private.
        JButton discardBtn = ViewComponentFactory.buildButton(EditPlantViewModel.DELETE_BUTTON_LABEL);
        discardBtn.setPreferredSize(new Dimension(100, 30));

        discardBtn.addActionListener((e) -> controller.deletePlant());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        actionPanel.add(discardBtn, constraints);

        constraints.gridx = 1;
        actionPanel.add(saveBtn, constraints);

        return actionPanel;
    }

    public void setController(EditPlantController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
