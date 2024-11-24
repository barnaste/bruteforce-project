package use_case.public_plant_view;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class PublicPlantViewInteractor implements PublicPlantViewInputBoundary {

    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;

    // TODO: It doesn't seem like the imageDatabase is doing much here?

    private Plant currentPlant;
    private Runnable escapeMap;

    public PublicPlantViewInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
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
