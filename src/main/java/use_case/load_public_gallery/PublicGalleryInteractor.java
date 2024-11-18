package use_case.load_public_gallery;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.PlantDataAccessObject;
import entity.Plant;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PublicGalleryInteractor implements PublicGalleryInputBoundary {
    private final PlantDataAccessObject plantDataAccessObject;
    private final PublicGalleryOutputBoundary galleryPresenter;
    private final MongoImageDataAccessObject imageDataAccessObject;
    private static final int IMAGES_PER_PAGE = 15;

    private int currentPage;

    public PublicGalleryInteractor(MongoPlantDataAccessObject galleryDataAccessObject,
                                   PublicGalleryOutputBoundary galleryPresenter, MongoImageDataAccessObject imageDataAccessObject) {
        this.plantDataAccessObject = galleryDataAccessObject;
        this.galleryPresenter = galleryPresenter;
        this.imageDataAccessObject = imageDataAccessObject;
        this.currentPage = 0;
    }

    public void nextPage() {
        int totalPages = getNumberOfPublicPlants();
        if (currentPage < totalPages - 1) {
            currentPage++;
            execute(new PublicGalleryInputData(currentPage));
        }
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            execute(new PublicGalleryInputData(currentPage));
        }
    }

    public int getNumberOfPublicPlants(){
        return (int) Math.ceil((double) plantDataAccessObject.getNumberOfPublicPlants() / IMAGES_PER_PAGE);
    }

    @Override
    public void execute(PublicGalleryInputData galleryInputData) {
        int page = galleryInputData.getPage();
        currentPage = page;
        int skip = page * IMAGES_PER_PAGE;  // Calculate the offset based on the page

        try {
            // Retrieve the correct slice of Plant objects from database
            List<Plant> plants = plantDataAccessObject.getPublicPlants(skip, IMAGES_PER_PAGE);

            if (plants == null || plants.isEmpty()) {
                galleryPresenter.prepareFailView();
                return;
            }

            // Get images from Plant objects
            List<BufferedImage> images = new ArrayList<>();
            for (Plant plant : plants) {
                BufferedImage image = imageDataAccessObject.getImageFromID(plant.getImageID());
                if (image != null) {
                    images.add(image);
                }
            }

            // Prepare output data and send to presenter
            int totalPages = getNumberOfPublicPlants();
            PublicGalleryOutputData outputData = new PublicGalleryOutputData(images, currentPage, totalPages);
            galleryPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            galleryPresenter.prepareFailView();
        }
    }
}
