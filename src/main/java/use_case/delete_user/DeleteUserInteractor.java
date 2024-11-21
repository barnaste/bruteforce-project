package use_case.delete_user;

import entity.Plant;
import entity.User;
import interface_adapter.delete_user.DeleteUserPresenter;
import use_case.login.*;
import use_case.logout.LogoutOutputData;

import java.util.List;


/**
 * The Delete User Interactor.
 */
public class DeleteUserInteractor implements DeleteUserInputBoundry {
    private final DeleteUserUserDataAccessInterface userDataAccessObject;
    private final DeleteUserOutputBoundary deleteUserPresenter;

    public DeleteUserInteractor(DeleteUserUserDataAccessInterface userDataAccessInterface,
                                DeleteUserOutputBoundary deleteUserOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.deleteUserPresenter = deleteUserOutputBoundary;
    }

    @Override
    public void execute(DeleteUserInputData deleteUserInputData) {
        final String username = deleteUserInputData.getUsername();

        //GRAB THE PLANTS
        List<Plant> plants = userDataAccessObject.getUserPlants(username);
        for (Plant plant : plants) {
            //DELETE IMAGES
            userDataAccessObject.deleteImage(plant.getImageID());
            //THEN PLANTS
            userDataAccessObject.deletePlant(plant.getFileID());
        }
        //THEN USER
        userDataAccessObject.deleteUser(username);
        //LOGOUT
        userDataAccessObject.setCurrentUsername(null);
        final DeleteUserOutputData deleteUserOutputData = new DeleteUserOutputData(username, false);
        //GO TO WELCOME VIEW
        deleteUserPresenter.prepareSuccessView(deleteUserOutputData);

    }
}
