package interface_adapter.upload;

import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInputData;

/**
 * The Upload Controller.
 */
public class UploadController {

    private final UploadInputBoundary uploadUseCaseInteractor;

    public UploadController(UploadInputBoundary uploadUseCaseInteractor) {
        this.uploadUseCaseInteractor = uploadUseCaseInteractor;
    }

    /**
     * Executes the Upload Use Case.
     */
    public void execute() {
        final UploadInputData uploadInputData = new UploadInputData();
        uploadUseCaseInteractor.execute(uploadInputData);
        // TODO: Implement
    }
}
