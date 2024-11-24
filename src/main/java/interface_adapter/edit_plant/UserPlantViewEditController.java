package interface_adapter.edit_plant;

import use_case.user_plant_view_edit.UserPlantViewEditInputBoundary;
import use_case.user_plant_view_edit.UserPlantViewEditInputData;

/**
 * Controller class for editing a plant.
 */
public class UserPlantViewEditController {
    private final UserPlantViewEditInputBoundary editInteractor;

    /**
     * Constructs an EditPlantController with the given EditPlantInputBoundary.
     * @param editInteractor the input boundary for plant editing
     */
    public UserPlantViewEditController(UserPlantViewEditInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    /**
     * Saves the plant with the provided notes and visibility status.
     * @param userNotes the notes provided by the user for the plant
     * @param isPublic whether the plant is set to public visibility
     */
    public void savePlant(String userNotes, boolean isPublic) {
        final UserPlantViewEditInputData inputData = new UserPlantViewEditInputData(userNotes, isPublic);
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