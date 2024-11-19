package interface_adapter.edit_plant;

import use_case.edit_plant.EditPlantInputBoundary;
import use_case.edit_plant.EditPlantInputData;

public class EditPlantController {
    private final EditPlantInputBoundary editInteractor;

    public EditPlantController(EditPlantInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    public void savePlant(String userNotes, boolean isPublic) {
        final EditPlantInputData inputData = new EditPlantInputData(userNotes, isPublic);
        editInteractor.savePlant(inputData);
    }

    public void deletePlant() {
        editInteractor.deletePlant();
    }

    public void escape() {
        editInteractor.escape();
    }
}
