package use_case.load_public_gallery;

public class PublicGalleryInputData {
    private final int pageIndex;

    public PublicGalleryInputData(int pageIndex){
        this.pageIndex = pageIndex;
    }

    public int getPage() {
        return pageIndex;
    }
}
