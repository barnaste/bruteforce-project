package use_case.delete_user;

import java.util.List;

import org.bson.types.ObjectId;

import entity.Plant;

/**
 * DAO for the Delete User Use Case.
 */
public interface DeleteUserUserDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    void setCurrentUsername(String username);

    /**
     * Deletes a user from the application.
     *
     * @param username the username of the user to be deleted.
     *                 Must not be null or empty. If the username does not exist in the system,
     *                 the method should handle this gracefully.
     * @return true if the user was successfully deleted; false if the user does not exist
     *          or the operation could not be completed.
     */
    boolean deleteUser(String username);

    /**
     * Retrieves a list of plants for the specified user.
     *
     * @param username the user's username
     * @return a list of the user's plants
     */
    List<Plant> getUserPlants(String username);

    /**
     * Deletes an image by its ID.
     *
     * @param id the image ID
     * @return true if the image was deleted, false otherwise
     */
    boolean deleteImage(String id);

    /**
     * Deletes a plant by its file ID.
     *
     * @param fileID the plant's file ID
     * @return true if the plant was deleted, false otherwise
     */
    boolean deletePlant(ObjectId fileID);
}
