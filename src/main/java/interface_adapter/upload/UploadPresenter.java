package interface_adapter.upload;

import interface_adapter.ViewManagerModel;
import interface_adapter.upload.confirm.UploadConfirmViewModel;
import interface_adapter.upload.result.UploadResultViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;
import use_case.upload.UploadOutputBoundary;

public class UploadPresenter implements UploadOutputBoundary {

    private final UploadSelectViewModel selectorViewModel;
    private final UploadConfirmViewModel confirmViewModel;
    private final UploadResultViewModel resultViewModel;
    private final ViewManagerModel viewManagerModel;

    public UploadPresenter(ViewManagerModel viewManagerModel,
                           UploadSelectViewModel selectorViewModel,
                           UploadConfirmViewModel confirmViewModel,
                           UploadResultViewModel resultViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.selectorViewModel = selectorViewModel;
        this.confirmViewModel = confirmViewModel;
        this.resultViewModel = resultViewModel;
    }
}