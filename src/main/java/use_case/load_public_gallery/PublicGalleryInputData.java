package use_case.load_public_gallery;

/**
 * Contains input data for accessing the public gallery, specifically the page number.
 */
public class PublicGalleryInputData {
    private final int page;

    public PublicGalleryInputData(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
