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
    /**
     * Creates and sets up the view for editing plant information, including initializing
     * the interactor, presenter, and controller. It also adds the view to the provided panel.
     *
     * @param plant The `Plant` object to be edited.
     * @param panel The `JPanel` to which the plant info edit view will be added.
     * @param escapeMap A `Runnable` for handling escape actions, such as canceling the edit.
     * @param mainViewModel The `MainViewModel` used for managing the application's state during the edit process.
     */
    public static void createEditPlantPanel(Plant plant, JPanel panel, Runnable escapeMap, MainViewModel mainViewModel) {
        // Get image access to retrieve the plant's image
        ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        // Create and add the plant info edit view to the panel
        PlantInfoEditView view = new PlantInfoEditView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        // Initialize the presenter for editing plant info
        UserPlantInfoEditPresenter editPlantPresenter = new UserPlantInfoEditPresenter(mainViewModel);

        // Set up the interactor to handle plant info editing logic
        UserPlantInfoEditInteractor interactor = new UserPlantInfoEditInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance(),
                editPlantPresenter
        );
        interactor.setPlant(plant);  // Set the plant to be edited
        interactor.setEscapeMap(escapeMap);  // Set escape handling

        // Create the controller and bind it to the view
        UserPlantInfoEditController controller = new UserPlantInfoEditController(interactor);
        view.setController(controller);  // Link the controller to the view
    }
}
