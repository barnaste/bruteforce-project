package interface_adapter.load_public_gallery;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import use_case.load_public_gallery.PublicGalleryOutputData;

public class PublicGalleryPresenter implements PublicGalleryOutputBoundary {

    private final PublicGalleryViewModel publicGalleryViewModel;
    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;

    public PublicGalleryPresenter(PublicGalleryViewModel publicGalleryViewModel, MainViewModel mainViewModel, ViewManagerModel viewManagerModel) {
        this.publicGalleryViewModel = publicGalleryViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView(PublicGalleryOutputData response) {
        // Update the gallery view with images.
        final MainState mainState = mainViewModel.getState();
        mainState.setPublic();
        this.mainViewModel.setState(mainState);
        this.mainViewModel.firePropertyChanged();

        this.viewManagerModel.setState(mainViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final PublicGalleryState galleryState = publicGalleryViewModel.getState();
        galleryState.setGalleryError(error);
        publicGalleryViewModel.firePropertyChanged();
    }

    @Override
    public void switchToPublicGalleryView(PublicGalleryOutputData response) {
        viewManagerModel.setState("public_gallery");
        viewManagerModel.firePropertyChanged();
    }
}
