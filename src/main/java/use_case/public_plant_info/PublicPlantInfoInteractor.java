package use_case.public_plant_info;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class PublicPlantInfoInteractor implements PublicPlantInfoInputBoundary {

    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;

    // TODO: It doesn't seem like the imageDatabase is doing much here?

    private Plant currentPlant;
    private Runnable escapeMap;

    public PublicPlantInfoInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase) {
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
