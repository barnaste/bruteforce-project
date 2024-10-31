package use_case.login;

import entity.User;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

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
    //TODO: Move this to where it's needed later.
}
