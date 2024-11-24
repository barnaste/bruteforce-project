package interface_adapter.public_plant_info;

import use_case.public_plant_info.PublicPlantInfoInputBoundary;

public class PublicPlantInfoController {

    private final PublicPlantInfoInputBoundary publicplantUseCaseInteractor;

    public PublicPlantInfoController(PublicPlantInfoInputBoundary publicplantUseCaseInteractor) {
        this.publicplantUseCaseInteractor = publicplantUseCaseInteractor;
    }
    public void escape() {
        publicplantUseCaseInteractor.escape();
    }
}