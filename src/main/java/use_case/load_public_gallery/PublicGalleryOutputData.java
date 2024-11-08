package use_case.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public class PublicGalleryOutputData {
    private final List<BufferedImage> images;
    private final int page;

    public PublicGalleryOutputData(List<BufferedImage> images, int totalPages) {
        this.images = images;
        this.page = totalPages;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public int getPage() {
        return page;
    }
}
