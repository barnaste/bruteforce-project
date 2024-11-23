package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import entity.Plant;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryPresenter;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInteractor;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import view.gallery.PublicGalleryView;

import java.util.function.Consumer;

public class PublicGalleryFactory {
    /**
     * Create and return a public gallery view.
     * @param viewModel the public gallery view model to be used
     * @param displayPlant the method to be called when the public gallery wishes to display a plant
     * @return the public gallery view
     */
    public static PublicGalleryView createPublicGallery(PublicGalleryViewModel viewModel,
                                                        Consumer<Plant> displayPlant) {
        MongoPlantDataAccessObject plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        MongoImageDataAccessObject imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        ViewManagerModel galleryManagerModel = new ViewManagerModel();

        // Set up the PublicGalleryPresenter and PublicGalleryInteractor
        PublicGalleryOutputBoundary galleryPresenter = new PublicGalleryPresenter(viewModel, galleryManagerModel);
        PublicGalleryInputBoundary publicGalleryInteractor = new PublicGalleryInteractor(plantDataAccessObject, galleryPresenter, imageDataAccessObject);

        // Initialize the PublicGalleryController and View
        PublicGalleryController publicGalleryController = new PublicGalleryController(publicGalleryInteractor);
        viewModel.firePropertyChanged();
        PublicGalleryView publicGalleryView = new PublicGalleryView(viewModel, displayPlant);
        publicGalleryView.setPublicGalleryController(publicGalleryController);

        // Load the first page by default
        publicGalleryController.loadPage(0);

        return publicGalleryView;
    }
}
