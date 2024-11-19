package interface_adapter.mode_switch;

import interface_adapter.ViewModel;

public class ModeSwitchViewModel extends ViewModel<ModeSwitchState> {
    private ModeSwitchState.Mode currentMode;

    public ModeSwitchViewModel() {
        super("mode_switch");
        this.currentMode = ModeSwitchState.Mode.DISCOVER;
    }

    public ModeSwitchState.Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(ModeSwitchState.Mode newMode) {
        this.currentMode = newMode;
    }
}
