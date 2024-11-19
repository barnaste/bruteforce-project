package interface_adapter.main;

/**
 * The State information representing the main app, i.e., the logged-in user.
 */
public class MainState {
    private String username = "";

    public MainState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}