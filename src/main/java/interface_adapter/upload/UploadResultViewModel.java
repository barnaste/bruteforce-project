package interface_adapter.upload;

import interface_adapter.ViewModel;

public class UploadResultViewModel extends ViewModel<UploadState> {
    public static final int BACKGROUND_COLOR = 0xfffffbef;

    public static final String RETURN_BUTTON_LABEL = "← Return";
    public static final String SAVE_BUTTON_LABEL = "✓ Save";
    public static final String DISCARD_BUTTON_LABEL = "× Discard";

    public UploadResultViewModel() {
        super("upload result");
        setState(new UploadState());
    }
}
