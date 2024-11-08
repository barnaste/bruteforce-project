package interface_adapter.load_public_gallery;

import interface_adapter.ViewModel;

public class PublicGalleryViewModel extends ViewModel<PublicGalleryState>{
    public PublicGalleryViewModel() {
        super("public_gallery");
        setState(new PublicGalleryState());
    }
}
