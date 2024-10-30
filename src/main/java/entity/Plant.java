package entity;

public class Plant {
    private String imageID;
    private String owner;
    private String comments;
    private Boolean isPublic;

    public Plant(String imageID, String owner, String comments, Boolean isPublic) {
        this.imageID = imageID;
        this.owner = owner;
        this.comments = comments;
        this.isPublic = isPublic;
    }

    public Plant() {

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
