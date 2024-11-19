package use_case.mode_switch;

import interface_adapter.mode_switch.ModeSwitchState;

public class ModeSwitchInputData {
    private final ModeSwitchState.Mode newMode;

    public ModeSwitchInputData(ModeSwitchState.Mode newMode) {
        this.newMode = newMode;
    }

    public ModeSwitchState.Mode getNewMode() {
        return newMode;
    }
}