package use_case.user_plant_info_edit;

import data_access.InMemoryImageDataAccessObject;
import data_access.InMemoryPlantDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Plant;
import org.bson.types.ObjectId;
import org.junit.Test;
import use_case.PlantDataAccessInterface;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class UserPlantInfoEditInteractorTest {

    @Test
    public void SavePlantTest() {

        // Initialize DAOs
        InMemoryPlantDataAccessObject plantDAO = InMemoryPlantDataAccessObject.getInstance();
        InMemoryImageDataAccessObject imageDAO = InMemoryImageDataAccessObject.getInstance();

        // Create a mock Runnable for escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        // Create a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        plant.setIsPublic(false);
        plant.setComments("Some random boring comment");

        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        UserPlantInfoEditOutputBoundary presenter = new UserPlantInfoEditOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                assert plantDAO.fetchPlantByID(plantID).getIsPublic();
            }
        };

        UserPlantInfoEditInputData inputData = new UserPlantInfoEditInputData("My Piranha Plant", true);
        UserPlantInfoEditInteractor interactor = new UserPlantInfoEditInteractor(imageDAO, plantDAO, presenter);
        interactor.setPlant(plant);
        interactor.setEscapeMap(mockEscapeMap);

        interactor.savePlant(inputData);
        interactor.escape();

        // Verify that the escape map's run method was called
        verify(mockEscapeMap, times(2)).run();  // Check that 'run' was called exactly once
    }

    @Test
    public void DeletePlantTest() {

        // Initialize DAOs
        InMemoryPlantDataAccessObject plantDAO = InMemoryPlantDataAccessObject.getInstance();
        InMemoryImageDataAccessObject imageDAO = InMemoryImageDataAccessObject.getInstance();
        InMemoryUserDataAccessObject userDAO = InMemoryUserDataAccessObject.getInstance();

        // Create a mock Runnable for escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        // Create a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        plant.setIsPublic(true);
        plant.setComments("Some random boring comment");

        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        UserPlantInfoEditOutputBoundary presenter = new UserPlantInfoEditOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                assertNull(plantDAO.fetchPlantByID(plantID));

            }
        };

        UserPlantInfoEditInteractor interactor = new UserPlantInfoEditInteractor(imageDAO, plantDAO, presenter);
        interactor.setPlant(plant);
        interactor.setEscapeMap(mockEscapeMap);

        interactor.deletePlant();

        // Verify that the escape map's run method was called
        verify(mockEscapeMap, times(1)).run();  // Check that 'run' was called exactly once
    }


}
