package use_case.delete_user;

/**
 * Output Data for the Logout Use Case.
 */
public class DeleteUserOutputData {
    private final String username;
    private final boolean useCaseFailed;

    public DeleteUserOutputData(String username, boolean useCaseFailed) {
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