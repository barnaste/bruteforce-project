package use_case.load_user_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * Represents the output data for the User Gallery use case.
 * Contains the images, their associated IDs, and pagination information
 * for displaying the user's plant gallery.
 */
public class UserGalleryOutputData {
    private final List<BufferedImage> images;
    private final List<ObjectId> ids;
    private final int page;
    private final int totalPages;

    public UserGalleryOutputData(List<BufferedImage> images, List<ObjectId> ids, int page, int totalPages) {
        this.images = images;
        this.page = page;
        this.totalPages = totalPages;
        this.ids = ids;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ObjectId> getIds() {
        return ids;
    }
}
