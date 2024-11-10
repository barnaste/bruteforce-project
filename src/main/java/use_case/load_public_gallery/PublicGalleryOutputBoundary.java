package use_case.load_public_gallery;

import use_case.login.LoginOutputData;

public interface PublicGalleryOutputBoundary {
    void prepareSuccessView(PublicGalleryOutputData outputData);
    void prepareFailView(String errorMessage);
}
