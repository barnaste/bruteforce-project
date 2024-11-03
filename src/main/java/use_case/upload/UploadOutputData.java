package use_case.upload;

public class UploadOutputData {

    private final String imagePath;

    public UploadOutputData(String image) {
        this.imagePath = image;
    }

    public String getImage() {
        return imagePath;
    }

}
