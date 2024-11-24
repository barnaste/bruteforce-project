package interface_adapter.public_plant_view;

import use_case.public_plant_view.PublicPlantViewInputBoundary;

public class PublicPlantViewController {

    private final PublicPlantViewInputBoundary publicplantUseCaseInteractor;

    public PublicPlantViewController(PublicPlantViewInputBoundary publicplantUseCaseInteractor) {
        this.publicplantUseCaseInteractor = publicplantUseCaseInteractor;
    }
    public void escape() {
        publicplantUseCaseInteractor.escape();
    }
}