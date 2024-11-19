package use_case.mode_switch;

import interface_adapter.mode_switch.ModeSwitchState;

public class ModeSwitchOutputData {
    private final ModeSwitchState.Mode updatedMode;

    public ModeSwitchOutputData(ModeSwitchState.Mode updatedMode) {
        this.updatedMode = updatedMode;
    }

    public ModeSwitchState.Mode getUpdatedMode() {
        return updatedMode;
    }
}
