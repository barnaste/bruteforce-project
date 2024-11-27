package use_case.delete_user;

import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

import java.util.List;

/**
 * The Delete User Interactor.
 */
public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final PlantDataAccessInterface plantDataAccessObject;
    private final ImageDataAccessInterface imageDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final DeleteUserOutputBoundary deleteUserPresenter;

    private Runnable escapeMap;

    // Constructor initializes DAOs and presenter
    public DeleteUserInteractor(PlantDataAccessInterface plantDataAccessObject,
                                ImageDataAccessInterface imageDataAccessObject,
                                UserDataAccessInterface userDataAccessObject,
                                DeleteUserOutputBoundary deleteUserOutputBoundary) {
        this.plantDataAccessObject = plantDataAccessObject;
        this.imageDataAccessObject = imageDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
        this.deleteUserPresenter = deleteUserOutputBoundary;
    }

    @Override
    public void execute(DeleteUserInputData deleteUserInputData) {
        final String tempusername = deleteUserInputData.getUsername();
        final String temppassword = deleteUserInputData.getPassword();
        String username = userDataAccessObject.getCurrentUsername();
        String password = userDataAccessObject.getUser(username).getPassword();

        // Validate provided credentials
        if (username.equals(tempusername) && password.equals(temppassword)) {
            // Proceed with deletion if credentials match

            // Retrieve all plants associated with the user
            List<Plant> plants = plantDataAccessObject.getUserPlants(username);
            for (Plant plant : plants) {
                // Delete associated images first
                imageDataAccessObject.deleteImage(plant.getImageID());
                // Then delete plant data
                plantDataAccessObject.deletePlant(plant.getFileID());
            }

            // Delete user data and log out
            userDataAccessObject.deleteUser(username);
            userDataAccessObject.setCurrentUsername(null);

            // Prepare success output data
            final DeleteUserOutputData deleteUserOutputData = new DeleteUserOutputData(username, false);
            // Show success view
            deleteUserPresenter.prepareSuccessView(deleteUserOutputData);
            escape(); // Prepare the mainView for the next user that logs in.
        } else {
            // Credentials mismatch: show failure message
            deleteUserPresenter.prepareFailView("Invalid credentials. Try again.");
        }
    }

    // Set escape action (used to navigate away from the current view)
    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    // Trigger escape action to exit or navigate
    public void escape() {
        this.escapeMap.run();
    }
}
