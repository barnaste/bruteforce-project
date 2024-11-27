package interface_adapter.delete_user;

import use_case.delete_user.DeleteUserInputBoundary;
import use_case.delete_user.DeleteUserInputData;

/**
 * The DeleteUserController is responsible for handling user interactions related to
 * the delete user use case, including executing the deletion logic and managing escape actions.
 */
public class DeleteUserController {

    private DeleteUserInputBoundary deleteUserUseCaseInteractor;  // Interactor to handle the delete user use case logic

    /**
     * Constructs a DeleteUserController with the specified interactor.
     *
     * @param deleteUseCaseInteractor the interactor that contains the logic for deleting a user
     */
    public DeleteUserController(DeleteUserInputBoundary deleteUseCaseInteractor) {
        this.deleteUserUseCaseInteractor = deleteUseCaseInteractor;
    }

    /**
     * Executes the delete user use case by passing the provided username and password
     * to the interactor for validation and processing.
     *
     * @param username the username of the user to be deleted
     * @param password the password of the user to be deleted
     */
    public void execute(String username, String password) {
        // Create input data for the interactor
        final DeleteUserInputData deleteUserInputData = new DeleteUserInputData(username, password);
        // Delegate the execution to the interactor
        deleteUserUseCaseInteractor.execute(deleteUserInputData);
    }

    /**
     * Executes the escape action (usually to navigate away from the current screen).
     */
    public void escape() {
        // Trigger the escape action defined in the interactor
        deleteUserUseCaseInteractor.escape();
    }
}
