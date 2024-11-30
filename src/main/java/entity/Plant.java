package entity;

import java.util.Date;

import org.bson.types.ObjectId;

/**
 * Represents a plant in our program.
 */
public class Plant {
    private ObjectId fileID;
    private String imageID;
    private String species;
    private String owner;
    private String comments;
    private Boolean isPublic;
    private Date lastChanged;
    private String family;
    private String scientificName;
    private int numOfLikes;

    /**
     * Constructs a new Plant instance with the provided details, setting the `lastChanged` field to the current date.
     *
     * @param imageID        the unique identifier for the plant's image
     * @param species        the species of the plant
     * @param family         the biological family to which the plant belongs
     * @param scientificName the scientific name of the plant
     * @param owner          the username of the plant's owner
     * @param comments       any comments associated with the plant
     * @param isPublic       whether the plant is set as public
     */
    public Plant(String imageID, String species, String family, String scientificName,
                 String owner, String comments, Boolean isPublic) {
        this.owner = owner;
        this.family = family;
        this.scientificName = scientificName;
        this.comments = comments;
        this.isPublic = isPublic;
        this.imageID = imageID;
        this.species = species;
        this.fileID = new ObjectId();
    }

    /**
     * Another constructor for the purpose of initializing plant objects directly from the database. Not meant for use.
     */
    public Plant() {

    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public String getImageID() {
        return imageID;
    }

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

    public String getSpecies() {
        return species;
    }

    public String getFamily() {
        return family;
    }

    public String getScientificName() {
        return scientificName;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setFileID(ObjectId fileID) {
        this.fileID = fileID;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

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
