package use_case.like_plant;

/**
 * Defines the output boundary for the "like plant" use case.
 * This boundary is responsible for preparing the success view
 * once the plant has been liked successfully.
 */
public interface LikePlantOutputBoundary {
    /**
     * Prepares the success view after a plant has been successfully liked.
     */
    void prepareSuccessView();
}
