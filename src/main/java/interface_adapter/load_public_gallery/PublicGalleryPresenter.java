package interface_adapter.load_public_gallery;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import use_case.load_public_gallery.PublicGalleryOutputData;

public class PublicGalleryPresenter implements PublicGalleryOutputBoundary {

    private final PublicGalleryViewModel publicGalleryViewModel;
    private final ViewManagerModel viewManagerModel;

    public PublicGalleryPresenter(PublicGalleryViewModel publicGalleryViewModel, MainViewModel mainViewModel, ViewManagerModel viewManagerModel) {
        this.publicGalleryViewModel = publicGalleryViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(PublicGalleryOutputData response) {
        // Update the public gallery state in the view model
        PublicGalleryState galleryState = publicGalleryViewModel.getState();
        galleryState.setPlantImages(response.getImages());
        galleryState.setCurrentPage(response.getPage());
        galleryState.setTotalPages(response.getTotalPages());

        publicGalleryViewModel.setState(galleryState);
        publicGalleryViewModel.firePropertyChanged();

        this.viewManagerModel.setState(publicGalleryViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final PublicGalleryState galleryState = publicGalleryViewModel.getState();
        galleryState.setGalleryError(error);
        publicGalleryViewModel.firePropertyChanged();
    }

}
