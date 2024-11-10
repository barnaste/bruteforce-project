package interface_adapter.load_user_gallery;

import interface_adapter.ViewModel;

public class UserGalleryViewModel extends ViewModel<UserGalleryState>{
    public UserGalleryViewModel() {
        super("user_gallery");
        setState(new UserGalleryState());
    }
}
