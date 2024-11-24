package use_case.upload;

public interface UploadOutputBoundary {

    void switchToConfirmView(UploadConfirmOutputData outputData);

    void switchToSelectView(UploadSelectOutputData outputData);

    void switchToResultView(UploadResultOutputData outputData);

    void prepareSuccessView();
}
