package interface_adapter.sort;

import use_case.sort.SortInputBoundary;
import use_case.sort.SortInputData;

/**
 * The Sort Controller.
 */
public class SortController {

    private final SortInputBoundary sortUseCaseInteractor;

    public SortController(SortInputBoundary sortUseCaseInteractor) {
        this.sortUseCaseInteractor = sortUseCaseInteractor;
    }

    /**
     * Executes the Sort Use Case.
     */
    public void execute() {
        final SortInputData sortInputData = new SortInputData();
        sortUseCaseInteractor.execute(sortInputData);
        // TODO: Implement
    }
}
