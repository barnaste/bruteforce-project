package use_case.load_user_gallery;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

/**
 * Interactor responsible for handling the business logic related to the user gallery use case.
 * It interacts with data access objects for plants, images, and users, and communicates with the presenter.
 */
public class UserGalleryInteractor implements UserGalleryInputBoundary {
    private static final int IMAGES_PER_PAGE = 15;

    private final PlantDataAccessInterface plantDataAccessObject;
    private final UserGalleryOutputBoundary galleryPresenter;
    private final ImageDataAccessInterface imageDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;

    private int currentPage;

    public UserGalleryInteractor(PlantDataAccessInterface galleryDataAccessObject,
                                 UserGalleryOutputBoundary galleryPresenter,
                                 ImageDataAccessInterface imageDataAccessObject,
                                 UserDataAccessInterface userDataAccessObject) {
        this.plantDataAccessObject = galleryDataAccessObject;
        this.galleryPresenter = galleryPresenter;
        this.imageDataAccessObject = imageDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
        this.currentPage = 0;
    }

    @Override
    public void nextPage() {
        final int totalPages = getNumberOfUserPlants();
        if (currentPage < totalPages - 1) {
            currentPage++;
            execute(new UserGalleryInputData(currentPage));
        }
    }

    @Override
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            execute(new UserGalleryInputData(currentPage));
        }
    }

    @Override
    public int getNumberOfUserPlants() {
        final int num = (int) Math.ceil((double) plantDataAccessObject.getNumberOfUserPlants(
                userDataAccessObject.getCurrentUsername()) / IMAGES_PER_PAGE);
        return num;
    }

    @Override
    public void execute(UserGalleryInputData galleryInputData) {
        final int page = galleryInputData.getPage();
        currentPage = page;
        final int skip = page * IMAGES_PER_PAGE;

        // Retrieve the correct slice of Plant objects from database
        final List<Plant> plants = plantDataAccessObject.getUserPlants(
                userDataAccessObject.getCurrentUsername(), skip, IMAGES_PER_PAGE);

        // Get images from Plant objects
        final List<BufferedImage> images = new ArrayList<>();
        final List<ObjectId> ids = new ArrayList<>();
        for (Plant plant : plants) {
            images.add(imageDataAccessObject.getImageFromID(plant.getImageID()));
            ids.add(plant.getFileID());
        }

        // Prepare output data and send to presenter
        final int totalPages = getNumberOfUserPlants();
        final UserGalleryOutputData outputData = new UserGalleryOutputData(images, ids, currentPage, totalPages);
        galleryPresenter.prepareSuccessView(outputData);
    }
}
