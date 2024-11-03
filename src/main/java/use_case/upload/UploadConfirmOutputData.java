package use_case.upload;

public class UploadConfirmOutputData {
    private final String imagePath;

    public UploadConfirmOutputData(String image) {
        this.imagePath = image;
    }

    public String getImage() {
        return imagePath;
    }

}
