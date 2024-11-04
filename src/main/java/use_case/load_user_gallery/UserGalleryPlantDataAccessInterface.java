package use_case.load_user_gallery;

import java.awt.image.BufferedImage;
import java.util.List;

public interface UserGalleryPlantDataAccessInterface {
    List<BufferedImage> getImagesForUser(String userId, int skip, int limit);
    int getTotalUserImages(String userId);
}
