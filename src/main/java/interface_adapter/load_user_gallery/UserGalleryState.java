package interface_adapter.load_user_gallery;

import org.bson.types.ObjectId;

import java.awt.image.BufferedImage;
import java.util.List;

public class UserGalleryState {

    private List<BufferedImage> plantImages;
    private List<ObjectId> plantID;
    private int currentPage;                  // Current page number
    private int totalPages;                   // Total number of pages

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
