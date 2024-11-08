package use_case.load_public_gallery;

public interface PublicGalleryInputBoundary {
    /**
     * Executes the public gallery use case.
     * @param galleryInputData the input data
     */
    void execute(PublicGalleryInputData galleryInputData);
}
