package use_case.upload;

/**
 * The Upload Interactor.
 */
public class UploadInteractor implements UploadInputBoundary{
    private final UploadUserDataAccessInterface userDataAccessObject;
    private final UploadOutputBoundary uploadPresenter;

    public UploadInteractor(UploadUserDataAccessInterface uploadUserDataAccessInterface,
                            UploadOutputBoundary uploadOutputBoundary) {
        this.userDataAccessObject = uploadUserDataAccessInterface;
        this.uploadPresenter = uploadOutputBoundary;
    }

    @Override
    public void execute(UploadInputData uploadInputData) {
        System.out.println("Upload use case triggered.");
        // TODO: Implement
    }
}
