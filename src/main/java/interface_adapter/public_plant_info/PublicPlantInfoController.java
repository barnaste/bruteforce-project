package interface_adapter.public_plant_info;

import use_case.public_plant_info.PublicPlantInfoInputBoundary;

/**
 * Controller responsible for handling user interactions in the Public Plant Info view.
 * It communicates with the `PublicPlantInfoInputBoundary` (Interactor) to execute
 * business logic and handle actions such as "escape" (e.g., closing the plant info view).
 */
public class PublicPlantInfoController {

    private final PublicPlantInfoInputBoundary publicplantUseCaseInteractor;

    public PublicPlantInfoController(PublicPlantInfoInputBoundary publicplantUseCaseInteractor) {
        this.publicplantUseCaseInteractor = publicplantUseCaseInteractor;
    }

    /**
     * Invokes the escape method on the interactor, typically used to handle the cancellation
     * or closing of the public plant info view.
     */
    public void escape() {
        publicplantUseCaseInteractor.escape();
    }
}
