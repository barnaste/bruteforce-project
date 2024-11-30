package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */

public class InMemoryUserDataAccessObject implements use_case.UserDataAccessInterface {

    private static InMemoryUserDataAccessObject instance;

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    /**
     * Returns the singleton instance of InMemoryUserDataAccessObject.
     * If the instance does not exist, it is created.
     */
    public InMemoryUserDataAccessObject() {

    }

    /**
     * Retrieves the singleton instance of InMemoryUserDataAccessObject.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of InMemoryUserDataAccessObject
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
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public boolean deleteUser(String username) {
        return users.remove(username) != null;
    }

    /**
     * Clears all user data from the in-memory storage.
     */
    public void deleteAll() {
        users.clear();
    }
}
