package use_case.load_public_gallery;

import data_access.MongoImageDataAccessObject;
import entity.Plant;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PublicGalleryInteractor implements PublicGalleryInputBoundary {
    private final PublicGalleryPlantDataAccessInterface galleryDataAccessObject;
    private final PublicGalleryOutputBoundary galleryPresenter;
    private final MongoImageDataAccessObject imageDataAccessObject;
    private final int pageSize = 16;

    public PublicGalleryInteractor(PublicGalleryPlantDataAccessInterface galleryDataAccessObject,
                                   PublicGalleryOutputBoundary galleryPresenter, MongoImageDataAccessObject imageDataAccessObject) {
        this.galleryDataAccessObject = galleryDataAccessObject;
        this.galleryPresenter = galleryPresenter;
        this.imageDataAccessObject = imageDataAccessObject;
    }

    @Override
    public void execute(PublicGalleryInputData galleryInputData) {
        int page = galleryInputData.getPage();
        int skip = (page - 1) * pageSize;  // Calculate the offset based on the page

        try {
            // Fetch paginated list of Plant objects
            List<Plant> plants = galleryDataAccessObject.getPublicPlants(skip, pageSize);

            if (plants == null || plants.isEmpty()) {
                galleryPresenter.prepareFailView("No images available for the public gallery.");
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
            PublicGalleryOutputData outputData = new PublicGalleryOutputData(images, page);
            galleryPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            galleryPresenter.prepareFailView("An error occurred while loading the public gallery: " + e.getMessage());
        }
    }
}
