package entity;

import data_access.ImageDataBase;
import data_access.MongoImageDataBase;
import data_access.PlantDataBase;

import java.awt.image.BufferedImage;
import java.util.Date;

public class Plant {
    private String imageID;
    private String owner;
    private String comments;
    private Boolean isPublic;
    private Date lastChanged;

    /**
     * A constructor for Plant that initializes a plant with lastChanged as the current date.
     * @param image is the image associated with the plant
     * @param owner is the username of the user who is associated with this image
     * @param comments is any comments that the user inputted regarding this image
     * @param isPublic is whether the user set the image as public
     */
    public Plant(BufferedImage image, String owner, String comments, Boolean isPublic) {
        this.owner = owner;
        this.comments = comments;
        this.isPublic = isPublic;
        this.lastChanged = new Date();
        this.imageID = makeImageID(image);
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public Plant() {

    }

    private String makeImageID(BufferedImage image) {
        ImageDataBase imageDataBase = new MongoImageDataBase();
        return imageDataBase.addImage(image);
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
