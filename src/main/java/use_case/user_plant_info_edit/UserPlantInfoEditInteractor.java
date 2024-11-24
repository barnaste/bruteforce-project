package use_case.user_plant_info_edit;

import interface_adapter.user_plant_info_edit.UserPlantInfoEditPresenter;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;

public class UserPlantInfoEditInteractor implements UserPlantInfoEditInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    private final ImageDataAccessInterface imageDatabase;
    private final UserPlantInfoEditPresenter presenter;
    private Plant currentPlant;

    private Runnable escapeMap;

    public UserPlantInfoEditInteractor(ImageDataAccessInterface imageDatabase, PlantDataAccessInterface plantDatabase, UserPlantInfoEditPresenter presenter) {
        this.plantDatabase = plantDatabase;
        this.imageDatabase = imageDatabase;
        this.presenter = presenter;
    }

    public void setPlant(Plant currentPlant) {
        this.currentPlant = currentPlant;
    }

    @Override
    public void savePlant(UserPlantInfoEditInputData inputData) {
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
