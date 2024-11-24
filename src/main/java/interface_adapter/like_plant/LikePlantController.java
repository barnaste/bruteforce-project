package interface_adapter.like_plant;

import org.bson.types.ObjectId;
import use_case.like_plant.LikePlantInputBoundary;
import use_case.like_plant.LikePlantInputData;

public class LikePlantController {
    private final LikePlantInputBoundary likePlantInteractor;

    public LikePlantController(LikePlantInputBoundary likePlantInteractor) {
        this.likePlantInteractor = likePlantInteractor;
    }

    public void execute(ObjectId plantID) {
        LikePlantInputData likePlantInputData = new LikePlantInputData(plantID);
        likePlantInteractor.execute(likePlantInputData);
    }
}
