package use_case.public_plant_info;

/**
 * Input Boundary for actions which are related to viewing an image.
 */

public interface PublicPlantInfoInputBoundary {

    /** Set the method by which the upload use case is closed -- the UI for this
     * component is owned by another component, and thus must be closed externally.
     * @param escapeMap the method called to close the upload use case
     */
    void setEscapeMap(Runnable escapeMap);

    /**
     * Exits the upload use case.
     */
    void escape();
}
