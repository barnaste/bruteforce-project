package entity;

import data_access.MongoImageDataBase;
import org.bson.types.ObjectId;

import java.awt.image.BufferedImage;
import java.util.Date;

public class Plant {
    private ObjectId fileID;
    private BufferedImage image;
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
        this.image = image;
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public Plant() {

    }

    /**
     * Set image to the image object associated with imageID.
     */
    public void makeImage() {
        MongoImageDataBase imageDataBase = new MongoImageDataBase();
        image = imageDataBase.getImageFromID(imageID);
    }

    /**
     * Prepares a plant object to be uploaded to the database.
     * Sets the lastChanged date to the current date and uploads the image and store it using imageID.
     */
    public void prepareForUpload() {
        fileID = new ObjectId();
        lastChanged = new Date();
        MongoImageDataBase imageDataBase = new MongoImageDataBase();
        imageID = imageDataBase.addImage(image);
        image = null;
    }

    public Date getLastChanged() { return lastChanged; }
    public BufferedImage getImage() { return image; }
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
    public ObjectId getfileID() {
        return fileID;
    }
    public void setImage(BufferedImage image){ this.image = image; }
    public void setfileID(ObjectId fileID) { this.fileID = fileID; }
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
