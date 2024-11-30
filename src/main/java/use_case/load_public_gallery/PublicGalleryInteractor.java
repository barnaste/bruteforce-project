package use_case.load_public_gallery;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import entity.Plant;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;

/**
 * Handles the business logic for navigating and retrieving data for the public gallery.
 * Manages pagination and interacts with data access objects to fetch public plant data and images.
 */
public class PublicGalleryInteractor implements PublicGalleryInputBoundary {
    private static final int IMAGES_PER_PAGE = 15;

    private final PlantDataAccessInterface plantDataAccessInterface;
    private final PublicGalleryOutputBoundary galleryPresenter;
    private final ImageDataAccessInterface imageDataAccessObject;

    private int currentPage;

    public PublicGalleryInteractor(PlantDataAccessInterface galleryDataAccessObject,
                                   PublicGalleryOutputBoundary galleryPresenter,
                                   ImageDataAccessInterface imageDataAccessObject) {
        this.plantDataAccessInterface = galleryDataAccessObject;
        this.galleryPresenter = galleryPresenter;
        this.imageDataAccessObject = imageDataAccessObject;
        this.currentPage = 0;
    }

    /**
     * Navigates to the next page of the public gallery, if there are more pages available.
     */
    public void nextPage() {
        final int totalPages = getNumberOfPublicPlants();
        if (currentPage < totalPages - 1) {
            currentPage++;
            execute(new PublicGalleryInputData(currentPage));
        }
    }

    /**
     * Navigates to the previous page of the public gallery, if there is a previous page.
     */
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            execute(new PublicGalleryInputData(currentPage));
        }
    }

    public int getNumberOfPublicPlants() {
        return (int) Math.ceil((double) plantDataAccessInterface.getNumberOfPublicPlants() / IMAGES_PER_PAGE);
    }

    @Override
    public void execute(PublicGalleryInputData galleryInputData) {
        final int page = galleryInputData.getPage();
        currentPage = page;
        final int skip = page * IMAGES_PER_PAGE;

        // Retrieve the correct slice of Plant objects from database
        final List<Plant> plants = plantDataAccessInterface.getPublicPlants(skip, IMAGES_PER_PAGE);

        // Get images from Plant objects
        final List<BufferedImage> images = new ArrayList<>();
        final List<ObjectId> ids = new ArrayList<>();
        for (Plant plant : plants) {
            ids.add(plant.getFileID());
            images.add(imageDataAccessObject.getImageFromID(plant.getImageID()));
        }

        // Prepare output data and send to presenter
        final int totalPages = getNumberOfPublicPlants();
        final PublicGalleryOutputData outputData = new PublicGalleryOutputData(images, ids, currentPage, totalPages);
        galleryPresenter.prepareSuccessView(outputData);
    }
}
