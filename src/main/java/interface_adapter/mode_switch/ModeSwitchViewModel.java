package interface_adapter.mode_switch;

import interface_adapter.ViewModel;

public class ModeSwitchViewModel extends ViewModel<ModeSwitchState> {

    public ModeSwitchViewModel() {
        super("mode_switch");
        setState(new ModeSwitchState());
        getState().setCurrentMode(ModeSwitchState.Mode.MY_PLANTS);
    }
}
