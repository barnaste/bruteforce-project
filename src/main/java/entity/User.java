package entity;

/**
 * Represents a user in the system, with a username and a password.
 * Used for user authentication and management.
 */
public class User {
    private String username;
    private String password;

    /**
     * A constructor for User that initializes a user with the given username and password.
     * @param username is the username of the user
     * @param password is the password that the user set
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
