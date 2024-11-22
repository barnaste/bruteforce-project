package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_public_gallery.PublicGalleryController;
import interface_adapter.load_public_gallery.PublicGalleryPresenter;
import interface_adapter.load_public_gallery.PublicGalleryViewModel;
import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInteractor;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import view.gallery.PublicGalleryView;

public class PublicGalleryFactory {
    public static PublicGalleryView createPublicGallery(PublicGalleryViewModel viewModel) {
        MongoPlantDataAccessObject plantDataAccessObject = new MongoPlantDataAccessObject();
        MongoImageDataAccessObject imageDataAccessObject = new MongoImageDataAccessObject();
        ViewManagerModel galleryManagerModel = new ViewManagerModel();

        // Set up the PublicGalleryPresenter and PublicGalleryInteractor
        PublicGalleryOutputBoundary galleryPresenter = new PublicGalleryPresenter(viewModel, galleryManagerModel);
        PublicGalleryInputBoundary publicGalleryInteractor = new PublicGalleryInteractor(plantDataAccessObject, galleryPresenter, imageDataAccessObject);

        // Initialize the PublicGalleryController and View
        PublicGalleryController publicGalleryController = new PublicGalleryController(publicGalleryInteractor);
        viewModel.firePropertyChanged();
        PublicGalleryView publicGalleryView = new PublicGalleryView(viewModel);
        publicGalleryView.setPublicGalleryController(publicGalleryController);

        // Load the first page by default
        publicGalleryController.loadPage(0);

        return publicGalleryView;
    }
}
