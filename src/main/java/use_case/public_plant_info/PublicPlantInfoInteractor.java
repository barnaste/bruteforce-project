package use_case.public_plant_info;

/**
 * Interactor responsible for handling the business logic related to public plant information.
 * It is used by the `PublicPlantInfoController` to manage actions such as escaping (e.g., closing the view).
 */
public class PublicPlantInfoInteractor implements PublicPlantInfoInputBoundary {

    private Runnable escapeMap;

    public PublicPlantInfoInteractor() {

    }

    @Override
    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    @Override
    public void escape() {
        this.escapeMap.run();
    }
}
