package interface_adapter.public_plant_view;

import use_case.publicplant.PublicPlantInputBoundary;

public class PublicPlantViewController {

    private final PublicPlantInputBoundary publicplantUseCaseInteractor;

    public PublicPlantViewController(PublicPlantInputBoundary publicplantUseCaseInteractor) {
        this.publicplantUseCaseInteractor = publicplantUseCaseInteractor;
    }

    public void escape() {
        publicplantUseCaseInteractor.escape();
    }
}