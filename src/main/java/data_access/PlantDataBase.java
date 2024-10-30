package data_access;

import entity.Plant;

import java.util.List;

/**
 * PlantDB is an interface that defines the methods that the PlantDB class must implement.
 */
public interface PlantDataBase {
    /**
     * A method that returns all the plants owned by a user in the database.
     * @param username is the username of the user
     * @return a list of all the plants belonging to that user
     */
    List<Plant> getPlants(String username);

    /**
     * A method that inserts a new plant into the database.
     * @param plant is the plant being inserted
     */
    void addPlant(Plant plant);
}
