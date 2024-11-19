package interface_adapter.mode_switch;

import use_case.mode_switch.ModeSwitchOutputBoundary;
import use_case.mode_switch.ModeSwitchOutputData;
import interface_adapter.main.MainViewModel;

public class ModeSwitchPresenter implements ModeSwitchOutputBoundary {
    private final ModeSwitchViewModel modeSwitchViewModel;

    public ModeSwitchPresenter(ModeSwitchViewModel modeSwitchViewModel) {
        this.modeSwitchViewModel = modeSwitchViewModel;
    }

    @Override
    public void present(ModeSwitchOutputData outputData) {
        // Set the updated mode in the view model, which will trigger UI updates
        modeSwitchViewModel.setCurrentMode(outputData.getUpdatedMode());
    }
}