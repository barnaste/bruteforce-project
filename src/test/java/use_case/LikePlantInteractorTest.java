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
        // create a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);

        // upload plant to an InMemory DAO
        PlantDataAccessInterface plantRepository = new InMemoryPlantDataAccessObject();
        plantRepository.addPlant(plant);

        // create input data
        LikePlantInputData inputData = new LikePlantInputData(plant);

        // construct temporary presenter
        LikePlantOutputBoundary successPresenter = new LikePlantOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // the like_plant use case should increment the plant's likes from 0 to 1
                assertEquals(1, plantRepository.fetchPlantByID(plantID).getNumOfLikes());
            }
        };

        // execute the use case
        LikePlantInputBoundary interactor = new LikePlantInteractor(plantRepository, successPresenter);
        interactor.execute(inputData);
    }
}
