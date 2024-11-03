package use_case.upload;

public class UploadResultOutputData {
    private final String imagePath;
    private final String name;
    private final String scientificName;
    private final String family;
    private final int certainty;

    public UploadResultOutputData(String imagePath, String name, String scientificName, String family, int certainty) {
        this.imagePath = imagePath;
        this.name = name;
        this.scientificName = scientificName;
        this.family = family;
        this.certainty = certainty;
    }

        public String getImage() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getFamily() {
        return family;
    }

    public int getCertainty() {
        return certainty;
    }
}
