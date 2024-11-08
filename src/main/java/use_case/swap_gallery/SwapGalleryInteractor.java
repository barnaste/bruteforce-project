package use_case.swap_gallery;

import interface_adapter.main.MainViewModel;

public class SwapGalleryInteractor implements SwapGalleryInputBoundary {
    private final SwapGalleryOutputBoundary outputBoundary;
    private final MainViewModel mainViewModel;

    public SwapGalleryInteractor(SwapGalleryOutputBoundary outputBoundary, MainViewModel mainViewModel) {
        this.outputBoundary = outputBoundary;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void execute(SwapGalleryInputData inputData) {
        // Perform business logic (mode switching)
        mainViewModel.setCurrentMode(inputData.getNewMode());

        // Create output data and send it to the presenter
        SwapGalleryOutputData outputData = new SwapGalleryOutputData(mainViewModel.getCurrentMode());
        outputBoundary.present(outputData);
    }
}