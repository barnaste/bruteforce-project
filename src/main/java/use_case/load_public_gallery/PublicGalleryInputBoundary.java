package use_case.load_public_gallery;

/**
 * Interface for the input boundary of the public gallery use case.
 *
 * <p>
 * This interface defines the methods required for interacting with the public gallery, including
 * executing the use case, navigating between pages, and retrieving information about public plants.
 */
public interface PublicGalleryInputBoundary {

    /**
     * Executes the public gallery use case with the given input data.
     *
     * @param galleryInputData the input data for the public gallery use case
     */
    void execute(PublicGalleryInputData galleryInputData);

    /**
     * Returns the total number of public plants.
     *
     * @return the number of public plants
     */
    int getNumberOfPublicPlants();

    /**
     * Advances to the next page in the public gallery.
     */
    void nextPage();

    /**
     * Moves to the previous page in the public gallery.
     */
    void previousPage();
}
