package interface_adapter.public_plant_view;

import org.bson.types.ObjectId;
import use_case.publicplant.PublicPlantInputBoundary;

public class PublicPlantViewController {

    private final PublicPlantInputBoundary publicplantUseCaseInteractor;

    public PublicPlantViewController(PublicPlantInputBoundary publicplantUseCaseInteractor) {
        this.publicplantUseCaseInteractor = publicplantUseCaseInteractor;
    }

    public void setPlant(ObjectId plantID) {
        publicplantUseCaseInteractor.setPlant(plantID);
    }

    public void escape() {
        publicplantUseCaseInteractor.escape();
    }

    public void like() {
        publicplantUseCaseInteractor.like();
    }
}