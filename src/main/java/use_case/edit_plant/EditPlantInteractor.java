package use_case.edit_plant;

import data_access.ImageDataAccessObject;
import data_access.PlantDataAccessObject;
import entity.Plant;

public class EditPlantInteractor implements EditPlantInputBoundary {
    private final PlantDataAccessObject plantDatabase;
    private final ImageDataAccessObject imageDatabase;
    private Plant currentPlant;

    private Runnable escapeMap;

    public EditPlantInteractor(ImageDataAccessObject imageDatabase, PlantDataAccessObject plantDatabase) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
    }

    public void setPlant(Plant currentPlant) {
        this.currentPlant = currentPlant;
    }

    @Override
    public void savePlant(EditPlantInputData inputData) {
        plantDatabase.editPlant(
                currentPlant.getFileID(),
                inputData.isPublic(),
                inputData.getUserNotes()
        );

        this.escapeMap.run();
    }

    @Override
    public void deletePlant() {
        imageDatabase.deleteImage(currentPlant.getImageID());
        plantDatabase.deletePlant(currentPlant.getFileID());

        this.escapeMap.run();
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
