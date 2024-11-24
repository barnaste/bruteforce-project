package use_case.like_plant;

import org.bson.types.ObjectId;

public class LikePlantInputData {

    private final ObjectId plantID;

    public LikePlantInputData(ObjectId plantID) {
        this.plantID = plantID;
    }

    public ObjectId getPlantID() {
        return plantID;
    }
}
