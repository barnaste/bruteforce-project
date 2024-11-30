package interface_adapter.delete_user;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Delete User view, responsible for managing the state and logic
 * related to deleting a user account. Inherits from ViewModel.
 */
public class DeleteUserViewModel extends ViewModel<Object> {

    public static final String LOGIN_ERROR_MESSAGE = "Invalid combination. Try again!";

    /**
     * Constructs a DeleteUserViewModel with the view name "delete_user".
     */
    public DeleteUserViewModel() {
        super("delete_user");
    }
}
