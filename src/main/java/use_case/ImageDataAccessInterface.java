package use_case;

import java.awt.image.BufferedImage;

/**
 * ImageDB is an interface that defines the methods that the ImageDB class must implement.
 */
public interface ImageDataAccessInterface {
    /**
     * A method that returns the image with the given id.
     * @param id is the unique id of the desired image
     * @return the image with the given id
     */
    BufferedImage getImageFromID(String id);

    /**
     * A method that inserts a new image into the database.
     * @param image is the image to insert
     * @return the id of the inserted image
     */
    String addImage(BufferedImage image);

    /**
     * A method that deletes the image with the given id.
     * @param id is the id of the image to delete
     * @return whether the image was successfully deleted
     */
    boolean deleteImage(String id);
}
