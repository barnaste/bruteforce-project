package interface_adapter.load_user_gallery;

import use_case.load_user_gallery.UserGalleryInputBoundary;
import use_case.load_user_gallery.UserGalleryInputData;

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

    public void loadNextPage() {
        galleryUseCaseInteractor.nextPage();
    }

    public void loadPreviousPage() {
        galleryUseCaseInteractor.previousPage();
    }
}
