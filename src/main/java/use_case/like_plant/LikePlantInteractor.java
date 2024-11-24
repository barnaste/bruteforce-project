package use_case.like_plant;

import use_case.PlantDataAccessInterface;

public class LikePlantInteractor implements LikePlantInputBoundary {
    private final PlantDataAccessInterface plantDatabase;

    public LikePlantInteractor(PlantDataAccessInterface plantDatabase) {
        this.plantDatabase = plantDatabase;
    }

    @Override
    public void execute(LikePlantInputData input) {
        plantDatabase.likePlant(input.getPlantID());
    }
}
