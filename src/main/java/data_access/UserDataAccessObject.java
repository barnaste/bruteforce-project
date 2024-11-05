package data_access;

import entity.User;

public interface UserDataAccessObject {
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * A method that adds a new user entry into the database
     * @param user is the user being added.
     */
    void addUser(User user);

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    void setCurrentUsername(String username);

    /**
     * A method that returns the information of a user in the database.
     * @param username is the username of the user.
     * @return the user corresponding to the username, null if not found.
     */
    User getUser(String username);

    /**
     * A method that deletes a user from the database.
     * @param username is the username of the user.
     * @return true if the user was successfully deleted, false otherwise.
     */
    boolean deleteUser(String username);
}
