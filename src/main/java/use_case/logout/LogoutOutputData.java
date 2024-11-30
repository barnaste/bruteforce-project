package use_case.logout;

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {
    private final String username;

    public LogoutOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
