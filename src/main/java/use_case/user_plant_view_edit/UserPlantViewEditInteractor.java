package use_case.user_plant_view_edit;

import interface_adapter.edit_plant.UserPlantViewEditPresenter;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class UserPlantViewEditInteractor implements UserPlantViewEditInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;
    private final UserPlantViewEditPresenter presenter;
    private Plant currentPlant;

    private Runnable escapeMap;

    public UserPlantViewEditInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase, UserPlantViewEditPresenter presenter) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
        this.presenter = presenter;
    }

    public void setPlant(Plant currentPlant) {
        this.currentPlant = currentPlant;
    }

    @Override
    public void savePlant(UserPlantViewEditInputData inputData) {
        plantDatabase.editPlant(
                currentPlant.getFileID(),
                inputData.isPublic(),
                inputData.getUserNotes()
        );

        presenter.prepareSuccessView();
        this.escapeMap.run();
    }

    @Override
    public void deletePlant() {
        imageDatabase.deleteImage(currentPlant.getImageID());
        plantDatabase.deletePlant(currentPlant.getFileID());

        presenter.prepareSuccessView();
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
