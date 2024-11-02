package interface_adapter.upload;

import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInputData;

import java.io.File;

public class UploadController {
    private final UploadInputBoundary uploadUseCaseInteractor;

    public UploadController(UploadInputBoundary uploadInteractor) {
        this.uploadUseCaseInteractor = uploadInteractor;
    }

    public void upload(String filePath) {
        final UploadInputData uploadInputData = new UploadInputData(filePath);
        uploadUseCaseInteractor.uploadImage(uploadInputData);
    }

    public void escape() {
        uploadUseCaseInteractor.escape();
    }

    public void save() {}
}
