package interface_adapter.load_public_gallery;

import interface_adapter.ViewModel;

/**
 * ViewModel for managing the state of the public gallery view.
 * This class extends the generic ViewModel class and is responsible for holding and updating
 * the state of the public gallery, such as the list of public plants and navigation state.
 */
public class PublicGalleryViewModel extends ViewModel<PublicGalleryState> {
    public PublicGalleryViewModel() {
        super("public_gallery");
        setState(new PublicGalleryState());
    }
}
