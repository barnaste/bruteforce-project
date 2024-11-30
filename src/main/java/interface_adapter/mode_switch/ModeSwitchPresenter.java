package interface_adapter.mode_switch;

import use_case.mode_switch.ModeSwitchOutputBoundary;

/**
 * Handles the presentation logic for the mode switch operation.
 * Implements the ModeSwitchOutputBoundary to update the current mode in the ModeSwitchViewModel.
 */
public class ModeSwitchPresenter implements ModeSwitchOutputBoundary {

    private final ModeSwitchViewModel modeSwitchViewModel;

    /**
     * Constructs a ModeSwitchPresenter with the given ModeSwitchViewModel.
     *
     * @param modeSwitchViewModel The ViewModel that holds the state of the mode.
     */
    public ModeSwitchPresenter(ModeSwitchViewModel modeSwitchViewModel) {
        this.modeSwitchViewModel = modeSwitchViewModel;
    }

    @Override
    public void presentModeSwitch() {
        if (modeSwitchViewModel.getState().getCurrentMode().equals(ModeSwitchState.Mode.DISCOVER)) {
            modeSwitchViewModel.getState().setCurrentMode(ModeSwitchState.Mode.MY_PLANTS);
        }
        else {
            modeSwitchViewModel.getState().setCurrentMode(ModeSwitchState.Mode.DISCOVER);
        }
        modeSwitchViewModel.firePropertyChanged("mode_switch");
    }
}
