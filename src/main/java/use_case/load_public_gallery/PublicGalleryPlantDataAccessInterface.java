package use_case.load_public_gallery;

import entity.Plant;

import java.util.List;

public interface PublicGalleryPlantDataAccessInterface {

    List<Plant> getPublicPlants(int skip, int limit) ;

    int getNumOfPublicImages(String username);
}
