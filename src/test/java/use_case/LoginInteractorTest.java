package use_case;
import data_access.InMemoryUserDataAccessObject;
import entity.User;
import org.junit.Test;
import use_case.login.*;

import static org.junit.Assert.*;

public class LoginInteractorTest {
    @Test
    public void successTest() {
        LoginInputData inputData = new LoginInputData("arz", "123");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        // For the success test, we need to add Paul to the data access repository before we log in.
        User user = new User("arz", "123");
        userRepository.addUser(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("arz", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToStartView() {}
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }


    @Test
    public void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("arz", "wrong");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        User user = new User("arz", "123");
        userRepository.addUser(user);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"arz\".", error);
            }

            @Override
            public void switchToStartView() {}
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("arz", "123");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("arz: Account does not exist.", error);
            }

            @Override
            public void switchToStartView() {}
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

}
