package use_case.publicplant;

import org.bson.types.ObjectId;

/**
 * Input Boundary for actions which are related to viewing an image.
 */

public interface PublicPlantInputBoundary {

    /**
     * Set the method by which the upload use case is closed -- the UI for this
     * component is owned by another component, and thus must be closed externally
     * @param escapeMap the method called to close the upload use case
     */
    void setEscapeMap(Runnable escapeMap);

    /**
     * Sets the plant for the use case.
     */
    void setPlant(ObjectId plantID);

    /**
     * Exits the upload use case.
     */
    void escape();

    /**
     * Executes the like plant use case
     */
    void like();
}
