package view.plant_view;

import entity.Plant;
import interface_adapter.publicplantview.PublicPlantViewController;
import interface_adapter.publicplantview.PublicPlantViewModel;
import view.ViewComponentFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PublicPlantView extends PlantView implements PropertyChangeListener {
    private PublicPlantViewController controller;


    public PublicPlantView(Plant plant, BufferedImage image) {
        this.setImage(image);
        this.getScientificNameLabel().setText(plant.getScientificName());
        this.getNotesField().setText(plant.getComments());
        this.getNotesField().setEditable(false);
        this.getFamilyLabel().setText(plant.getFamily());
        this.getNameLabel().setText(plant.getSpecies());
        this.getOwnerLabel().setText("Posted by " + plant.getOwner());
    }

    protected JPanel createTopPanel() {
        JPanel topPanel = super.createTopPanel();
        JButton returnBtn = ViewComponentFactory.buildButton(PublicPlantViewModel.RETURN_BUTTON_LABEL);
        returnBtn.setBorderPainted(false);

        returnBtn.addActionListener((e) -> controller.escape());
        topPanel.add(returnBtn, BorderLayout.WEST);
        return topPanel;
    }

    protected JPanel createContentPanel() {

        JPanel contentPanel = super.createContentPanel();
        contentPanel.remove(this.getTogglePublic());
        getOwnerLabel().setVisible(true);
        return contentPanel;
    }




    public void setController(PublicPlantViewController controller) {
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
