package use_case.upload;

/**
 * The Input Data for the Upload use case.
 */
public class UploadInputData {

    private final String imagePath;

    public UploadInputData(String image) {
        this.imagePath = image;
    }

    public String getImage() {
        return imagePath;
    }
}
