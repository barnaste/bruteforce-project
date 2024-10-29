package data_access;

import entity.Plant;

/**
 * PlantDB is an interface that defines the methods that the PlantDB class must implement.
 */
public interface PlantDataBase {

    Plant[] getPlants(String username);



}
