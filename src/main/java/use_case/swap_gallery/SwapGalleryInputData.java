package use_case.swap_gallery;

import interface_adapter.main.MainState;

public class SwapGalleryInputData {
    private final MainState.Mode newMode;

    public SwapGalleryInputData(MainState.Mode newMode) {
        this.newMode = newMode;
    }

    public MainState.Mode getNewMode() {
        return newMode;
    }
}