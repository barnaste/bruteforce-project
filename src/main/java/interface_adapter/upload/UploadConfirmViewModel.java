package interface_adapter.upload;

import interface_adapter.ViewModel;

public class UploadConfirmViewModel extends ViewModel<UploadState> {
    public static final int TRANSPARENT = 0x00000000;
    public static final int TOP_PANEL_COLOR = 0xfff8f5e4;

    public static final int TOP_PANEL_HEIGHT = 25;
    public static final int MAIN_PANEL_HEIGHT = 500;
    public static final int PANEL_WIDTH = 500;

    public static final String RETURN_BUTTON_LABEL = "← Return";
    public static final String CONFIRM_BUTTON_LABEL = "Continue →";

    public UploadConfirmViewModel() {
        super("upload confirm");
        this.setState(new UploadState());
    }
}
