package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import entity.Plant;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryPresenter;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import interface_adapter.load_user_gallery.UserGalleryController;
import interface_adapter.load_user_gallery.UserGalleryPresenter;
import interface_adapter.load_user_gallery.UserGalleryViewModel;
import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInteractor;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import use_case.load_user_gallery.UserGalleryInputBoundary;
import use_case.load_user_gallery.UserGalleryInteractor;
import use_case.load_user_gallery.UserGalleryOutputBoundary;
import view.gallery.PublicGalleryView;
import view.gallery.UserGalleryView;

import java.util.function.Consumer;

public class UserGalleryFactory {
    /**
     * Create and return a user gallery view.
     * @param displayPlantMap the method to be called when the user gallery wishes to display a plant
     * @return the user gallery view
     */
    public static UserGalleryView createUserGallery(Consumer<Plant> displayPlantMap) {
        MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        MongoUserDataAccessObject userDataAccessObject = MongoUserDataAccessObject.getInstance();
        UserGalleryViewModel viewModel = new UserGalleryViewModel();
        ViewManagerModel galleryManagerModel = new ViewManagerModel();

        UserGalleryOutputBoundary galleryPresenter = new UserGalleryPresenter(viewModel, galleryManagerModel);
        UserGalleryInputBoundary userGalleryInteractor = new UserGalleryInteractor(plantDataAccessObject, galleryPresenter , imageDataAccessObject, userDataAccessObject);

        UserGalleryController userGalleryController = new UserGalleryController(userGalleryInteractor);
        viewModel.firePropertyChanged();
        UserGalleryView userGalleryView = new UserGalleryView(viewModel, displayPlantMap);
        userGalleryView.setUserGalleryController(userGalleryController);

        // Load the first page by default
        userGalleryController.loadPage(0);

        return userGalleryView;
    }
}
