package use_case.logout;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import org.junit.Test;
import use_case.UserDataAccessInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LogoutInteractorTest {
    @Test
    public void successTest() {
        // add user to repo
        User user = new User("arz", "123");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();
        userRepository.addUser(user);

        // create input data with current username
        LogoutInputData inputData = new LogoutInputData("arz");

        // construct testing presenter
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                assertEquals("arz", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        // execute use case
        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }
}
