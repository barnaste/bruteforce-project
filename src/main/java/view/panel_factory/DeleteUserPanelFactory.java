package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadPresenter;
import interface_adapter.upload.confirm.UploadConfirmViewModel;
import interface_adapter.upload.result.UploadResultViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;
import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInteractor;
import use_case.upload.UploadOutputBoundary;
import view.ViewManager;
import view.upload.UploadConfirmView;
import view.upload.UploadResultView;
import view.upload.UploadSelectView;

import javax.swing.*;
import java.awt.*;

public class DeleteUserPanelFactory {
    public static void createDeleteUserPanel(Container parentPanel, JPanel cardPanel, Runnable escapeMap) {
        // NOTE: we extend CardLayout so that whenever the top card is swapped, the
        // ENTIRE view is redrawn, and not just the region the card occupied.
        // This is because cards may be of variant dimensions. We would otherwise
        // have artefacts from previous cards if they were of larger dimensions.
        CardLayout cardLayout = new CardLayout() {
            @Override
            public void show(Container parent, String name) {
                super.show(parent, name);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        };
        cardPanel.setLayout(cardLayout);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create and add labels and text fields for username and password
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        panel.add(new JLabel("Enter Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10)); // Add space between components
        panel.add(new JLabel("Enter Password:"));
        panel.add(passwordField);

        // Show the confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Confirm Deletion",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check the user's choice
        if (result == JOptionPane.OK_OPTION) {
            String tempusername = usernameField.getText();
            String temppassword = new String(passwordField.getPassword());
        }else {
            //CAN REPLACE THIS
            System.out.println("Deletion canceled by user.");
        }
    }
}
