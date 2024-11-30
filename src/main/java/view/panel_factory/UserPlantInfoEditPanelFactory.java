package view.panel_factory;

import javax.swing.JPanel;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.main.MainViewModel;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditController;
import interface_adapter.user_plant_info_edit.UserPlantInfoEditPresenter;
import use_case.ImageDataAccessInterface;
import use_case.user_plant_info_edit.UserPlantInfoEditInteractor;
import view.plant_view.PlantInfoEditView;

/**
 * The `UserPlantInfoEditPanelFactory` class is responsible for creating and setting up the
 * view for editing plant information. It initializes the necessary components including the
 * interactor, presenter, and controller, and then adds the plant information edit view to
 * the provided panel. The class abstracts the creation of the plant info edit panel and
 * links all the components needed for the plant editing process.
 *
 * <p>
 * The view enables users to modify the details of a plant such as its name, description,
 * and other attributes. It also supports escape functionality, allowing users to cancel
 * the edit operation if desired.
 *
 * <p>
 * The factory pattern is used here to ensure that all necessary dependencies are properly
 * wired together and encapsulated before the view is added to the parent panel.
 */
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
    public static void createEditPlantPanel(Plant plant, JPanel panel, Runnable escapeMap,
                                            MainViewModel mainViewModel) {
        // Get image access to retrieve the plant's image
        final ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        // Create and add the plant info edit view to the panel
        final PlantInfoEditView view = new PlantInfoEditView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        // Initialize the presenter for editing plant info
        final UserPlantInfoEditPresenter editPlantPresenter = new UserPlantInfoEditPresenter(mainViewModel);

        // Set up the interactor to handle plant info editing logic
        final UserPlantInfoEditInteractor interactor = new UserPlantInfoEditInteractor(
                imageAccess,
                MongoPlantDataAccessObject.getInstance(),
                editPlantPresenter
        );
        interactor.setPlant(plant);
        interactor.setEscapeMap(escapeMap);

        // Create the controller and bind it to the view
        final UserPlantInfoEditController controller = new UserPlantInfoEditController(interactor);
        view.setController(controller);
    }
}
