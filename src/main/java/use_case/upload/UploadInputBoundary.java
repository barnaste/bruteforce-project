package use_case.upload;

/**
 * Input Boundary for actions which are related to uploading a new image.
 */
public interface UploadInputBoundary {

    /**
     * Executes the upload use case.
     * @param uploadInputData the input data
     */
    void loadImageData(UploadInputData uploadInputData);

    void switchToConfirmView(UploadInputData uploadInputData);

    void switchToSelectView();

    void saveUpload(UploadSaveInputData uploadInputData);

    /**
     * Set the method by which the upload use case is closed -- the UI for this
     * component is owned by another component, and thus must be closed externally
     * @param escapeMap the method called to close the upload use case
     */
    void setEscapeMap(Runnable escapeMap);

    /**
     * Exits the upload use case.
     */
    void escape();
}
