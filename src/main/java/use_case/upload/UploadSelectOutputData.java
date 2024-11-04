package use_case.upload;

public class UploadSelectOutputData {
    private final String error;

    public UploadSelectOutputData() {
        this.error = null;
    }

    public UploadSelectOutputData(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
