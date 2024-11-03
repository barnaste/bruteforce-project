package data_access;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

public class UserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface, LogoutUserDataAccessInterface {

    private final MongoUserDataBase mongoUserDataBase = new MongoUserDataBase();
    private String currentUsername;

    /**
     * @param username the username to look for
     * @return
     */
    @Override
    public boolean existsByUsername(String username) {
        return mongoUserDataBase.existsByUsername(username);
    }

    /**
     * @param user is the user being added.
     */
    @Override
    public void addUser(User user) {
        mongoUserDataBase.addUser(user);
    }

    /**
     * @param username is the username of the user.
     * @return
     */
    @Override
    public User getUser(String username) {
        return mongoUserDataBase.getUser(username);
    }

    /**
     * @param username is the username of the user.
     * @return
     */
    @Override
    public boolean deleteUser(String username) {
        return mongoUserDataBase.deleteUser(username);
    }


    /**
     * @return
     */
    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;

    }
}
