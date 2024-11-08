package interface_adapter.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public class PublicGalleryState {

    private List<BufferedImage> plantImages;  // List of plant images for the gallery
    private String galleryError;          // Error message, if any

    public List<BufferedImage> getPlantImages() {
        return plantImages;
    }

    public String getGalleryError() {
        return galleryError;
    }

    public void setPlantImages(List<BufferedImage> plantImages) {
        this.plantImages = plantImages;
    }

    public void setGalleryError(String galleryError) {
        this.galleryError = galleryError;
    }

}
