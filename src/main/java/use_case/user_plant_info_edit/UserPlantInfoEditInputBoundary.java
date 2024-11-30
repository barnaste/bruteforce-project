package use_case.user_plant_info_edit;

public interface UserPlantInfoEditInputBoundary {

    /**
     * Saves the plant using the provided input data.
     * @param inputData the data for the plant to be saved
     */
    void savePlant(UserPlantInfoEditInputData inputData);

    /**
     * Deletes the plant.
     */
    void deletePlant();

    /**
     * Sets the method for closing the upload use case. The UI for this component is owned externally.
     * @param escapeMap the method to close the upload use case
     */
    void setEscapeMap(Runnable escapeMap);

    /**
     * Exits the upload use case.
     */
    void escape();
}
