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
        plantDatabase.likePlant(input.getPlant().getFileID());
        presenter.prepareSuccessView();
    }
}
