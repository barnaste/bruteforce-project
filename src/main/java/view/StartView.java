package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    public StartView(SignupViewModel signupViewModel, LoginViewModel loginViewModel,
                     ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;

        // Welcome label
        final JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(welcomeLabel);

        // "New User" button
        final JButton newUserButton = ViewComponentFactory.buildButton("New User");
        newUserButton.setPreferredSize(new Dimension(200, 55));
        newUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newUserButton.addActionListener(this);

        // "Login" button
        final JButton loginButton = ViewComponentFactory.buildButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 55));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);

        // Add components
        final JPanel buttonsPanel = ViewComponentFactory.buildHorizontalPanel(List.of(newUserButton, loginButton));
        add(ViewComponentFactory.buildVerticalPanel(List.of(welcomeLabel, buttonsPanel)));
        setLayout(new GridBagLayout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        if (e.getActionCommand().equals("New User")) {
            // Switch to signup view
            viewManagerModel.setState(signupViewModel.getViewName());
        }
        else if (e.getActionCommand().equals("Login")) {
            // Switch to login view
            viewManagerModel.setState(loginViewModel.getViewName());
        }
        viewManagerModel.firePropertyChanged();
    }
}
