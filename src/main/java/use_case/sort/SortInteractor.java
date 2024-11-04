package use_case.sort;

/**
 * The Sort Interactor.
 */
public class SortInteractor implements SortInputBoundary {
    private final SortUserDataAccessInterface sortUserDataAccessObject;
    private final SortOutputBoundary sortPresenter;

    public SortInteractor(SortUserDataAccessInterface sortUserDataAccessInterface,
                          SortOutputBoundary sortOutputBoundary) {
        this.sortUserDataAccessObject = sortUserDataAccessInterface;
        this.sortPresenter = sortOutputBoundary;
    }

    @Override
    public void execute(SortInputData sortInputData) {
        System.out.println("Sort use case triggered.");
        // TODO: Implement
    }
}
