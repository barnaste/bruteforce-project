package interface_adapter.load_user_gallery;

import use_case.load_user_gallery.UserGalleryInputData;
import use_case.load_user_gallery.UserGalleryInputBoundary;

public class UserGalleryController {

    private final UserGalleryInputBoundary galleryUseCaseInteractor;

    public UserGalleryController(UserGalleryInputBoundary galleryUseCaseInteractor) {
        this.galleryUseCaseInteractor = galleryUseCaseInteractor;
    }

    /**
     * Executes the Public Gallery Use Case.
     * @param page the page of images to load
     */
    public void loadPage(String username, int page) {
        final UserGalleryInputData galleryInputData = new UserGalleryInputData(username, page);
        galleryUseCaseInteractor.execute(galleryInputData);
    }
}
