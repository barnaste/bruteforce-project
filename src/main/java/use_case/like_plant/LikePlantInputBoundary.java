package use_case.like_plant;

/**
 * Interface defining the contract for the "like plant" use case.
 * Implementations of this interface handle the business logic for liking a plant.
 */
public interface LikePlantInputBoundary {
    /**
     * Executes the "like plant" use case with the provided input data.
     *
     * @param input the data required to perform the "like" operation on a plant
     */
    void execute(LikePlantInputData input);
}
