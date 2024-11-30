package interface_adapter.load_public_gallery;

import interface_adapter.ViewManagerModel;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import use_case.load_public_gallery.PublicGalleryOutputData;

/**
 * Presenter for the public gallery, responsible for updating the view model with the gallery data.
 * Implements the {@link PublicGalleryOutputBoundary} to handle the preparation of the success view.
 */
public class PublicGalleryPresenter implements PublicGalleryOutputBoundary {

    private final PublicGalleryViewModel publicGalleryViewModel;
    private final ViewManagerModel viewManagerModel;

    public PublicGalleryPresenter(PublicGalleryViewModel publicGalleryViewModel, ViewManagerModel viewManagerModel) {
        this.publicGalleryViewModel = publicGalleryViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(PublicGalleryOutputData outputData) {
        // Update the public gallery state in the view model
        final PublicGalleryState galleryState = publicGalleryViewModel.getState();
        galleryState.setPlantImages(outputData.getImages());
        galleryState.setCurrentPage(outputData.getPage());
        galleryState.setTotalPages(outputData.getTotalPages());
        galleryState.setPlantID(outputData.getIds());

        publicGalleryViewModel.setState(galleryState);
        publicGalleryViewModel.firePropertyChanged();

        viewManagerModel.setState(publicGalleryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
