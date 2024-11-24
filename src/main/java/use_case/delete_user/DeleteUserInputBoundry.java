package use_case.delete_user;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface DeleteUserInputBoundry {

    /**
     * Executes the Delete User use case.
     * @param deleteUserInputData the input data
     */
    void execute(DeleteUserInputData deleteUserInputData);

}
