package interface_adapter.delete_user;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.delete_user.DeleteUserOutputBoundary;

/**
 * Presenter for handling the output of the DeleteUser use case.
 * Updates the view with success or failure states based on user deletion.
 */
public class DeleteUserPresenter implements DeleteUserOutputBoundary {

    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final DeleteUserViewModel deleteUserViewModel;

    /**
     * Constructs a DeleteUserPresenter to manage the delete user functionality.
     *
     * @param viewManagerModel the model for managing the current view state
     * @param mainViewModel the model for managing the main application state
     * @param loginViewModel the model for managing user login state
     * @param deleteUserViewModel the model for managing the delete user view state
     */
    public DeleteUserPresenter(ViewManagerModel viewManagerModel,
                               MainViewModel mainViewModel,
                               LoginViewModel loginViewModel,
                               DeleteUserViewModel deleteUserViewModel) {
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.deleteUserViewModel = deleteUserViewModel;
    }

    /**
     * Prepares the success view after successful user deletion.
     * Logs the user out, clears login details, and switches to the start view.
     */
    @Override
    public void prepareSuccessView() {
        // Clear the main view state (logout)
        final MainState mainState = mainViewModel.getState();
        mainState.setUsername("");
        mainViewModel.setState(mainState);
        mainViewModel.firePropertyChanged("state");

        // Clear the login view state (reset login details)
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged("state");

        // Switch to the start view
        this.viewManagerModel.setState("StartView");
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view and shows an error message.
     */
    @Override
    public void prepareFailView() {
        this.deleteUserViewModel.firePropertyChanged("error");
    }
}
