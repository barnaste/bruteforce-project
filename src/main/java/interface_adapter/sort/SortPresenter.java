package interface_adapter.sort;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import use_case.sort.SortOutputBoundary;
import use_case.sort.SortOutputData;

/**
 * The Sort Presenter.
 */
public class SortPresenter implements SortOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MainViewModel mainViewModel;

    public SortPresenter(ViewManagerModel viewManagerModel, MainViewModel mainViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView(SortOutputData outputData) {
        // TODO: Implement
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // TODO: Implement
    }
}
