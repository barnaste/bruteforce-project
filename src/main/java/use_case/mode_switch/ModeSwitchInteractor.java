package use_case.mode_switch;

import interface_adapter.mode_switch.ModeSwitchViewModel;

public class ModeSwitchInteractor implements ModeSwitchInputBoundary {
    private final ModeSwitchOutputBoundary outputBoundary;
    private final ModeSwitchViewModel modeSwitchViewModel;

    public ModeSwitchInteractor(ModeSwitchOutputBoundary outputBoundary, ModeSwitchViewModel modeSwitchViewModel) {
        this.outputBoundary = outputBoundary;
        this.modeSwitchViewModel = modeSwitchViewModel;
    }

    @Override
    public void execute(ModeSwitchInputData inputData) {
        // Perform business logic (mode switching)
        modeSwitchViewModel.setCurrentMode(inputData.getNewMode());

        // Create output data and send it to the presenter
        ModeSwitchOutputData outputData = new ModeSwitchOutputData(modeSwitchViewModel.getCurrentMode());
        outputBoundary.present(outputData);
    }
}