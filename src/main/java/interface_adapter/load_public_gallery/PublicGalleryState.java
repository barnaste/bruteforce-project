package interface_adapter.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * Represents the state of the public gallery, including the current page of plant images,
 * the total number of pages, and the plant images and IDs.
 */
public class PublicGalleryState {

    private List<BufferedImage> plantImages;
    private List<ObjectId> plantID;
    private int currentPage;
    private int totalPages;

    public List<BufferedImage> getPlantImages() {
        return plantImages;
    }

    public List<ObjectId> getPlantID() {
        return plantID;
    }

    public void setPlantImages(List<BufferedImage> plantImages) {
        this.plantImages = plantImages;
    }

    public void setPlantID(List<ObjectId> plantID) {
        this.plantID = plantID;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
