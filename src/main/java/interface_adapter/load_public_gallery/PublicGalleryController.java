package interface_adapter.load_public_gallery;

import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInputData;

/**
 * Controller for managing the public gallery's image pages. It interacts with the use case interactor
 * to load, paginate, and retrieve images for the public gallery.
 */
public class PublicGalleryController {

    private final PublicGalleryInputBoundary galleryUseCaseInteractor;

    public PublicGalleryController(PublicGalleryInputBoundary galleryUseCaseInteractor) {
        this.galleryUseCaseInteractor = galleryUseCaseInteractor;
    }

    /**
     * Executes the Public Gallery Use Case.
     * @param page the page of images to load
     */
    public void loadPage(int page) {
        final PublicGalleryInputData galleryInputData = new PublicGalleryInputData(page);
        galleryUseCaseInteractor.execute(galleryInputData);
    }

    /**
     * Loads the next page of the public gallery by invoking the `nextPage` method in the use case interactor.
     */
    public void loadNextPage() {
        galleryUseCaseInteractor.nextPage();
    }

    /**
     * Loads the previous page of the public gallery by invoking the `previousPage` method in the use case interactor.
     */
    public void loadPreviousPage() {
        galleryUseCaseInteractor.previousPage();
    }
}
