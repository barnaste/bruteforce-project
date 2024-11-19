package use_case.load_public_gallery;

public interface PublicGalleryOutputBoundary {
    void prepareSuccessView(PublicGalleryOutputData outputData);
    void prepareFailView();
}
