package interface_adapter.load_user_gallery;

import interface_adapter.ViewManagerModel;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import use_case.load_user_gallery.UserGalleryOutputData;

/**
 * The presenter for the User Gallery use case.
 * Prepares and updates the user gallery view with the data to be displayed.
 */
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
        final UserGalleryState galleryState = userGalleryViewModel.getState();
        galleryState.setPlantImages(outputData.getImages());
        galleryState.setCurrentPage(outputData.getPage());
        galleryState.setTotalPages(outputData.getTotalPages());
        galleryState.setPlantID(outputData.getIds());

        userGalleryViewModel.setState(galleryState);
        userGalleryViewModel.firePropertyChanged();

        viewManagerModel.setState(userGalleryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
