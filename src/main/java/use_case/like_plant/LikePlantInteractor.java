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
        // fetch plant id and tell DAO to perform a like
        plantDatabase.likePlant(input.getPlant().getFileID());

        // pass to presenter (this use case always passes)
        presenter.prepareSuccessView();
    }
}
