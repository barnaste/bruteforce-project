package use_case.delete_user;

/**
 * The Input Data for the Delete User Case.
 */
public class DeleteUserInputData {
    private final String username;

    public DeleteUserInputData(String username) {

        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}