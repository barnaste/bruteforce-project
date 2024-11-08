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
    List<Plant> getPlants(String username, int skip, int limit);

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
     * A method that inserts a new plant into the database.
     * @param fileID is the fileID of the plant being modified
     * @param newPlant is the plant object containing information to be updated with
     * @return true if the plant has been successfully modified, false otherwise
     */
    boolean editPlant(ObjectId fileID, Plant newPlant);

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
     * Retrieves the total number of plants owned by a specific user.
     *
     * @param username the username of the user whose plant count is to be retrieved
     * @return the count of plants owned by the user
     */
    int getNumberOfUserPlants(String username);
}
