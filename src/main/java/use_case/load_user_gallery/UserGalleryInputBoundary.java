package use_case.load_user_gallery;

public interface UserGalleryInputBoundary {
    /**
     * Executes the user gallery use case.
     * @param galleryInputData the input data
     */
    void execute(UserGalleryInputData galleryInputData);
}
