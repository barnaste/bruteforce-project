package use_case.sort;

/**
 * The Sort Output Boundary.
 */
public interface SortOutputBoundary {

    /**
     * Prepares the success view for the Sort Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SortOutputData outputData);

    /**
     * Prepares the failure view for the Sort Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
