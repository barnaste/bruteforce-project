package use_case.load_user_gallery;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.PlantDataAccessObject;
import entity.Plant;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UserGalleryInteractor implements UserGalleryInputBoundary {
    private final PlantDataAccessObject plantDataAccessObject;
    private final UserGalleryOutputBoundary galleryPresenter;
    private final MongoImageDataAccessObject imageDataAccessObject;
    private final int pageSize = 16;

    public UserGalleryInteractor(MongoPlantDataAccessObject galleryDataAccessObject,
                                   UserGalleryOutputBoundary galleryPresenter, MongoImageDataAccessObject imageDataAccessObject) {
        this.plantDataAccessObject = galleryDataAccessObject;
        this.galleryPresenter = galleryPresenter;
        this.imageDataAccessObject = imageDataAccessObject;
    }

    @Override
    public void execute(UserGalleryInputData galleryInputData) {
        int page = galleryInputData.getPage();
        int skip = (page - 1) * pageSize;  // Calculate the offset based on the page

        try {
            // Fetch paginated list of Plant objects
            List<Plant> plants = plantDataAccessObject.getPlants(galleryInputData.getUsername(), skip, pageSize);

            if (plants == null || plants.isEmpty()) {
                galleryPresenter.prepareFailView("No images available for the user gallery.");
                return;
            }

            // Extract images from Plant objects
            List<BufferedImage> images = new ArrayList<>();
            for (Plant plant : plants) {
                BufferedImage image = imageDataAccessObject.getImageFromID(plant.getImageID());
                if (image != null) {
                    images.add(image);
                } else {
                    System.out.println("Image with ID " + plant.getImageID() + " not found.");
                }
            }

            // Prepare output data and send to presenter
            UserGalleryOutputData outputData = new UserGalleryOutputData(images, page);
            galleryPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            galleryPresenter.prepareFailView("An error occurred while loading the user gallery: " + e.getMessage());
        }
    }
}
