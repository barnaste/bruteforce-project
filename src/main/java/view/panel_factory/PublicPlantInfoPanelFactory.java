package view.panel_factory;

import javax.swing.JPanel;

import data_access.MongoImageDataAccessObject;
import entity.Plant;
import interface_adapter.public_plant_info.PublicPlantInfoController;
import use_case.ImageDataAccessInterface;
import use_case.public_plant_info.PublicPlantInfoInteractor;
import view.plant_view.PublicPlantInfoView;

/**
 * Factory class responsible for creating and configuring a `PublicPlantInfoView` to display
 * detailed information about a specific plant. It sets up the interactor, controller, and escape functionality
 * for the view.
 */
public class PublicPlantInfoPanelFactory {
    /**
     * Creates and configures a `PublicPlantInfoView` to display the details of a given plant.
     * Sets up the interactor, controller, and escape functionality for the view.
     *
     * @param plant The `Plant` whose information (e.g., image, details) will be displayed.
     * @param panel The `JPanel` to which the `PublicPlantInfoView` will be added.
     * @param escapeMap A `Runnable` to handle escape/cancel actions (e.g., closing the view).
     */
    public static void createPublicPlantPanel(Plant plant, JPanel panel, Runnable escapeMap) {
        // Get image access and create the plant info view
        final ImageDataAccessInterface imageAccess = MongoImageDataAccessObject.getInstance();

        final PublicPlantInfoView view = new PublicPlantInfoView(plant, imageAccess.getImageFromID(plant.getImageID()));
        panel.add(view);

        // Set up interactor and escape map
        final PublicPlantInfoInteractor interactor = new PublicPlantInfoInteractor();
        interactor.setEscapeMap(escapeMap);

        // Create controller and link to view
        final PublicPlantInfoController controller = new PublicPlantInfoController(interactor);
        view.setController(controller);
    }
}
