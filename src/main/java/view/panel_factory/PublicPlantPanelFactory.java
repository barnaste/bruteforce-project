package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.public_plant_view.PublicPlantViewController;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.publicplant.PublicPlantInteractor;
import view.plant_view.PublicPlantView;

import javax.swing.*;

public class PublicPlantPanelFactory {
    public static void createPublicPlantPanel(Plant plant, JPanel panel, Runnable escapeMap) {
        ImageDataAccessInterface imageAccess = new MongoImageDataAccessObject();
        PlantDataAccessInterface plantAccess = new MongoPlantDataAccessObject();

        PublicPlantView view = new PublicPlantView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view, imageAccess);

        PublicPlantInteractor interactor = new PublicPlantInteractor(
                imageAccess,
                plantAccess
        );
        interactor.setPlant(plant);
        interactor.setEscapeMap(escapeMap);

        PublicPlantViewController controller = new PublicPlantViewController(interactor);
        view.setController(controller);
    }
}
