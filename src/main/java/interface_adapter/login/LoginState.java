package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String username = "";
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getLoginError() {
        return "Username and password do not match. Try again!";
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
