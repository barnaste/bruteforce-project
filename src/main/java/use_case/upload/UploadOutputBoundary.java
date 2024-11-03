package use_case.upload;

public interface UploadOutputBoundary {

    void switchToConfirmView(UploadConfirmOutputData outputData);

    void switchToResultView(UploadResultOutputData outputData);

    void switchToSelectView();
}
