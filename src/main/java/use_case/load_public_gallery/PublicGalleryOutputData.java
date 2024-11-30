package use_case.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * Data transfer object (DTO) that contains the output data for the public gallery.
 * Holds the images, their IDs, current page number, and total pages for gallery pagination.
 */
public class PublicGalleryOutputData {
    private final List<BufferedImage> images;
    private final List<ObjectId> ids;
    private final int page;
    private final int totalPages;

    public PublicGalleryOutputData(List<BufferedImage> images, List<ObjectId> ids, int page, int totalPages) {
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
