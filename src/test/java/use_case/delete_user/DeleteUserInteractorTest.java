package use_case.delete_user;

import data_access.InMemoryImageDataAccessObject;
import data_access.InMemoryPlantDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Plant;
import entity.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import use_case.PlantDataAccessInterface;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class DeleteUserInteractorTest {
    @Test
    public void successTest() {
        // Initialize DAOs
        InMemoryPlantDataAccessObject plantDAO = InMemoryPlantDataAccessObject.getInstance();
        InMemoryImageDataAccessObject imageDAO = InMemoryImageDataAccessObject.getInstance();
        InMemoryUserDataAccessObject userDAO = InMemoryUserDataAccessObject.getInstance();

        // Create a new user
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        userDAO.setCurrentUsername(user.getUsername());
        userDAO.addUser(user);

        // Create a mock Runnable for escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        // Create a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        DeleteUserInputData inputData = new DeleteUserInputData(user.getUsername(), user.getPassword());

        // Upload the plant to the database under the user
        plant.setOwner(user.getUsername());
        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        // Define success presenter
        DeleteUserOutputBoundary successPresenter = new DeleteUserOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                assertFalse(userDAO.existsByUsername("test"));
                assertEquals(plantRepository.getNumberOfUserPlants("test"), 0);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail();
            }
        };

        // Initialize interactor with the mock escape map
        DeleteUserInputBoundary interactor = new DeleteUserInteractor(plantDAO, imageDAO, userDAO, successPresenter);
        interactor.setEscapeMap(mockEscapeMap);

        // Execute the interactor
        interactor.execute(inputData);

        // Verify that the escape map's run method was called
        verify(mockEscapeMap, times(1)).run();  // Check that 'run' was called exactly once
    }

    @Test
    public void failureUserMismatchTest() {
        // This test tests the case when the user which is logged in is trying to delete the account of another user unsuccessfully.
        InMemoryPlantDataAccessObject plantDAO = InMemoryPlantDataAccessObject.getInstance();
        InMemoryImageDataAccessObject imageDAO = InMemoryImageDataAccessObject.getInstance();
        InMemoryUserDataAccessObject userDAO = InMemoryUserDataAccessObject.getInstance();
        // make a new user
        User user1 = new User();
        user1.setUsername("test1");
        user1.setPassword("test1");

        User user2 = new User();
        user2.setUsername("test2");
        user2.setPassword("test2");

        userDAO.setCurrentUsername(user1.getUsername());
        userDAO.addUser(user1);
        userDAO.addUser(user2);

        // Create a mock Runnable for escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        //make a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        plant.setOwner(user2.getUsername());
        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        //delete plants
        DeleteUserOutputBoundary successPresenter = new DeleteUserOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                fail();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assert plantDAO.fetchPlantByID(plantID) != null;
            }
        };

        DeleteUserInputData inputData = new DeleteUserInputData("test2", "test2");

        DeleteUserInputBoundary interactor = new DeleteUserInteractor(plantDAO, imageDAO, userDAO, successPresenter);
        interactor.setEscapeMap(mockEscapeMap);
        interactor.execute(inputData);

        // Verify that the escape map's run method was called
        verify(mockEscapeMap, times(0)).run();
    }

    @Test
    public void failurePasswordMismatchTest() {
        InMemoryPlantDataAccessObject plantDAO = InMemoryPlantDataAccessObject.getInstance();
        InMemoryImageDataAccessObject imageDAO = InMemoryImageDataAccessObject.getInstance();
        InMemoryUserDataAccessObject userDAO = InMemoryUserDataAccessObject.getInstance();
        // make a new user
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        userDAO.setCurrentUsername(user.getUsername());
        userDAO.addUser(user);

        // Create a mock Runnable for escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        //make a new plant
        Plant plant = new Plant();
        ObjectId plantID = new ObjectId();
        plant.setFileID(plantID);
        DeleteUserInputData inputData = new DeleteUserInputData("wrong username", "wrong password");

        //upload the plant to the database under the user
        plant.setOwner(user.getUsername());
        PlantDataAccessInterface plantRepository = InMemoryPlantDataAccessObject.getInstance();
        plantRepository.addPlant(plant);

        //delete plants
        DeleteUserOutputBoundary successPresenter = new DeleteUserOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                fail();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                return;
            }
        };

        DeleteUserInputBoundary interactor = new DeleteUserInteractor(plantDAO, imageDAO, userDAO, successPresenter);
        interactor.setEscapeMap(mockEscapeMap);

        // Execute the interactor
        interactor.execute(inputData);

        // Verify that the escape map's run method was called
        verify(mockEscapeMap, times(0)).run();
    }
}
