package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import entity.Plant;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_user_gallery.UserGalleryController;
import interface_adapter.load_user_gallery.UserGalleryPresenter;
import interface_adapter.load_user_gallery.UserGalleryViewModel;
import interface_adapter.main.MainViewModel;
import use_case.load_user_gallery.UserGalleryInputBoundary;
import use_case.load_user_gallery.UserGalleryInteractor;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import view.gallery.UserGalleryView;

import java.util.function.Consumer;

public class UserGalleryFactory {
    /**
     * Create and return a user gallery view.
     * @param displayPlantMap the method to be called when the user gallery wishes to display a plant
     * @return the user gallery view
     */
    public static UserGalleryView createUserGallery(Consumer<Plant> displayPlantMap) {
        // Get instances of data access objects for plants, images, and users
        MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        MongoUserDataAccessObject userDataAccessObject = MongoUserDataAccessObject.getInstance();

        // Initialize the view model and gallery manager model
        UserGalleryViewModel viewModel = new UserGalleryViewModel();
        ViewManagerModel galleryManagerModel = new ViewManagerModel();

        // Set up the presenter and interactor for the user gallery
        UserGalleryOutputBoundary galleryPresenter = new UserGalleryPresenter(viewModel, galleryManagerModel);
        UserGalleryInputBoundary userGalleryInteractor = new UserGalleryInteractor(plantDataAccessObject, galleryPresenter, imageDataAccessObject, userDataAccessObject);

        // Create the controller and bind it to the user gallery view
        UserGalleryController userGalleryController = new UserGalleryController(userGalleryInteractor);
        viewModel.firePropertyChanged();  // Notify the view model of changes
        UserGalleryView userGalleryView = new UserGalleryView(viewModel, displayPlantMap);
        userGalleryView.setUserGalleryController(userGalleryController);

        // Return the fully initialized user gallery view
        return userGalleryView;
    }
}
