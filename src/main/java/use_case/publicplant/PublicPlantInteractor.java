package use_case.publicplant;

import data_access.ImageDataAccessObject;
import data_access.PlantDataAccessObject;
import entity.Plant;
import org.bson.types.ObjectId;

public class PublicPlantInteractor implements PublicPlantInputBoundary{

    private final PlantDataAccessObject plantDatabase;
    private final ImageDataAccessObject imageDatabase;
    private Plant currentPlant;

    private Runnable escapeMap;

    public PublicPlantInteractor(ImageDataAccessObject imageDatabase, PlantDataAccessObject plantDatabase) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
    }

    @Override
    public void setPlant(ObjectId plantID) {
        this.currentPlant = plantDatabase.fetchPlantByID(plantID);
    }

    @Override
    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    @Override
    public void escape() {
        this.escapeMap.run();
    }

    @Override
    public void like() {
        plantDatabase.likePlant(currentPlant.getFileID());
    }
}
