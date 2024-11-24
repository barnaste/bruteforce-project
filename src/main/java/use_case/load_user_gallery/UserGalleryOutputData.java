package use_case.load_user_gallery;

import org.bson.types.ObjectId;

import java.awt.image.BufferedImage;
import java.util.List;

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
