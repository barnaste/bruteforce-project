package interface_adapter.load_user_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public class UserGalleryState {

    private List<BufferedImage> plantImages;  // List of plant images for the gallery
    private int currentPage;                  // Current page number
    private int totalPages;                   // Total number of pages

    public List<BufferedImage> getPlantImages() {
        return plantImages;
    }

    public void setPlantImages(List<BufferedImage> plantImages) {
        this.plantImages = plantImages;
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
