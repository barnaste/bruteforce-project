package app;

import data_access.MongoPlantDatabase;
import data_access.MongoUserDataBase;
import entity.Plant;
import entity.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        // final AppBuilder appBuilder = new AppBuilder();
        MongoPlantDatabase mongoPlantDatabase = new MongoPlantDatabase();
        List<Plant> plants = mongoPlantDatabase.getPublicPlants(20, 50);
        for (Plant plant : plants) {
            System.out.println(plant.getLastChanged());
        }
    }
}
