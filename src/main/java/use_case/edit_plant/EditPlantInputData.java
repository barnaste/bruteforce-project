package use_case.edit_plant;

public class EditPlantInputData {

    private final String userNotes;
    private final boolean isPublic;

    public EditPlantInputData(String userNotes, boolean isPublic) {
        this.userNotes = userNotes;
        this.isPublic = isPublic;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
