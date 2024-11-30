package data_access;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import entity.Plant;
import use_case.PlantDataAccessInterface;

/**
 * In-memory implementation of the PlantDataAccessInterface for managing plant data.
 * This class follows the Singleton pattern to ensure a single instance for plant data storage.
 */
public final class InMemoryPlantDataAccessObject implements PlantDataAccessInterface {
    private static InMemoryPlantDataAccessObject instance;

    private final Map<ObjectId, Plant> plants = new HashMap<>();

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private InMemoryPlantDataAccessObject() {

    }

    /**
     * Returns the singleton instance of InMemoryPlantDataAccessObject.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of InMemoryPlantDataAccessObject
     */
    public static InMemoryPlantDataAccessObject getInstance() {
        if (instance == null) {
            instance = new InMemoryPlantDataAccessObject();
        }
        return instance;
    }

    @Override
    public List<Plant> getUserPlants(String username, int skip, int limit) {

        // Reused the single-parameter version
        final List<Plant> sortedPlants = getUserPlants(username);

        final int start = Math.min(skip, sortedPlants.size());
        final int end = Math.min(start + limit, sortedPlants.size());

        return sortedPlants.subList(start, end);

    }

    @Override
    public List<Plant> getUserPlants(String username) {
        final List<Plant> result = new ArrayList<>();
        for (Plant plant : plants.values()) {
            if (plant.getOwner().equals(username)) {
                result.add(plant);
            }
        }
        result.sort((evt1, evt2) -> evt2.getLastChanged().compareTo(evt1.getLastChanged()));
        return result;
    }

    @Override
    public List<Plant> getPublicPlants(int skip, int limit) {
        return plants.values().stream()
                .filter(Plant::getIsPublic)
                .sorted(Comparator.comparing(Plant::getNumOfLikes).reversed())
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public void addPlant(Plant plant) {
        plant.setLastChanged(new Date());
        plants.put(plant.getFileID(), plant);
    }

    @Override
    public boolean editPlant(ObjectId fileID, boolean isPublic, String comments) {
        boolean result = false;
        final Plant plant = plants.get(fileID);
        if (plant != null) {
            plant.setIsPublic(isPublic);
            plant.setComments(comments);
            plant.setLastChanged(new Date());
            result = true;
        }
        return result;
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        return plants.remove(fileID) != null;
    }

    @Override
    public int getNumberOfPublicPlants() {
        int count = 0;
        for (Plant plant : plants.values()) {
            if (plant.getIsPublic()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Plant fetchPlantByID(ObjectId fileID) {
        return plants.get(fileID);
    }

    @Override
    public int getNumberOfUserPlants(String username) {
        int count = 0;
        for (Plant plant : plants.values()) {
            if (plant.getOwner().equals(username)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean likePlant(ObjectId fileID) {
        boolean result = false;
        final Plant plant = plants.get(fileID);
        if (plant != null) {
            plant.setNumOfLikes(plant.getNumOfLikes() + 1);
            result = true;
        }
        return result;
    }

    /**
     * Clears all plants from the in-memory storage.
     */
    public void deleteAll() {
        plants.clear();
    }
}
