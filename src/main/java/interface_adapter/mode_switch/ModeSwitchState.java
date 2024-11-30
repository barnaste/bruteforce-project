package interface_adapter.mode_switch;

/**
 * Represents the state of the mode switch, holding the current mode between "DISCOVER" and "MY_PLANTS".
 */
public class ModeSwitchState {

    private Mode currentMode = Mode.MY_PLANTS;

    /**
     * Enum representing the two possible modes: DISCOVER and MY_PLANTS.
     */
    public enum Mode {
        DISCOVER,
        MY_PLANTS
    }

    /**
     * Gets the current mode.
     *
     * @return the current mode
     */
    public Mode getCurrentMode() {
        return currentMode;
    }

    /**
     * Sets the current mode.
     *
     * @param currentMode the mode to set
     */
    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }
}
