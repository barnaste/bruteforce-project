package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private MainViewModel mainViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           MainViewModel mainViewModel,
                           LoginViewModel loginViewModel) {
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        // On success, switch to the login view

        // Update the logged in state
        final MainState mainState = mainViewModel.getState();
        mainState.setUsername("");
        mainViewModel.setState(mainState);
        mainViewModel.firePropertyChanged("state");

        // Update the login state
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged("state");

        // Switch to the login view
        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // We assume that logout can't fail
    }
}