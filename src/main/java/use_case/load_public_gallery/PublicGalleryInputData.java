package use_case.load_public_gallery;

public class PublicGalleryInputData {
    private final int page;

    public PublicGalleryInputData(int page){
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
