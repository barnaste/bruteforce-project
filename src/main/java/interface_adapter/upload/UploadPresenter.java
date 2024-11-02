package interface_adapter.upload;

import use_case.upload.UploadOutputBoundary;

public class UploadPresenter implements UploadOutputBoundary {

    private final UploadSelectorViewModel selectorViewModel;
    private final UploadResultViewModel resultViewModel;

    public UploadPresenter(UploadSelectorViewModel selectorViewModel,
                           UploadResultViewModel resultViewModel) {
        this.selectorViewModel = selectorViewModel;
        this.resultViewModel = resultViewModel;
    }
}