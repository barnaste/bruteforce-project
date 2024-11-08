package interface_adapter.main;

/**
 * The State information representing the main app, i.e., the logged-in user.
 */
public class MainState {
    private String username = "";

    private String password = "";
    private String passwordError;

    private boolean isPublic;

    public MainState(MainState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MainState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic() {
        this.isPublic = true;
    }

    public void setPrivate() {
        this.isPublic = false;
    }

}