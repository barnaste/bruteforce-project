package interface_adapter.delete_user;

import use_case.delete_user.DeleteUserInputBoundry;
import use_case.delete_user.DeleteUserInputData;
import use_case.delete_user.DeleteUserInteractor;
import use_case.logout.LogoutInputData;

public class DeleteUserController {
    private DeleteUserInputBoundry deleteUserUseCaseInteractor;

    public DeleteUserController(DeleteUserInputBoundry deleteUseCaseInteractor) {
        this.deleteUserUseCaseInteractor = deleteUseCaseInteractor;
    }

    /**
     * Executes the Logout Use Case.
     * @param username the username of the user who is logged in
     */
    public void execute(String username, String password) {
        final DeleteUserInputData deleteUserInputData = new DeleteUserInputData(username, password);
        deleteUserUseCaseInteractor.execute(deleteUserInputData);
    }
}
