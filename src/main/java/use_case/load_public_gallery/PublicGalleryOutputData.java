package use_case.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public class PublicGalleryOutputData {
    private final List<BufferedImage> images;
    private final int totalPages;

    public PublicGalleryOutputData(List<BufferedImage> images, int totalPages) {
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
