package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interface_adapter.delete_user.DeleteUserController;
import interface_adapter.delete_user.DeleteUserViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;

/**
 * A JPanel that represents the "Delete User" view. It allows the user to input their username
 * and password to confirm the deletion of their account. The view includes a confirmation button
 * and a cancel button, and listens for changes in the view model to display error messages.
 */
public class DeleteUserView extends JPanel implements PropertyChangeListener {

    private static final int TOP_PANEL_WIDTH = 400;
    private static final int TOP_PANEL_HEIGHT = 25;
    private static final int MAIN_PANEL_WIDTH = 400;
    private static final int MAIN_PANEL_HEIGHT = 250;

    private final String viewName = "delete user";
    private DeleteUserController controller;

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel usernameLabel = new JLabel("Enter Username:");
    private final JLabel passwordLabel = new JLabel("Enter Password:");

    private final JButton confirmButton = ViewComponentFactory.buildButton("Confirm");

    public DeleteUserView(DeleteUserViewModel deleteUserViewModel) {
        deleteUserViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadSelectViewModel.TRANSPARENT, true));

        // position each component nicely within the view area using a GridBagLayout
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(createTopPanel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(createMainPanel(), constraints);
    }

    private JPanel createTopPanel() {
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(TOP_PANEL_WIDTH, TOP_PANEL_HEIGHT));

        final JButton cancelBtn = ViewComponentFactory.buildButton("× Cancel");
        cancelBtn.setBorderPainted(false);

        cancelBtn.addActionListener(evt -> controller.escape());
        topPanel.add(cancelBtn, BorderLayout.WEST);
        return topPanel;
    }

    private JPanel createMainPanel() {
        final JLabel warningMsg = new JLabel("Confirm Account Deletion");

        final Font warningFont = new Font("Arial", Font.PLAIN, 16);
        warningMsg.setFont(warningFont);
        warningMsg.setForeground(Color.RED);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));

        final JPanel usernameFields =
                ViewComponentFactory.buildHorizontalPanel(List.of(usernameLabel, usernameInputField));
        final JPanel passwordFields =
                ViewComponentFactory.buildHorizontalPanel(List.of(passwordLabel, passwordInputField));

        // Add warning message, username fields, and password fields using GridBagConstraints
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(warningMsg, gbc);

        gbc.gridy = 1;
        mainPanel.add(usernameFields, gbc);

        gbc.gridy = 2;
        mainPanel.add(passwordFields, gbc);

        // Now add the confirm button (centered in the next row)
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(confirmButton, gbc);

        // Add action listener for the confirm button
        confirmButton.addActionListener(evt -> {
            controller.execute(usernameInputField.getText(), new String(passwordInputField.getPassword()));
        });

        return mainPanel;
    }

    public void setController(DeleteUserController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("error")) {
            JOptionPane.showMessageDialog(this, DeleteUserViewModel.LOGIN_ERROR_MESSAGE);
        }
    }
}
