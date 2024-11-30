package view.panel_factory;

import java.util.function.Consumer;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import entity.Plant;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_user_gallery.UserGalleryController;
import interface_adapter.load_user_gallery.UserGalleryPresenter;
import interface_adapter.load_user_gallery.UserGalleryViewModel;
import use_case.load_user_gallery.UserGalleryInputBoundary;
import use_case.load_user_gallery.UserGalleryInteractor;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import view.gallery.UserGalleryView;

/**
 * Factory class to create and initialize the User Gallery view along with its associated components.
 * This includes setting up the view model, controller, interactor, and presenter.
 */
public class UserGalleryFactory {
    /**
     * Create and return a user gallery view.
     * @param displayPlantMap the method to be called when the user gallery wishes to display a plant
     * @return the user gallery view
     */
    public static UserGalleryView createUserGallery(Consumer<Plant> displayPlantMap) {
        // Get instances of data access objects for plants, images, and users
        final MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        final MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        final MongoUserDataAccessObject userDataAccessObject = MongoUserDataAccessObject.getInstance();

        // Initialize the view model and gallery manager model
        final UserGalleryViewModel viewModel = new UserGalleryViewModel();
        final ViewManagerModel galleryManagerModel = new ViewManagerModel();

        // Set up the presenter and interactor for the user gallery
        final UserGalleryOutputBoundary galleryPresenter = new UserGalleryPresenter(viewModel, galleryManagerModel);
        final UserGalleryInputBoundary userGalleryInteractor = new UserGalleryInteractor(plantDataAccessObject,
                galleryPresenter, imageDataAccessObject, userDataAccessObject);

        // Create the controller and bind it to the user gallery view
        final UserGalleryController userGalleryController = new UserGalleryController(userGalleryInteractor);
        viewModel.firePropertyChanged();
        final UserGalleryView userGalleryView = new UserGalleryView(viewModel, displayPlantMap);
        userGalleryView.setUserGalleryController(userGalleryController);

        // Return the fully initialized user gallery view
        return userGalleryView;
    }
}
