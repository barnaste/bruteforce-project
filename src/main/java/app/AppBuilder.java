package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.LoginView;
import view.MainView;
import view.SignupView;
import view.StartView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    // Panel to hold views, managed by CardLayout
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // Model and manager to handle view transitions
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Data access object for user data (MongoDB)
    private final MongoUserDataAccessObject userDataAccessObject = MongoUserDataAccessObject.getInstance();

    private final MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
    private final MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();

    // ViewModels for different views
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final MainViewModel mainViewModel = new MainViewModel();

    // Views for different app states
    private final SignupView signupView = new SignupView(signupViewModel);
    private final MainView mainView = new MainView(mainViewModel, viewManagerModel, loginViewModel);
    private final LoginView loginView = new LoginView(loginViewModel);
    private final StartView startView = new StartView(signupViewModel, loginViewModel, viewManagerModel);

    // Initializes CardLayout for the card panel
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Start View to the application.
     * @return this builder
     */
    public AppBuilder addStartView() {
        // Add StartView to card panel with a unique name
        cardPanel.add(startView, "StartView");
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        cardPanel.add(mainView, mainView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                mainViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Plantinarium");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Set initial view to "StartView"
        viewManagerModel.setState("StartView");
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
