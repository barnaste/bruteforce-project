package use_case.logout;

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {
    private final String username;
    private final boolean useCaseFailed;

    public LogoutOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}