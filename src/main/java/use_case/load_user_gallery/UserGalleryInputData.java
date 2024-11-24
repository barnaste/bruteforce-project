package use_case.load_user_gallery;

public class UserGalleryInputData {
    private final int page;

    public UserGalleryInputData(int page){
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
