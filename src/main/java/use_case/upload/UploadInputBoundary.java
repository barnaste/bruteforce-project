package use_case.upload;

/**
 * The Upload Input Boundary.
 */
public interface UploadInputBoundary {

    /**
     * Executes the upload use case.
     * @param uploadInputData the input data
     */
    void execute(UploadInputData uploadInputData);
}
