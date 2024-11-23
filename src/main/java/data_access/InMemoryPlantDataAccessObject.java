package data_access;

import entity.Plant;
import org.bson.types.ObjectId;
import use_case.PlantDataAccessInterface;

import java.util.*;
import java.util.stream.Collectors;


public class InMemoryPlantDataAccessObject implements PlantDataAccessInterface {

    private final Map<ObjectId, Plant> plants = new HashMap<>();

    private static InMemoryPlantDataAccessObject instance;

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private InMemoryPlantDataAccessObject() {}

    /**
     * The method used to retrieve an instance of this class. This way, the DAO is maintained as a singleton.
     */
    public static InMemoryPlantDataAccessObject getInstance() {
        if (instance == null) {
            instance = new InMemoryPlantDataAccessObject();
        }
        return instance;
    }

    @Override
    public List<Plant> getUserPlants(String username, int skip, int limit) {

        List<Plant> sortedplants = getUserPlants(username); // Reused the single-parameter version

        int start = Math.min(skip, sortedplants.size());
        int end = Math.min(start + limit, sortedplants.size());

        return sortedplants.subList(start, end);

    }

    @Override
    public List<Plant> getUserPlants(String username) {

        List<Plant> result = new ArrayList<>();
        for (Plant plant : plants.values()) {
            if (plant.getOwner().equals(username)) {
                result.add(plant);
            }
        }
        result.sort((p1, p2) -> p2.getLastChanged().compareTo(p1.getLastChanged()));
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
        Plant plant = plants.get(fileID);
        if (plant != null) {
            plant.setIsPublic(isPublic);
            plant.setComments(comments);
            plant.setLastChanged(new Date());
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        return plants.remove(fileID) != null;
    }

    @Override
    public int getNumberOfPublicPlants() {
        int count = 0;
        for (Plant plant : plants.values()) {
            if (plant.getIsPublic()){
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
        Plant plant = plants.get(fileID);
        if (plant != null) {
            plant.setNumOfLikes(plant.getNumOfLikes() + 1);
            return true;
        }
        return false;
    }

    public void deleteAll(){
        plants.clear();
    }

}
