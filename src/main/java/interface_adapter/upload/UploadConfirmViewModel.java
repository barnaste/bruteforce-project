package interface_adapter.upload;

import interface_adapter.ViewModel;

public class UploadConfirmViewModel extends ViewModel<UploadState> {
    public static final int BACKGROUND_COLOR = 0xfffffbef;

    public static final String RETURN_BUTTON_LABEL = "← Return";
    public static final String CONFIRM_BUTTON_LABEL = "Continue →";

    public UploadConfirmViewModel() {
        super("upload confirm");
        this.setState(new UploadState());
    }
}
