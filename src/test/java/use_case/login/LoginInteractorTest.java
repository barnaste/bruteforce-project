package use_case.login;
import data_access.InMemoryUserDataAccessObject;
import entity.User;
import org.junit.Test;
import use_case.UserDataAccessInterface;

import static org.junit.Assert.*;

public class LoginInteractorTest {

    @Test
    public void successTest() {
        // add user to repo
        User user = new User("arz", "123");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();
        userRepository.addUser(user);

        // create input data with correct username/password
        LoginInputData inputData = new LoginInputData("arz", "123");

        // construct testing presenter
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData output) {
                assertEquals("arz", output.getUsername());
            }

            @Override
            public void prepareFailView() {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToStartView() {
                fail("Use case switching view is unexpected.");
            }
        };

        // execute the test
        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);

    }

    @Test
    public void failurePasswordMismatchTest() {
        // add user to repo
        User user = new User("arz", "123");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();
        userRepository.addUser(user);

        // create input data with incorrect password
        LoginInputData inputData = new LoginInputData("arz", "wrong");

        // constructing testing presenter
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData output) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView() {
                // this means the test passed, so do nothing
            }

            @Override
            public void switchToStartView() {
                fail("Use case switching view is unexpected.");
            }
        };

        // execute use case
        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureUserDoesNotExistTest() {
        // create empty repo
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        // create input data with non-existent user
        LoginInputData inputData = new LoginInputData("arz", "123");

        // construct testing presenter
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView() {
                // this means the test passed, so do nothing
            }

            @Override
            public void switchToStartView() {
                fail("Use case switching view is unexpected.");
            }
        };

        // execute use case
        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void successSwitchToStartView() {
        // create empty repo
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        // construct testing presenter
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // use case is not executed
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView() {
                // use case is not executed
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToStartView() {
                // this means the test passed, so do nothing
            }
        };

        // execute switch to start view
        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.switchToStartView();
    }
}
