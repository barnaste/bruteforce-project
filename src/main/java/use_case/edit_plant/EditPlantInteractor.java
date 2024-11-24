package use_case.edit_plant;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class EditPlantInteractor implements EditPlantInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;
    private Plant currentPlant;

    private Runnable escapeMap;

    public EditPlantInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase) {
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
