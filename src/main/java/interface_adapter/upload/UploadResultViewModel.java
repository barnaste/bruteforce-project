package interface_adapter.upload;

import interface_adapter.ViewModel;

public class UploadResultViewModel extends ViewModel<UploadState> {
    public UploadResultViewModel() {
        super("upload result");
        setState(new UploadState());
    }
}
