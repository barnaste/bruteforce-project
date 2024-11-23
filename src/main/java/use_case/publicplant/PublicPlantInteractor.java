package use_case.publicplant;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class PublicPlantInteractor implements PublicPlantInputBoundary{

    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;
    private Plant currentPlant;

    private Runnable escapeMap;

    public PublicPlantInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
    }

    public void setPlant(Plant currentPlant) {
        this.currentPlant = currentPlant;
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
