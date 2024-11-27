package interface_adapter.delete_user;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.delete_user.DeleteUserOutputBoundary;

import javax.swing.*;

/**
 * Presenter for handling the output of the DeleteUser use case.
 * Updates the view with success or failure states based on user deletion.
 */
public class DeleteUserPresenter implements DeleteUserOutputBoundary {

    private final MainViewModel mainViewModel;  // Manages the main app's state
    private final ViewManagerModel viewManagerModel;  // Handles view transitions
    private final LoginViewModel loginViewModel;  // Manages login state
    private final DeleteUserViewModel deleteUserViewModel;

    /**
     * Constructs the DeleteUserPresenter.
     *
     * @param viewManagerModel manages the current view state
     * @param mainViewModel handles the main application state
     * @param loginViewModel manages login state
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
        mainState.setUsername("");  // Clear logged-in username
        mainViewModel.setState(mainState);
        mainViewModel.firePropertyChanged("state");

        // Clear the login view state (reset login details)
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");  // Clear username
        loginState.setPassword("");  // Clear password
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged("state");

        // Switch to the start view
        this.viewManagerModel.setState("StartView");
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view and shows an error message.
     *
     * @param error the error message to display
     */
    @Override
    public void prepareFailView() {
        this.deleteUserViewModel.firePropertyChanged("error");
    }
}