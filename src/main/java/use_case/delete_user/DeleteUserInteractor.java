package use_case.delete_user;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

import javax.swing.*;
import java.util.List;


/**
 * The Delete User Interactor.
 */
public class DeleteUserInteractor implements DeleteUserInputBoundry {
    //private final DeleteUserUserDataAccessInterface userDataAccessObject;
    private final PlantDataAccessInterface plantDataAccessObject;
    private final ImageDataAccessInterface imageDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final DeleteUserOutputBoundary deleteUserPresenter;

    public DeleteUserInteractor(DeleteUserOutputBoundary deleteUserOutputBoundary) {
        this.plantDataAccessObject = MongoPlantDataAccessObject.getInstance();
        this.imageDataAccessObject = MongoImageDataAccessObject.getInstance();
        this.userDataAccessObject = MongoUserDataAccessObject.getInstance();
        this.deleteUserPresenter = deleteUserOutputBoundary;
    }

    @Override
    public void execute(DeleteUserInputData deleteUserInputData) {
        final String username = deleteUserInputData.getUsername();
        final String password = userDataAccessObject.getUser(username).getPassword();
        // Create a panel to hold the input fields
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

            // Validate the input (you can add your logic here)
            if (username.equals(tempusername) && password.equals(temppassword)) {
                // Proceed with deletion or other logic
                System.out.println("Username and password confirmed. Proceeding with deletion...");
            } else {
                // Show an error message if validation fails
                JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
            }
        } else {
            System.out.println("Deletion canceled by user.");
        }

        /*
        //GRAB THE PLANTS
        List<Plant> plants = plantDataAccessObject.getUserPlants(username);
        for (Plant plant : plants) {
            //DELETE IMAGES
            imageDataAccessObject.deleteImage(plant.getImageID());
            //THEN PLANTS
            plantDataAccessObject.deletePlant(plant.getFileID());
        }
        //THEN USER
        userDataAccessObject.deleteUser(username);
        //LOGOUT
        userDataAccessObject.setCurrentUsername(null);
        final DeleteUserOutputData deleteUserOutputData = new DeleteUserOutputData(username, false);
        //GO TO WELCOME VIEW
        deleteUserPresenter.prepareSuccessView(deleteUserOutputData);
        */
    }
}
