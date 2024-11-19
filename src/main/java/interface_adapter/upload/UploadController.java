package interface_adapter.upload;

import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInputData;
import use_case.upload.UploadSaveInputData;

import java.awt.image.BufferedImage;

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

    public void saveUpload(BufferedImage image, String plantName, String family, String plantSpecies,
                           String userNotes, boolean isPublic) {
        final UploadSaveInputData inputData = new UploadSaveInputData(image, plantName, family, plantSpecies,
                userNotes, isPublic);
        uploadUseCaseInteractor.saveUpload(inputData);
    }
}
