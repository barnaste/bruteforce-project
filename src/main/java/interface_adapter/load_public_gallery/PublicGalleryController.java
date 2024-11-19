package interface_adapter.load_public_gallery;

import use_case.load_public_gallery.PublicGalleryInputBoundary;
import use_case.load_public_gallery.PublicGalleryInputData;

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

    public void loadNextPage() {
        galleryUseCaseInteractor.nextPage();
    }

    public void loadPreviousPage() {
        galleryUseCaseInteractor.previousPage();
    }
}
