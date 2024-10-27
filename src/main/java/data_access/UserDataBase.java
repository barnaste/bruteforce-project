package data_access;

import entity.User;

/**
 * UserDB is an interface that defines the methods that the UserDB class must implement.
 */
public interface UserDataBase {
    /**
     * A method that returns the information of a user in the database.
     * @param username is the username of the user.
     * @return the user corresponding to the username, null if not found.
     */
    User getUser(String username);

    /**
     * A method that adds a new user entry into the database (if the username differs from existing ones)
     * @param user is the user being added.
     * @return whether the user has been successfully added.
     */
    boolean addUser(User user);
}
