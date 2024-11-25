package use_case.like_plant;
import use_case.PlantDataAccessInterface;

public class LikePlantInteractor implements LikePlantInputBoundary {
    private final PlantDataAccessInterface plantDatabase;
    LikePlantOutputBoundary presenter;

    public LikePlantInteractor(PlantDataAccessInterface plantDatabase, LikePlantOutputBoundary presenter) {
        this.plantDatabase = plantDatabase;
        this.presenter = presenter;
    }

    @Override
    public void execute(LikePlantInputData input) {
        // fetch plant id and tell DAO to like the corresponding plant
        plantDatabase.likePlant(input.getPlant().getFileID());

        // prepare success view (this use case never fails)
        presenter.prepareSuccessView();
    }
}
