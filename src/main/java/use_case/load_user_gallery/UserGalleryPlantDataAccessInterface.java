package use_case.load_user_gallery;

import entity.Plant;
import java.util.List;

public interface UserGalleryPlantDataAccessInterface {
    /**
     * A method that returns plants for a given user in the database.
     * The plants are sorted by the last modified date in descending order.
     * @param username is the username of the user
     * @param skip is the number of documents to skip in the result set.
     * @param limit the maximum number of documents to return.
     * @return a list of Plant objects that belong to the specified user,
     *         sorted by the last modified date. Returns null if an exception occurs.
     */
    List<Plant> getPlants(String username, int skip, int limit) ;

    int getNumOfUserImages(String username);
}
