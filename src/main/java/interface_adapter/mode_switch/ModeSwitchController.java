package interface_adapter.mode_switch;

import use_case.mode_switch.ModeSwitchInteractor;

/**
 * Controller responsible for handling mode switch actions.
 * It delegates the mode switching logic to the associated interactor.
 */
public class ModeSwitchController {
    private final ModeSwitchInteractor modeSwitchInteractor;

    /**
     * Constructs a ModeSwitchController with the provided interactor.
     *
     * @param modeSwitchInteractor the interactor responsible for the mode switching logic
     */
    public ModeSwitchController(ModeSwitchInteractor modeSwitchInteractor) {
        this.modeSwitchInteractor = modeSwitchInteractor;
    }

    /**
     * Switches the current mode by delegating the action to the interactor.
     */
    public void switchMode() {
        modeSwitchInteractor.switchMode();
    }
}
