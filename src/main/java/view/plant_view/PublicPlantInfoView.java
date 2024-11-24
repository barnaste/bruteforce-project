package view.plant_view;

import entity.Plant;
import interface_adapter.public_plant_info.PublicPlantInfoController;
import interface_adapter.public_plant_info.PublicPlantInfoViewModel;
import view.ViewComponentFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PublicPlantInfoView extends PlantView implements PropertyChangeListener {
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

    protected JPanel createTopPanel() {
        JPanel topPanel = super.createTopPanel();
        JButton returnBtn = ViewComponentFactory.buildButton(PublicPlantInfoViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.escape());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    protected JPanel createContentPanel() {
        JPanel contentPanel = super.createContentPanel();
        contentPanel.remove(this.getTogglePublic());
        return contentPanel;
    }

    public void setController(PublicPlantInfoController controller) {
        this.controller = controller;
    }

    /**
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

}
