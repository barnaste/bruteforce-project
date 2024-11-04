package interface_adapter.upload.result;

import interface_adapter.ViewModel;

public class UploadResultViewModel extends ViewModel<UploadResultState> {
    public static final int TRANSPARENT = 0x00000000;
    public static final int TOP_PANEL_COLOR = 0xfff8f5e4;
    public static final int CONTENT_PANEL_COLOR = 0xfffffbef;
    public static final int ACTION_PANEL_COLOR = 0xfffffbef;

    public static final int TOP_PANEL_HEIGHT = 25;
    public static final int PANEL_WIDTH = 800;
    public static final int IMAGE_WIDTH = 500;
    public static final int IMAGE_HEIGHT = 500;

    public static final String RETURN_BUTTON_LABEL = "← Return";
    public static final String SAVE_BUTTON_LABEL = "✓ Save";
    public static final String DISCARD_BUTTON_LABEL = "× Discard";

    public UploadResultViewModel() {
        super("upload result");
        setState(new UploadResultState());
    }
}
