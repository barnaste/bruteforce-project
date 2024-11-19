package interface_adapter.mode_switch;

public class ModeSwitchState {
    private Mode currentMode = Mode.MY_PLANTS;

    public enum Mode {
        DISCOVER,
        MY_PLANTS
    }
    public Mode getCurrentMode() {
        return currentMode;
    }
    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }
}
