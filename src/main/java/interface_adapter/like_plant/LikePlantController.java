package interface_adapter.like_plant;

import entity.Plant;
import use_case.like_plant.LikePlantInputBoundary;
import use_case.like_plant.LikePlantInputData;

/**
 * Controller responsible for managing the "like plant" operation.
 * It interacts with the LikePlantInputBoundary to handle business logic for liking a plant.
 */
public class LikePlantController {
    private final LikePlantInputBoundary likePlantInteractor;

    /**
     * Constructs a LikePlantController with the specified LikePlantInputBoundary.
     *
     * @param likePlantInteractor the interactor that handles the business logic for liking a plant
     */
    public LikePlantController(LikePlantInputBoundary likePlantInteractor) {
        this.likePlantInteractor = likePlantInteractor;
    }

    /**
     * Executes the process of liking a plant by passing the plant data to the interactor.
     *
     * @param plant the plant to be liked
     */
    public void execute(Plant plant) {
        final LikePlantInputData likePlantInputData = new LikePlantInputData(plant);
        likePlantInteractor.execute(likePlantInputData);
    }
}
