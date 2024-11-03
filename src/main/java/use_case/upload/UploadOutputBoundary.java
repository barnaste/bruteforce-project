package use_case.upload;

public interface UploadOutputBoundary {

    void switchToConfirmView(UploadOutputData outputData);

    void switchToResultView(UploadOutputData outputData);

    void switchToSelectView();
}
