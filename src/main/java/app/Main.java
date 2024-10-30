package app;

import data_access.MongoUserDataBase;
import entity.User;

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
        User user = new User("ali karim lalani", "mario");
        MongoUserDataBase db = new MongoUserDataBase();
        db.addUser(user);
        System.out.println(db.getUser(user.getUsername()).getUsername());
    }
}
