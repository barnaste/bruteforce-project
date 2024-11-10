package interface_adapter.load_user_gallery;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import use_case.load_user_gallery.UserGalleryOutputData;

public class UserGalleryPresenter implements UserGalleryOutputBoundary {

    private final UserGalleryViewModel userGalleryViewModel;
    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;

    public UserGalleryPresenter(UserGalleryViewModel userGalleryViewModel, MainViewModel mainViewModel, ViewManagerModel viewManagerModel) {
        this.userGalleryViewModel = userGalleryViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView(UserGalleryOutputData response) {
        // Update the user gallery state in the view model
        UserGalleryState galleryState = userGalleryViewModel.getState();
        galleryState.setPlantImages(response.getImages());
        userGalleryViewModel.firePropertyChanged();

        // Update the main state to reflect the gallery mode as user
        MainState mainState = mainViewModel.getState();
        mainViewModel.setState(mainState);
        mainViewModel.firePropertyChanged();

        // Transition to the user gallery view
        switchToUserGalleryView(response);

        this.viewManagerModel.setState(mainViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final UserGalleryState galleryState = userGalleryViewModel.getState();
        galleryState.setGalleryError(error);
        userGalleryViewModel.firePropertyChanged();
    }

    @Override
    public void switchToUserGalleryView(UserGalleryOutputData response) {
        viewManagerModel.setState("user_gallery");
        viewManagerModel.firePropertyChanged();
    }
}
