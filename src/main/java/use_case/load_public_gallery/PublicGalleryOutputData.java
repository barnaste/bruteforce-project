package use_case.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public class PublicGalleryOutputData {
    private final List<BufferedImage> images;
    private final int page;
    private final int totalPages;

    public PublicGalleryOutputData(List<BufferedImage> images, int page, int totalPages) {
        this.images = images;
        this.page = page;
        this.totalPages = totalPages;
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
}