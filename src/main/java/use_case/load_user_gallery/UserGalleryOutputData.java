package use_case.load_user_gallery;

import java.util.List;
import java.awt.image.BufferedImage;

public class UserGalleryOutputData {
    private final List<BufferedImage> images;
    private final int totalPages;

    public UserGalleryOutputData(List<BufferedImage> images, int totalPages) {
        this.images = images;
        this.totalPages = totalPages;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
