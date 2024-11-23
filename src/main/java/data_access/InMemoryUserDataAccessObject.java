package data_access;

import entity.User;

import java.util.HashMap;
import java.util.Map;

import use_case.UserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;


/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */

public class InMemoryUserDataAccessObject implements UserDataAccessInterface, LoginUserDataAccessInterface, SignupUserDataAccessInterface, LogoutUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public void addUser(User user) { users.put(user.getUsername(), user);}

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {this.currentUsername = username;}

    @Override
    public User getUser(String username) {return users.get(username);}

    @Override
    public boolean deleteUser(String username) {return users.remove(username) != null;}
}
