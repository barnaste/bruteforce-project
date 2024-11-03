package use_case.upload;

public class UploadOutputData {

    private final boolean useCaseFailed;

    public UploadOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
        // TODO: Implement
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
