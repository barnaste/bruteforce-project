package entity;

import org.bson.types.ObjectId;

import java.util.Date;

public class Plant {
    private ObjectId fileID;
    private String imageID;
    private String owner;
    private String comments;
    private Boolean isPublic;
    private Date lastChanged;

    /**
     * A constructor for Plant that initializes a plant with lastChanged as the current date.
     * @param imageID is the image id associated with the image for the plant
     * @param owner is the username of the user who is associated with this image
     * @param comments is any comments that the user inputted regarding this image
     * @param isPublic is whether the user set the image as public
     */
    public Plant(String imageID, String owner, String comments, Boolean isPublic) {
        this.owner = owner;
        this.comments = comments;
        this.isPublic = isPublic;
        this.imageID = imageID;
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public Plant() {

    }

    public Date getLastChanged() { return lastChanged; }
    public String getImageID() { return imageID; }
    public String getOwner() {
        return owner;
    }
    public String getComments() {
        return comments;
    }
    public Boolean getIsPublic() {
        return isPublic;
    }
    public ObjectId getFileID() {
        return fileID;
    }
    public void setFileID(ObjectId fileID) { this.fileID = fileID; }
    public void setLastChanged(Date lastChanged) { this.lastChanged = lastChanged; }
    public void setImageID(String imageID) {
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
