package use_case.delete_user;

import java.util.List;

import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

/**
 * The Delete User Interactor handles the logic for deleting a user's account,
 * including their plants and associated images, after validating their credentials.
 */
public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final PlantDataAccessInterface plantDataAccessObject;
    private final ImageDataAccessInterface imageDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final DeleteUserOutputBoundary deleteUserPresenter;

    private Runnable escapeMap;

    // Constructor to initialize the necessary DAOs and presenter for the delete operation
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
        final String username = userDataAccessObject.getCurrentUsername();
        final String password = userDataAccessObject.getUser(username).getPassword();

        // Validate user credentials before proceeding with deletion
        if (username.equals(tempusername) && password.equals(temppassword)) {
            // If credentials are valid, proceed with deleting the user's plants, images, and account

            // Retrieve all plants associated with the user
            final List<Plant> plants = plantDataAccessObject.getUserPlants(username);
            for (Plant plant : plants) {
                // Delete associated images for each plant
                imageDataAccessObject.deleteImage(plant.getImageID());
                // Delete the plant from the database
                plantDataAccessObject.deletePlant(plant.getFileID());
            }

            // Delete the user's account from the database
            userDataAccessObject.deleteUser(username);
            // Log out the current user
            userDataAccessObject.setCurrentUsername(null);

            // Prepare success view after successful deletion
            deleteUserPresenter.prepareSuccessView();
            // Trigger navigation after successful deletion
            escape();
        }
        else {
            // If credentials do not match, show failure message
            deleteUserPresenter.prepareFailView();
        }
    }

    // Set the escape map for navigating away after the deletion
    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    /**
     * Executes the escape action, typically used to navigate to the welcome view.
     * This method triggers the {@link #escapeMap} to perform the necessary action.
     */
    public void escape() {
        this.escapeMap.run();
    }
}
