package interface_adapter.load_user_gallery;

import interface_adapter.ViewManagerModel;
import interface_adapter.load_public_gallery.PublicGalleryState;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import use_case.load_public_gallery.PublicGalleryOutputData;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import use_case.load_user_gallery.UserGalleryOutputData;

public class UserGalleryPresenter implements UserGalleryOutputBoundary {
    private final UserGalleryViewModel userGalleryViewModel;
    private final ViewManagerModel viewManagerModel;

    public UserGalleryPresenter(UserGalleryViewModel publicGalleryViewModel, ViewManagerModel viewManagerModel) {
        this.userGalleryViewModel = publicGalleryViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(UserGalleryOutputData outputData) {
        // Update the public gallery state in the view model
        UserGalleryState galleryState = userGalleryViewModel.getState();
        galleryState.setPlantImages(outputData.getImages());
        galleryState.setCurrentPage(outputData.getPage());
        galleryState.setTotalPages(outputData.getTotalPages());

        userGalleryViewModel.setState(galleryState);
        userGalleryViewModel.firePropertyChanged();

        viewManagerModel.setState(userGalleryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView() {
        userGalleryViewModel.firePropertyChanged();
    }
}
