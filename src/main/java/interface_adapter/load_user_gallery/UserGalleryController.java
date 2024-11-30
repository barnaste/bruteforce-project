package interface_adapter.load_user_gallery;

import use_case.load_user_gallery.UserGalleryInputBoundary;
import use_case.load_user_gallery.UserGalleryInputData;

/**
 * Controller for managing the user gallery use case.
 * It interacts with the `UserGalleryInputBoundary` to load user-specific gallery pages
 * and handle page navigation (next/previous).
 */
public class UserGalleryController {
    private final UserGalleryInputBoundary galleryUseCaseInteractor;

    public UserGalleryController(UserGalleryInputBoundary galleryUseCaseInteractor) {
        this.galleryUseCaseInteractor = galleryUseCaseInteractor;
    }

    /**
     * Executes the Public Gallery Use Case.
     * @param page the page of images to load
     */
    public void loadPage(int page) {
        final UserGalleryInputData galleryInputData = new UserGalleryInputData(page);
        galleryUseCaseInteractor.execute(galleryInputData);
    }

    /**
     * Loads the next page of the user gallery.
     * This method delegates the page change action to the interactor's nextPage method.
     */
    public void loadNextPage() {
        galleryUseCaseInteractor.nextPage();
    }

    /**
     * Loads the previous page of the user gallery.
     * This method delegates the page change action to the interactor's previousPage method.
     */
    public void loadPreviousPage() {
        galleryUseCaseInteractor.previousPage();
    }
}
