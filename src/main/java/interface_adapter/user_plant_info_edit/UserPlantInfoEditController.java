package interface_adapter.user_plant_info_edit;

import use_case.user_plant_info_edit.UserPlantInfoEditInputBoundary;
import use_case.user_plant_info_edit.UserPlantInfoEditInputData;

/**
 * Controller class for editing a plant.
 */
public class UserPlantInfoEditController {
    private final UserPlantInfoEditInputBoundary editInteractor;

    /**
     * Constructs an EditPlantController with the given EditPlantInputBoundary.
     * @param editInteractor the input boundary for plant editing
     */
    public UserPlantInfoEditController(UserPlantInfoEditInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    /**
     * Saves the plant with the provided notes and visibility status.
     * @param userNotes the notes provided by the user for the plant
     * @param isPublic whether the plant is set to public visibility
     */
    public void savePlant(String userNotes, boolean isPublic) {
        final UserPlantInfoEditInputData inputData = new UserPlantInfoEditInputData(userNotes, isPublic);
        editInteractor.savePlant(inputData);
    }

    /**
     * Deletes the plant.
     */
    public void deletePlant() {
        editInteractor.deletePlant();
    }

    /**
     * Triggers escape action, typically used for cancelling or exiting.
     */
    public void escape() {
        editInteractor.escape();
    }
}
