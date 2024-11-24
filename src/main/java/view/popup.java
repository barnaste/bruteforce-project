package view;

import javax.swing.*;

public class popup {
    public static void main(String[] args) {
        // Create text fields for username and password
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Create a panel to hold the input fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        // Show the popup dialog
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Process the result
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword()); // Avoid using password directly

            // Validate username and password (example logic)
            if (username.equals("") && password.equals("")) {
                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
