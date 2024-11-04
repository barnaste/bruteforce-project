package interface_adapter.upload.select;

public class UploadSelectState {
    private String error;

    UploadSelectState() {
        error = null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
