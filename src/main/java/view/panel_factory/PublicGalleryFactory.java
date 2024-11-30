package view.panel_factory;

import java.util.function.Consumer;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.ViewManagerModel;
import interface_adapter.like_plant.LikePlantController;
import interface_adapter.like_plant.LikePlantPresenter;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryPresenter;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import interface_adapter.main.MainViewModel;
import use_case.like_plant.LikePlantInteractor;
import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInteractor;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import view.gallery.PublicGalleryView;

/**
 * Controller for managing the public gallery's image pages. It interacts with the use case interactor
 * to load, paginate, and retrieve images for the public gallery.
 */
public class PublicGalleryFactory {
    /**
     * Creates and returns a PublicGalleryView, which represents the public gallery of plants.
     * This method sets up all the necessary components such as the ViewModel, Controller,
     * Interactor, and Presenter to support the functionality of displaying and interacting
     * with the public gallery, including liking plants.
     *
     * @param displayPlantMap The method to be called when the public gallery wishes to
     *                        display a plant. It takes a `Plant` object as an argument.
     * @param mainViewModel The MainViewModel used for managing the application's state
     *                      related to liking plants.
     * @return The PublicGalleryView, fully initialized and ready to be displayed, with
     *         the necessary controllers and interactors wired up for functionality.
     */
    public static PublicGalleryView createPublicGallery(Consumer<Plant> displayPlantMap, MainViewModel mainViewModel) {
        final MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        final MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        final PublicGalleryViewModel viewModel = new PublicGalleryViewModel();
        final ViewManagerModel galleryManagerModel = new ViewManagerModel();

        // Set up the PublicGalleryPresenter and PublicGalleryInteractor
        final PublicGalleryOutputBoundary galleryPresenter = new PublicGalleryPresenter(viewModel, galleryManagerModel);
        final PublicGalleryInputBoundary publicGalleryInteractor =
                new PublicGalleryInteractor(plantDataAccessObject, galleryPresenter, imageDataAccessObject);

        // Initialize the PublicGalleryController and View
        final PublicGalleryController publicGalleryController = new PublicGalleryController(publicGalleryInteractor);
        viewModel.firePropertyChanged();
        final PublicGalleryView publicGalleryView = new PublicGalleryView(viewModel, displayPlantMap);
        publicGalleryView.setPublicGalleryController(publicGalleryController);

        // Initialize the like use case
        final LikePlantPresenter likePlantPresenter = new LikePlantPresenter(mainViewModel);
        final LikePlantInteractor likePlantInteractor =
                new LikePlantInteractor(plantDataAccessObject, likePlantPresenter);
        final LikePlantController likePlantController = new LikePlantController(likePlantInteractor);
        publicGalleryView.setLikePlantController(likePlantController);

        return publicGalleryView;
    }
}
