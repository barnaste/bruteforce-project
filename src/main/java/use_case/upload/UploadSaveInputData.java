package use_case.upload;

import java.awt.image.BufferedImage;

/**
 * The Input Data for the Upload use case.
 */
public class UploadSaveInputData {

    private final BufferedImage image;
    private final String plantSpecies;
    private final String userNotes;

    public UploadSaveInputData(BufferedImage image, String plantSpecies, String userNotes) {
        this.image = image;
        this.plantSpecies = plantSpecies;
        this.userNotes = userNotes;
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
}
