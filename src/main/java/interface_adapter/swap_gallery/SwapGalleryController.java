package interface_adapter.swap_gallery;

import interface_adapter.main.MainState;
import use_case.swap_gallery.SwapGalleryInputBoundary;
import use_case.swap_gallery.SwapGalleryInputData;

public class SwapGalleryController {
    private final SwapGalleryInputBoundary inputBoundary;

    public SwapGalleryController(SwapGalleryInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void switchMode(MainState.Mode newMode) {
        SwapGalleryInputData inputData = new SwapGalleryInputData(newMode);
        inputBoundary.execute(inputData);  // Pass the input data to the interactor
    }
}