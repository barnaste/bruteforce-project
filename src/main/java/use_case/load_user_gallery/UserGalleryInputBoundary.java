package use_case.load_user_gallery;

/**
 * Interface defining the input boundary for the user gallery use case.
 * This interface is responsible for managing the logic related to retrieving
 * and navigating through a user's gallery of plants. It includes methods for
 * executing the use case, getting the number of plants, and handling pagination.
 */
public interface UserGalleryInputBoundary {

    /**
     * Executes the user gallery use case with the provided input data.
     *
     * @param galleryInputData the input data containing the page number to load.
     */
    void execute(UserGalleryInputData galleryInputData);

    /**
     * Returns the total number of plants belonging to the current user.
     *
     * @return the total number of plants in the user's gallery.
     */
    int getNumberOfUserPlants();

    /**
     * Loads the next page of the user's plant gallery.
     * This method updates the current page and fetches the corresponding set of plants.
     */
    void nextPage();

    /**
     * Loads the previous page of the user's plant gallery.
     * This method updates the current page and fetches the corresponding set of plants.
     */
    void previousPage();
}
