package use_case.load_user_gallery;

/**
 * Represents the input data required for the user gallery use case, specifically the page number to load.
 * This class encapsulates the page information that will be used to fetch a specific subset of the user's plants.
 */
public class UserGalleryInputData {
    private final int page;

    public UserGalleryInputData(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
