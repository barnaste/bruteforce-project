package interface_adapter.upload;

import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInputData;

public class UploadController {
    private final UploadInputBoundary uploadUseCaseInteractor;

    public UploadController(UploadInputBoundary uploadInteractor) {
        this.uploadUseCaseInteractor = uploadInteractor;
    }

    public void switchToConfirmView(String filePath) {
        final UploadInputData uploadInputData = new UploadInputData(filePath);
        uploadUseCaseInteractor.switchToConfirmView(uploadInputData);
    }

    public void switchToResultView(String filePath) {
        final UploadInputData uploadInputData = new UploadInputData(filePath);
        uploadUseCaseInteractor.loadImageData(uploadInputData);
    }

    public void switchToSelectView() {
        uploadUseCaseInteractor.switchToSelectView();
    }

    public void escape() {
        uploadUseCaseInteractor.escape();
    }

    public void save() {}
}
