package use_case;
import data_access.InMemoryPlantDataAccessObject;
import entity.Plant;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;
import use_case.like_plant.LikePlantInputBoundary;
import use_case.like_plant.LikePlantInputData;
import use_case.like_plant.LikePlantInteractor;
import use_case.like_plant.LikePlantOutputBoundary;


public class LikePlantInteractorTest {
    @Test
    public void successTest() {
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        LikePlantInputData inputData = new LikePlantInputData(plant);

        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        LikePlantOutputBoundary successPresenter = new LikePlantOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                assertEquals(1, plantRepository.fetchPlantByID(plantID).getNumOfLikes());
            }
        };

        LikePlantInputBoundary interactor = new LikePlantInteractor(plantRepository, successPresenter);
        interactor.execute(inputData);
    }
}
