package use_case.like_plant;

import entity.Plant;

/**
 * Represents the input data required for the "like plant" use case.
 * This class holds the plant to be liked.
 */
public class LikePlantInputData {

    private final Plant plant;

    /**
     * Constructs an instance of LikePlantInputData with the specified plant.
     *
     * @param plant the plant to be liked
     */
    public LikePlantInputData(Plant plant) {
        this.plant = plant;
    }

    /**
     * Returns the plant to be liked.
     *
     * @return the plant to be liked
     */
    public Plant getPlant() {
        return plant;
    }
}
