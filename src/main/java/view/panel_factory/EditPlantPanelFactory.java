package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.edit_plant.EditPlantController;
import use_case.ImageDataAccessInterface;
import use_case.edit_plant.EditPlantInteractor;
import view.plant_view.EditPlantView;

import javax.swing.*;

public class EditPlantPanelFactory {
    public static void createEditPlantPanel(Plant plant, JPanel panel, Runnable escapeMap) {
        ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        EditPlantView view = new EditPlantView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        EditPlantInteractor interactor = new EditPlantInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance()
        );
        interactor.setPlant(plant);
        interactor.setEscapeMap(escapeMap);

        EditPlantController controller = new EditPlantController(interactor);
        view.setController(controller);
    }
}
