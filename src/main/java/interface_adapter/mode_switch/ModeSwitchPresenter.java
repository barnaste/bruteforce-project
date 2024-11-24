package interface_adapter.mode_switch;

import use_case.mode_switch.ModeSwitchOutputBoundary;

public class ModeSwitchPresenter implements ModeSwitchOutputBoundary {
    private final ModeSwitchViewModel modeSwitchViewModel;

    public ModeSwitchPresenter(ModeSwitchViewModel modeSwitchViewModel) {
        this.modeSwitchViewModel = modeSwitchViewModel;
    }

    @Override
    public void presentModeSwitch() {
        if (modeSwitchViewModel.getState().getCurrentMode().equals(ModeSwitchState.Mode.DISCOVER)) {
            modeSwitchViewModel.getState().setCurrentMode(ModeSwitchState.Mode.MY_PLANTS);
        } else {
            modeSwitchViewModel.getState().setCurrentMode(ModeSwitchState.Mode.DISCOVER);
        }
        modeSwitchViewModel.firePropertyChanged("mode_switch");
    }
}