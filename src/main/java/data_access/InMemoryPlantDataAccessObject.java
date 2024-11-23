package data_access;

import entity.Plant;
import org.bson.types.ObjectId;
import use_case.PlantDataAccessInterface;

import java.util.*;


public class InMemoryPlantDataAccessObject implements PlantDataAccessInterface {

    private final Map<ObjectId, Plant> plants = new HashMap<>();

    @Override
    public List<Plant> getUserPlants(String username, int skip, int limit) {
        return List.of();
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
        return List.of();
    }

    @Override
    public void addPlant(Plant plant) {
        plant.setLastChanged(new Date());
        plant.setNumOfLikes(0);
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

}
