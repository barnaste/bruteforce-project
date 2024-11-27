package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteUserController;
import interface_adapter.delete_user.DeleteUserPresenter;
import interface_adapter.delete_user.DeleteUserViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.upload.UploadController;
import use_case.delete_user.DeleteUserInputBoundary;
import use_case.delete_user.DeleteUserInputData;
import use_case.delete_user.DeleteUserInteractor;
import use_case.delete_user.DeleteUserOutputBoundary;
import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInteractor;
import view.DeleteUserView;

import javax.swing.*;
import java.awt.*;

public class DeleteUserPanelFactory {
    /**
     * Creates and sets up the Delete User panel, wiring the necessary components together
     * such as the ViewModel, View, Controller, and Interactor. It also adds the DeleteUser view
     * to the provided parent panel and ensures the correct business logic and data access layers
     * are in place for deleting a user.
     *
     * @param parentPanel The parent container to which the delete user panel will be added.
     * @param deleteUserPanel The JPanel representing the delete user view that will be added
     *                        to the parent panel.
     * @param escapeMap A Runnable that can be invoked to cancel the delete operation.
     * @param viewManagerModel The ViewManagerModel for managing different views in the application.
     * @param mainViewModel The MainViewModel to manage the main application's state.
     * @param loginViewModel The LoginViewModel to manage the user's login state.
     */
    public static void createDeleteUserPanel(Container parentPanel, JPanel deleteUserPanel, Runnable escapeMap, ViewManagerModel viewManagerModel, MainViewModel mainViewModel, LoginViewModel loginViewModel) {
        // Revalidate and repaint the parent panel to reflect changes
        parentPanel.revalidate();
        parentPanel.repaint();

        // Create the ViewModel for the DeleteUser view
        DeleteUserViewModel deleteUserViewModel = new DeleteUserViewModel();

        // Create the view and add it to the deleteUserPanel with its name
        DeleteUserView deleteUserView = new DeleteUserView(deleteUserViewModel);
        deleteUserPanel.add(deleteUserView, deleteUserView.getViewName());

        // Get instances of the required Data Access Objects (DAOs)
        MongoPlantDataAccessObject mongoPlantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        MongoUserDataAccessObject mongoUserDataAccessObject = MongoUserDataAccessObject.getInstance();
        MongoImageDataAccessObject mongoImageDataAccessObject = MongoImageDataAccessObject.getInstance();

        // Create the presenter for the delete user functionality
        DeleteUserOutputBoundary deleteUserPresentor = new DeleteUserPresenter(viewManagerModel, mainViewModel, loginViewModel );

        // Create the interactor which handles business logic related to deleting a user
        DeleteUserInputBoundary deleteuserInteractor = new DeleteUserInteractor(mongoPlantDataAccessObject, mongoImageDataAccessObject, mongoUserDataAccessObject, deleteUserPresentor);

        // Set up escape map functionality for cancelling the action
        deleteuserInteractor.setEscapeMap(escapeMap);

        // Create the controller that coordinates the interactor and view
        DeleteUserController controller = new DeleteUserController(deleteuserInteractor);

        // Set the controller in the view to manage interactions
        deleteUserView.setController(controller);
    }
}
