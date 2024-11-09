package interface_adapter.swap_gallery;

import use_case.swap_gallery.SwapGalleryOutputBoundary;
import use_case.swap_gallery.SwapGalleryOutputData;
import interface_adapter.main.MainViewModel;

public class SwapGalleryPresenter implements SwapGalleryOutputBoundary {
    private final MainViewModel mainViewModel;

    public SwapGalleryPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void present(SwapGalleryOutputData outputData) {
        // Set the updated mode in the view model, which will trigger UI updates
        mainViewModel.setCurrentMode(outputData.getUpdatedMode());
    }
}