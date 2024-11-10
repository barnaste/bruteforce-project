package interface_adapter.main;

/**
 * The State information representing the main app, i.e., the logged-in user.
 */
public class MainState {
    private String username = "";
    private Mode currentMode = Mode.MY_PLANTS;

    public enum Mode {
        DISCOVER,
        MY_PLANTS
    }

    public MainState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }
}