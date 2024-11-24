package use_case.like_plant;

import interface_adapter.like_plant.LikePlantPresenter;
import use_case.PlantDataAccessInterface;

public class LikePlantInteractor implements LikePlantInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    LikePlantPresenter presenter;

    public LikePlantInteractor(PlantDataAccessInterface plantDatabase, LikePlantPresenter presenter) {
        this.plantDatabase = plantDatabase;
        this.presenter = presenter;
    }

    @Override
    public void execute(LikePlantInputData input) {
        plantDatabase.likePlant(input.getPlantID());
        presenter.prepareSuccessView();
    }
}
