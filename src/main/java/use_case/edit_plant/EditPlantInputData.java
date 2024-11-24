package use_case.edit_plant;

/**
 * Represents the input data for editing a plant, including user notes and visibility status.
 */
public class EditPlantInputData {

    private final String userNotes;
    private final boolean isPublic;

    /**
     * Constructs an EditPlantInputData instance with the provided user notes and visibility status.
     * @param userNotes the notes provided by the user for the plant
     * @param isPublic the visibility status of the plant (public or private)
     */
    public EditPlantInputData(String userNotes, boolean isPublic) {
        this.userNotes = userNotes;
        this.isPublic = isPublic;
    }

    /**
     * Gets the user notes for the plant.
     * @return the notes provided by the user
     */
    public String getUserNotes() {
        return userNotes;
    }

    /**
     * Checks if the plant is public.
     * @return true if the plant is public, false otherwise
     */
    public boolean isPublic() {
        return isPublic;
    }
}
