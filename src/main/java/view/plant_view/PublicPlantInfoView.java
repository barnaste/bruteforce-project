package view.plant_view;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Plant;
import interface_adapter.public_plant_info.PublicPlantInfoController;
import interface_adapter.public_plant_info.PublicPlantInfoViewModel;
import view.ViewComponentFactory;

/**
 * The View for the display of publicly visible plants use case.
 */
public class PublicPlantInfoView extends PlantView {

    private PublicPlantInfoController controller;

    public PublicPlantInfoView(Plant plant, BufferedImage image) {
        this.setImage(image);
        this.getScientificNameLabel().setText(plant.getScientificName());
        this.getNotesField().setText(plant.getComments());
        this.getNotesField().setEditable(false);
        this.getFamilyLabel().setText(plant.getFamily());
        this.getNameLabel().setText(plant.getSpecies());

        getOwnerLabel().setVisible(true);
        getLikesLabel().setVisible(true);
        this.getOwnerLabel().setText("Posted by " + plant.getOwner());
        this.getLikesLabel().setText("\uD83D\uDC4D " + plant.getNumOfLikes());
    }

    @Override
    protected JPanel createTopPanel() {
        final JPanel topPanel = super.createTopPanel();
        final JButton returnBtn = ViewComponentFactory.buildButton(PublicPlantInfoViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.escape());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    @Override
    protected JPanel createContentPanel() {
        final JPanel contentPanel = super.createContentPanel();
        contentPanel.remove(this.getTogglePublic());
        return contentPanel;
    }

    public void setController(PublicPlantInfoController controller) {
        this.controller = controller;
    }
}
