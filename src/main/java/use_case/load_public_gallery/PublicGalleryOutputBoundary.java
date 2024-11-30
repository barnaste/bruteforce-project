package use_case.load_public_gallery;

/**
 * Interface for the output boundary of the public gallery use case.
 * Responsible for presenting the data to the user interface after the gallery data is processed.
 */
public interface PublicGalleryOutputBoundary {

    /**
     * Prepares and presents the success view of the public gallery with the given output data.
     *
     * @param outputData the data to be displayed in the public gallery view, including images and
     *                   pagination information
     */
    void prepareSuccessView(PublicGalleryOutputData outputData);
}
