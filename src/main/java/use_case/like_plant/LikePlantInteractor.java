package use_case.like_plant;

import use_case.PlantDataAccessInterface;

/**
 * Interactor for the "like plant" use case.
 * Handles the business logic for liking a plant by interacting with the plant data store and presenter.
 */
public class LikePlantInteractor implements LikePlantInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    private final LikePlantOutputBoundary presenter;

    /**
     * Constructs a LikePlantInteractor with the specified data access object and presenter.
     *
     * @param plantDatabase the DAO for accessing and modifying plant data
     * @param presenter the presenter responsible for updating the view
     */
    public LikePlantInteractor(PlantDataAccessInterface plantDatabase, LikePlantOutputBoundary presenter) {
        this.plantDatabase = plantDatabase;
        this.presenter = presenter;
    }

    @Override
    public void execute(LikePlantInputData input) {
        // fetch plant id and tell DAO to perform a like
        plantDatabase.likePlant(input.getPlant().getFileID());

        // pass to presenter (this use case always passes)
        presenter.prepareSuccessView();
    }
}
