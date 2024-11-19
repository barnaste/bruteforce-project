package interface_adapter.mode_switch;

public class ModeSwitchController {
    private final ModeSwitchViewModel modeSwitchViewModel;

    public ModeSwitchController(ModeSwitchViewModel modeSwitchViewModel) {
        this.modeSwitchViewModel = modeSwitchViewModel;
    }

    public void switchMode(ModeSwitchState.Mode newMode) {
        // Update view model's current mode
        modeSwitchViewModel.setCurrentMode(newMode);

        // Notify the presenter about the mode change
        // This should trigger the view update (main view)
    }
}