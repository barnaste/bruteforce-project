package use_case.mode_switch;

/**
 * Defines the input boundary for the "mode switch" use case.
 * Responsible for triggering the mode switching logic.
 */
public interface ModeSwitchInputBoundary {
    /**
     * Switches the current mode by executing the associated logic.
     */
    void switchMode();
}
