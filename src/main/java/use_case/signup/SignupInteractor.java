package use_case.signup;

import entity.User;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
        private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                SignupOutputBoundary signupOutputBoundary) {
            this.userDataAccessObject = signupDataAccessInterface;
            this.userPresenter = signupOutputBoundary;
        }

        @Override
        public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByUsername(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if (signupInputData.getUsername().isEmpty() || signupInputData.getPassword().isEmpty()) {
            userPresenter.prepareFailView("Username and / or password cannot be empty.");
        }
        else {
            final User user = new User(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.addUser(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
    public void switchToStartView() {
        userPresenter.switchToStartView();
    }
}
