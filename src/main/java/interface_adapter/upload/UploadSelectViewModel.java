package interface_adapter.upload;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the File Selection component of the Upload View.
 */
public class UploadSelectViewModel extends ViewModel<UploadState> {
    public static final int BACKGROUND_COLOR = 0xfffffbef;

    public static final String CANCEL_BUTTON_LABEL = "× Cancel";
    public static final String UPLOAD_BUTTON_LABEL = "Select from Computer";

    public UploadSelectViewModel() {
        super("upload select");
        setState(new UploadState());
    }
}
