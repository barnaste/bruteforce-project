package use_case.delete_user;

import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

import java.util.List;


/**
 * The Delete User Interactor handles the logic for deleting a user's account,
 * including their plants and associated images, after validating their credentials.
 */
public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final PlantDataAccessInterface plantDataAccessObject;  // DAO for plant data operations
    private final ImageDataAccessInterface imageDataAccessObject;  // DAO for image data operations
    private final UserDataAccessInterface userDataAccessObject;    // DAO for user data operations
    private final DeleteUserOutputBoundary deleteUserPresenter;    // Presenter for displaying the result

    private Runnable escapeMap;  // Runnable to handle escape action (navigation)

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
        final String tempusername = deleteUserInputData.getUsername();  // User-provided username
        final String temppassword = deleteUserInputData.getPassword();  // User-provided password
        String username = userDataAccessObject.getCurrentUsername();  // Current logged-in username
        String password = userDataAccessObject.getUser(username).getPassword();  // Current user's password

        // Validate user credentials before proceeding with deletion
        if (username.equals(tempusername) && password.equals(temppassword)) {
            // If credentials are valid, proceed with deleting the user's plants, images, and account

            // Retrieve all plants associated with the user
            List<Plant> plants = plantDataAccessObject.getUserPlants(username);
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
            escape();  // Trigger navigation after successful deletion
        } else {
            // If credentials do not match, show failure message
            deleteUserPresenter.prepareFailView();
        }
    }

    // Set the escape map for navigating away after the deletion
    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    // Execute the escape action (typically to navigate to the welcome view)
    public void escape() {
        this.escapeMap.run();
    }
}