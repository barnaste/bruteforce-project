package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.edit_plant.UserPlantViewEditController;
import interface_adapter.edit_plant.UserPlantViewEditPresenter;
import interface_adapter.main.MainViewModel;
import use_case.ImageDataAccessInterface;
import use_case.user_plant_view_edit.UserPlantViewEditInteractor;
import view.plant_view.EditPlantView;

import javax.swing.*;

public class UserPlantViewEditPanelFactory {
    public static void createEditPlantPanel(Plant plant, JPanel panel, Runnable escapeMap, MainViewModel mainViewModel) {
        ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        EditPlantView view = new EditPlantView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        UserPlantViewEditPresenter editPlantPresenter = new UserPlantViewEditPresenter(mainViewModel);

        UserPlantViewEditInteractor interactor = new UserPlantViewEditInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance(),
                editPlantPresenter
        );
        interactor.setPlant(plant);
        interactor.setEscapeMap(escapeMap);

        UserPlantViewEditController controller = new UserPlantViewEditController(interactor);
        view.setController(controller);
    }
}
