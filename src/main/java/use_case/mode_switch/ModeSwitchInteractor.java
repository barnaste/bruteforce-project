package use_case.mode_switch;

public class ModeSwitchInteractor implements ModeSwitchInputBoundary {
    private final ModeSwitchOutputBoundary outputPresenter;

    public ModeSwitchInteractor(ModeSwitchOutputBoundary outputBoundary) {
        this.outputPresenter = outputBoundary;
    }

    @Override
    public void switchMode() {
        outputPresenter.presentModeSwitch();
    }
}