package use_case.logout;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import org.junit.Test;
import use_case.logout.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LogoutInteractorTest {
    @Test
    public void successTest() {
        LogoutInputData inputData = new LogoutInputData("arz");
        UserDataAccessInterface userRepository = InMemoryUserDataAccessObject.getInstance();

        User user = new User("arz", "123");
        userRepository.addUser(user);

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

        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }
}
