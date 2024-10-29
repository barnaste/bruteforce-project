package entity;

public class Plant {
    private String imageID;
    private String owner;
    private String comments;
    private String[] sharedWith;
    private Boolean isPublic;

    public Plant(String imageID, String owner, String comments, String[] sharedWith, Boolean isPublic) {
        this.imageID = imageID;
        this.owner = owner;
        this.comments = comments;
        this.sharedWith = sharedWith;
        this.isPublic = isPublic;
    }

    public String getImage() {
        return imageID;
    }
    public String getOwner() {
        return owner;
    }
    public String getComments() {
        return comments;
    }
    public String[] getSharedWith() {
        return sharedWith;
    }
    public Boolean isPublic() {
        return isPublic;
    }
    public void setImage(String imageID) {
        this.imageID = imageID;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public void setSharedWith(String[] sharedWith) {
        this.sharedWith = sharedWith;
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
