package use_case.load_user_gallery;

/**
 * The output boundary interface for the User Gallery use case.
 * It defines the contract for preparing and displaying the output data
 * for the user gallery, typically to be consumed by the view layer.
 */
public interface UserGalleryOutputBoundary {

    /**
     * Prepares the success view for the user gallery.
     *
     * @param outputData the data to be displayed in the user gallery view
     */
    void prepareSuccessView(UserGalleryOutputData outputData);
}
