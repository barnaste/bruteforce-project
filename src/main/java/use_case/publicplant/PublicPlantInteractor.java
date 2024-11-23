package use_case.publicplant;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;
import org.bson.types.ObjectId;

public class PublicPlantInteractor implements PublicPlantInputBoundary{

    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;
    private Plant currentPlant;

    private Runnable escapeMap;

    public PublicPlantInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase) {
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
