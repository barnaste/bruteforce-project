package interface_adapter.mode_switch;

import use_case.mode_switch.ModeSwitchInteractor;

public class ModeSwitchController {
    private final ModeSwitchInteractor modeSwitchInteractor;

    public ModeSwitchController(ModeSwitchInteractor modeSwitchInteractor) {
        this.modeSwitchInteractor = modeSwitchInteractor;
    }

    public void switchMode(){
        modeSwitchInteractor.switchMode();
    }
}