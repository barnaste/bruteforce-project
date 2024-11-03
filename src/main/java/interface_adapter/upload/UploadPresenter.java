package interface_adapter.upload;

import interface_adapter.ViewManagerModel;
import use_case.upload.UploadOutputBoundary;
import use_case.upload.UploadOutputData;

public class UploadPresenter implements UploadOutputBoundary {

    private final UploadSelectViewModel selectViewModel;
    private final UploadConfirmViewModel confirmViewModel;
    private final UploadResultViewModel resultViewModel;
    private final ViewManagerModel viewManagerModel;

    public UploadPresenter(ViewManagerModel viewManagerModel,
                           UploadSelectViewModel selectViewModel,
                           UploadConfirmViewModel confirmViewModel,
                           UploadResultViewModel resultViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.selectViewModel = selectViewModel;
        this.confirmViewModel = confirmViewModel;
        this.resultViewModel = resultViewModel;
    }

    @Override
    public void switchToConfirmView(UploadOutputData outputData) {
        final UploadState state = confirmViewModel.getState();
        state.setImagePath(outputData.getImage());
        confirmViewModel.setState(state);
        confirmViewModel.firePropertyChanged();

        viewManagerModel.setState(confirmViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToResultView(UploadOutputData outputData) {
        final UploadState state = resultViewModel.getState();
        state.setImagePath(outputData.getImage());
        resultViewModel.setState(state);
        resultViewModel.firePropertyChanged();

        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToSelectView() {
        viewManagerModel.setState(selectViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}