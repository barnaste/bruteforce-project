package use_case.mode_switch;

/**
 * Interactor that handles the logic for switching modes.
 * It interacts with the output boundary to present the result of the mode switch.
 */
public class ModeSwitchInteractor implements ModeSwitchInputBoundary {
    private final ModeSwitchOutputBoundary outputPresenter;

    /**
     * Constructs a ModeSwitchInteractor with the provided output boundary.
     *
     * @param outputBoundary the output boundary responsible for presenting the result of the mode switch
     */
    public ModeSwitchInteractor(ModeSwitchOutputBoundary outputBoundary) {
        this.outputPresenter = outputBoundary;
    }

    /**
     * Executes the mode switch logic and passes the result to the output boundary.
     */
    @Override
    public void switchMode() {
        outputPresenter.presentModeSwitch();
    }
}
