package use_case.upload;

import java.io.File;

/**
 * The Input Data for the Upload use case.
 */
public class UploadInputData {

    private final String imagePath;

    public UploadInputData(String image) {
        this.imagePath = image;
    }

    String getImage() { return imagePath; }
}
