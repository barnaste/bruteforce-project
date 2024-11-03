package use_case.upload;

/**
 * The Upload Output Boundary.
 */
public interface UploadOutputBoundary {

    /**
     * Prepares the success view for the Upload Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(UploadOutputData outputData);

    /**
     * Prepares the failure view for the Upload Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
