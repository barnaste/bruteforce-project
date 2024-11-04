package use_case.load_user_gallery;

public class UserGalleryInputData {
    private final String username;
    private final int pageIndex;

    public UserGalleryInputData(String username, int pageIndex){
        this.username = username;
        this.pageIndex = pageIndex;
    }

    public String getUsername() {
        return username;
    }

    public int getPage() {
        return pageIndex;
    }
}
