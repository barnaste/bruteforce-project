package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.public_plant_info.PublicPlantInfoController;
import use_case.ImageDataAccessInterface;
import use_case.public_plant_info.PublicPlantInfoInteractor;
import view.plant_view.PublicPlantInfoView;

import javax.swing.*;

public class PublicPlantInfoPanelFactory {
    public static void createPublicPlantPanel(Plant plant, JPanel panel, Runnable escapeMap) {
        ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        PublicPlantInfoView view = new PublicPlantInfoView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        PublicPlantInfoInteractor interactor = new PublicPlantInfoInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance()
        );
        interactor.setEscapeMap(escapeMap);

        PublicPlantInfoController controller = new PublicPlantInfoController(interactor);
        view.setController(controller);
    }
}
