package app;

import data_access.MongoUserDataBase;

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
        MongoUserDataBase db = new MongoUserDataBase();
        System.out.println(db.getUser("nezere").getPassword());
    }
}
