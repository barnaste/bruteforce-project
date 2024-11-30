package interface_adapter.load_user_gallery;

import interface_adapter.ViewModel;

/**
 * ViewModel for the User Gallery, responsible for managing and storing the state of the user gallery view.
 * Extends the generic ViewModel class and provides a specific state for user gallery functionality.
 */
public class UserGalleryViewModel extends ViewModel<UserGalleryState> {
    public UserGalleryViewModel() {
        super("user_gallery");
        setState(new UserGalleryState());
    }
}
