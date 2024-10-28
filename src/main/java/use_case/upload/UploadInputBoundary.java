package use_case.upload;

/**
 * Input Boundary for actions which are related to uploading a new image.
 */
public interface UploadInputBoundary {

    /**
     * Executes the upload use case.
     * @param uploadInputData the input data
     */
    void execute(UploadInputData uploadInputData);
}
