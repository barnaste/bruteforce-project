package interface_adapter.upload;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import use_case.upload.UploadOutputBoundary;
import use_case.upload.UploadOutputData;

/**
 * The Upload Presenter.
 */
public class UploadPresenter implements UploadOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MainViewModel mainViewModel;

    public UploadPresenter(ViewManagerModel viewManagerModel, MainViewModel uploadViewModel) {
        this.mainViewModel = uploadViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(UploadOutputData outputData) {
        // TODO: Implement
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // TODO: Implement
    }
}
