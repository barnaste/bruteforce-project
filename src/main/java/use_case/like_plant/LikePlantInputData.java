package use_case.like_plant;

import entity.Plant;
import org.bson.types.ObjectId;

public class LikePlantInputData {

    private final Plant plant;

    public LikePlantInputData(Plant plant) {
        this.plant = plant;
    }

    public Plant getPlant() {
        return plant;
    }
}
