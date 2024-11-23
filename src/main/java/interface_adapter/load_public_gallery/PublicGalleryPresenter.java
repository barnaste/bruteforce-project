package interface_adapter.load_public_gallery;

import interface_adapter.ViewManagerModel;
import use_case.load_public_gallery.PublicGalleryOutputBoundary;
import use_case.load_public_gallery.PublicGalleryOutputData;

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
        PublicGalleryState galleryState = publicGalleryViewModel.getState();
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
