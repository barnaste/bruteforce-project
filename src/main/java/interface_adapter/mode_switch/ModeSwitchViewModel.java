package interface_adapter.mode_switch;

import interface_adapter.ViewModel;

/**
 * ViewModel for handling the state and logic of the mode switch functionality,
 * including switching between "MY_PLANTS" and "DISCOVER" modes.
 */
public class ModeSwitchViewModel extends ViewModel<ModeSwitchState> {

    /**
     * Initializes the ModeSwitchViewModel, setting the initial state and the default mode to MY_PLANTS.
     */
    public ModeSwitchViewModel() {
        super("mode_switch");
        setState(new ModeSwitchState());
        getState().setCurrentMode(ModeSwitchState.Mode.MY_PLANTS);
    }
}
