package use_case.signup;

import entity.User;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

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
}
