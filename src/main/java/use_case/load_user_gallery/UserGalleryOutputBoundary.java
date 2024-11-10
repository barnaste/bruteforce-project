package use_case.load_user_gallery;

import use_case.login.LoginOutputData;

public interface UserGalleryOutputBoundary {
    void prepareSuccessView(UserGalleryOutputData outputData);
    void prepareFailView(String errorMessage);
    void switchToUserGalleryView(UserGalleryOutputData outputData);
}
