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
        // Update the public gallery state in the view model
        PublicGalleryState galleryState = publicGalleryViewModel.getState();
        galleryState.setPlantImages(response.getImages());
        publicGalleryViewModel.firePropertyChanged();

        // Update the main state to reflect the gallery mode as public
        MainState mainState = mainViewModel.getState();
        mainState.setPublic();
        mainViewModel.setState(mainState);
        mainViewModel.firePropertyChanged();

        // Transition to the public gallery view
        switchToPublicGalleryView(response);

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
