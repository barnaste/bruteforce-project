package data_access;

import entity.Plant;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * PlantDB is an interface that defines the methods that the PlantDB class must implement.
 */
public interface PlantDataAccessObject {
    /**
     * A method that returns plants for a given user in the database.
     * The plants are sorted by the last modified date in descending order.
     * @param username is the username of the user
     * @param skip is the number of documents to skip in the result set.
     * @param limit the maximum number of documents to return.
     * @return a list of Plant objects that belong to the specified user,
     *         sorted by the last modified date. Returns null if an exception occurs.
     */
    List<Plant> getUserPlants(String username, int skip, int limit);

    /**
     * A method that returns ALL plants for a given user in the database.
     * The plants are sorted by the last modified date in descending order.
     * @param username is the username of the user
     * @return a list of Plant objects that belong to the specified user,
     *         sorted by the last modified date. Returns null if an exception occurs.
     */
    List<Plant> getUserPlants(String username);

    /**
     * A method that returns all the plants owned by a user in the database.
     * @param skip is the number of documents to skip in the result set.
     * @param limit the maximum number of documents to return.
     * @return a list of all the plants belonging to that user
     */
    List<Plant> getPublicPlants(int skip, int limit);

    /**
     * A method that inserts a new plant into the database.
     * @param plant is the plant being inserted
     */
    void addPlant(Plant plant);

    /**
     * Edits the `isPublic` and `comments` fields of a plant identified by its ID.
     *
     * @param fileID   the ID of the plant to update
     * @param isPublic the new value for the `isPublic` field
     * @param comments the new value for the `comments` field
     * @return {@code true} if the plant was successfully updated, {@code false} otherwise
     */
    boolean editPlant(ObjectId fileID, boolean isPublic, String comments);

    /**
     * A method that deletes a plant from the database.
     * @param fileID is the fileID of the plant being deleted
     * @return true if the plant has been successfully deleted, false otherwise
     */
    boolean deletePlant(ObjectId fileID);

    /**
     * Retrieves the total number of public plants in the database.
     *
     * @return the count of public plants
     */
    int getNumberOfPublicPlants();

    /**
     * Fetches a plant from the database by its unique ID.
     *
     * @param fileID the ID of the plant to fetch
     * @return the plant with the specified ID, or {@code null} if no plant is found or an error occurs
     */
    Plant fetchPlantByID(ObjectId fileID);

    /**
     * Retrieves the total number of plants owned by a specific user.
     *
     * @param username the username of the user whose plant count is to be retrieved
     * @return the count of plants owned by the user
     */
    int getNumberOfUserPlants(String username);

    /**
     * Increments the number of likes for the plant identified by its ID.
     *
     * @param fileID the ID of the plant to like
     * @return {@code true} if the like was successfully recorded, {@code false} otherwise
     */
    boolean likePlant(ObjectId fileID);;
}
