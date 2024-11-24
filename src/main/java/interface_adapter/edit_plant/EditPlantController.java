package interface_adapter.edit_plant;

import use_case.edit_plant.EditPlantInputBoundary;
import use_case.edit_plant.EditPlantInputData;

/**
 * Controller class for editing a plant.
 */
public class EditPlantController {
    private final EditPlantInputBoundary editInteractor;

    /**
     * Constructs an EditPlantController with the given EditPlantInputBoundary.
     * @param editInteractor the input boundary for plant editing
     */
    public EditPlantController(EditPlantInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    /**
     * Saves the plant with the provided notes and visibility status.
     * @param userNotes the notes provided by the user for the plant
     * @param isPublic whether the plant is set to public visibility
     */
    public void savePlant(String userNotes, boolean isPublic) {
        final EditPlantInputData inputData = new EditPlantInputData(userNotes, isPublic);
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