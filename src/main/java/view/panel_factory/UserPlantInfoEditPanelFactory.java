package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditController;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditPresenter;
import interface_adapter.main.MainViewModel;
import use_case.ImageDataAccessInterface;
import use_case.user_plant_info_edit.UserPlantInfoEditInteractor;
import view.plant_view.PlantInfoEditView;

import javax.swing.*;

public class UserPlantInfoEditPanelFactory {
    public static void createEditPlantPanel(Plant plant, JPanel panel, Runnable escapeMap, MainViewModel mainViewModel) {
        ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        PlantInfoEditView view = new PlantInfoEditView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        UserPlantInfoEditPresenter editPlantPresenter = new UserPlantInfoEditPresenter(mainViewModel);

        UserPlantInfoEditInteractor interactor = new UserPlantInfoEditInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance(),
                editPlantPresenter
        );
        interactor.setPlant(plant);
        interactor.setEscapeMap(escapeMap);

        UserPlantInfoEditController controller = new UserPlantInfoEditController(interactor);
        view.setController(controller);
    }
}
