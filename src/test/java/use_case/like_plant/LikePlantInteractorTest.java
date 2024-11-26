package use_case.like_plant;

import data_access.InMemoryPlantDataAccessObject;
import entity.Plant;
import org.bson.types.ObjectId;
import org.junit.Test;
import use_case.PlantDataAccessInterface;

import static org.junit.Assert.*;


public class LikePlantInteractorTest {
    @Test
    public void successTest() {
        // create plant with an arbitrary (but saved) id
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);

        // add plant to repo
        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        // create input data with plant
        LikePlantInputData inputData = new LikePlantInputData(plant);

        // construct testing presenter
        LikePlantOutputBoundary successPresenter = () -> {
            // the like count should increase from 0 to 1
            assertEquals(1, plantRepository.fetchPlantByID(plantID).getNumOfLikes());
        };

        // execute use case
        LikePlantInputBoundary interactor = new LikePlantInteractor(plantRepository, successPresenter);
        interactor.execute(inputData);
    }
}
