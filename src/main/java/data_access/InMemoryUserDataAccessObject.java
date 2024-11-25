package data_access;

import entity.User;

import java.util.HashMap;
import java.util.Map;


/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */

public class InMemoryUserDataAccessObject implements use_case.UserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    private static InMemoryUserDataAccessObject instance;

    public InMemoryUserDataAccessObject() {}

    /**
     * The method used to retrieve an instance of this class. This way, the DAO is maintained as a singleton.
     */
    public static InMemoryUserDataAccessObject getInstance() {
        if (instance == null) {
            instance = new InMemoryUserDataAccessObject();
        }
        return instance;
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public void addUser(User user) { users.put(user.getUsername(), user);}

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {this.currentUsername = username;}

    @Override
    public User getUser(String username) {return users.get(username);}

    @Override
    public boolean deleteUser(String username) {return users.remove(username) != null;}
}
