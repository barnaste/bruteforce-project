package use_case.sort;

/**
 * The Sort Input Boundary.
 */
public interface SortInputBoundary {

    /**
     * Executes the Sort Use Case.
     * @param sortInputData the input data
     */
    void execute(SortInputData sortInputData);
}
