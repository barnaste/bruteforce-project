package use_case.load_user_gallery;

public interface UserGalleryInputBoundary {
    void execute(UserGalleryInputData galleryInputData);

    int getNumberOfUserPlants();

    void nextPage();

    void previousPage();
}
