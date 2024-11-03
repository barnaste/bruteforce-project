package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;

/**
 * The StartView for when the application is first opened.
 */
public class StartView extends JPanel implements ActionListener {
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    public StartView(SignupViewModel signupViewModel, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;

        // Set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(welcomeLabel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        // "New User" button
        JButton newUserButton = new JButton("New User");
        newUserButton.setPreferredSize(new Dimension(200, 55));
        newUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newUserButton.addActionListener(this);
        buttonsPanel.add(newUserButton);

        // "Login" button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 55));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);
        buttonsPanel.add(loginButton);

        add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and buttons
        add(buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        if (e.getActionCommand().equals("New User")) {
            // Switch to signup view
            viewManagerModel.setState(signupViewModel.getViewName());
        } else if (e.getActionCommand().equals("Login")) {
            // Switch to login view
            viewManagerModel.setState(loginViewModel.getViewName());
        }
        viewManagerModel.firePropertyChanged();
    }
}
