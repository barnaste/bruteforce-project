package interface_adapter.load_user_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * Represents the state of the user gallery, holding information about the currently displayed plant images,
 * the current page, total pages, and the IDs of the plants.
 * This class is used to track and manage the gallery's state for the user, including pagination and the
 * associated images and plant IDs.
 */
public class UserGalleryState {

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
