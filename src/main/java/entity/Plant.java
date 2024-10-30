package entity;

import java.util.Date;

public class Plant {
    private String imageID;
    private String owner;
    private String comments;
    private Boolean isPublic;
    private Date lastChanged;

    /**
     * A constructor for Plant that initializes a plant with lastChanged as the current date.
     * @param imageID is the ID of the image which is stored in the database
     * @param owner is the username of the user who is associated with this image
     * @param comments is any comments that the user inputted regarding this image
     * @param isPublic is whether the user set the image as public
     */
    public Plant(String imageID, String owner, String comments, Boolean isPublic) {
        this.imageID = imageID;
        this.owner = owner;
        this.comments = comments;
        this.isPublic = isPublic;
        lastChanged = new Date();
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public Plant() {

    }

    public Date getLastChanged() { return lastChanged; }
    public void setLastChanged(Date lastChanged) { this.lastChanged = lastChanged; }
    public String getImage() { return imageID; }
    public String getOwner() {
        return owner;
    }
    public String getComments() {
        return comments;
    }
    public Boolean getIsPublic() {
        return isPublic;
    }
    public void setImage(String imageID) {
        this.imageID = imageID;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
