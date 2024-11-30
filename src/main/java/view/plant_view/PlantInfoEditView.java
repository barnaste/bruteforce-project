package view.plant_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Plant;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditController;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditViewModel;
import view.ViewComponentFactory;

/**
 * The View for the editable plant view use case.
 */
public class PlantInfoEditView extends PlantView {
    private static final Dimension BUTTON_DIMENSION = new Dimension(100, 30);
    private static final int INSETS_THICKNESS = 20;
    private UserPlantInfoEditController controller;

    public PlantInfoEditView(Plant plant, BufferedImage image) {
        this.setImage(image);
        this.getScientificNameLabel().setText(plant.getScientificName());
        this.getNotesField().setText(plant.getComments());
        this.getTogglePublic().setSelected(plant.getIsPublic());
        this.getFamilyLabel().setText(plant.getFamily());

        this.getNameLabel().setText(plant.getSpecies());
        this.getLikesLabel().setText("\uD83D\uDC4D " + plant.getNumOfLikes());
    }

    @Override
    protected JPanel createTopPanel() {
        final JPanel topPanel = super.createTopPanel();
        final JButton returnBtn = ViewComponentFactory.buildButton(UserPlantInfoEditViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener(evt -> controller.escape());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    /**
     * Create the panel within which user action buttons save and delete are displayed.
     * @return a reference to the created panel
     */
    @Override
    protected JPanel createActionPanel() {
        final JPanel actionPanel = super.createActionPanel();

        final JButton saveBtn = ViewComponentFactory.buildButton(UserPlantInfoEditViewModel.SAVE_BUTTON_LABEL);
        saveBtn.setPreferredSize(BUTTON_DIMENSION);

        saveBtn.addActionListener(evt -> {
            controller.savePlant(
                    getNotesField().getText(),
                    getTogglePublic().isSelected()
            );
        });
        // NOTE: we negate privacyToggle as the controller needs to know if the image is public,
        //  not if the image is private.
        final JButton discardBtn = ViewComponentFactory.buildButton(UserPlantInfoEditViewModel.DELETE_BUTTON_LABEL);
        discardBtn.setPreferredSize(BUTTON_DIMENSION);

        discardBtn.addActionListener(evt -> controller.deletePlant());

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(INSETS_THICKNESS, INSETS_THICKNESS, INSETS_THICKNESS, INSETS_THICKNESS);
        actionPanel.add(discardBtn, constraints);

        constraints.gridx = 1;
        actionPanel.add(saveBtn, constraints);

        return actionPanel;
    }

    public void setController(UserPlantInfoEditController controller) {
        this.controller = controller;
    }
}
