package use_case.swap_gallery;

import interface_adapter.main.MainState;

public class SwapGalleryOutputData {
    private final MainState.Mode updatedMode;

    public SwapGalleryOutputData(MainState.Mode updatedMode) {
        this.updatedMode = updatedMode;
    }

    public MainState.Mode getUpdatedMode() {
        return updatedMode;
    }
}
