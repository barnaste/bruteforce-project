package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;

    public LoginOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
