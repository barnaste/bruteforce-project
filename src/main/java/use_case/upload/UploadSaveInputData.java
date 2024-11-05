package use_case.upload;

import java.awt.image.BufferedImage;

/**
 * The Input Data for the Upload use case.
 */
public class UploadSaveInputData {

    private final BufferedImage image;
    private final String plantSpecies;
    private final String userNotes;
    private final boolean isPublic;

    public UploadSaveInputData(BufferedImage image, String plantSpecies, String userNotes, boolean isPublic) {
        this.image = image;
        this.plantSpecies = plantSpecies;
        this.userNotes = userNotes;
        this.isPublic = isPublic;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getPlantSpecies() {
        return plantSpecies;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
