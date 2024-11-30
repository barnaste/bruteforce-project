package data_access;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import use_case.ImageDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing image data. This implementation does
 * NOT persist data between runs of the program.
 */
public final class InMemoryImageDataAccessObject implements ImageDataAccessInterface {
    private static InMemoryImageDataAccessObject instance;

    private final Map<String, BufferedImage> imageStorage = new HashMap<>();

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private InMemoryImageDataAccessObject() {

    }

    /**
     * Retrieves the singleton instance of InMemoryImageDataAccessObject.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of InMemoryImageDataAccessObject
     */
    public static InMemoryImageDataAccessObject getInstance() {
        if (instance == null) {
            instance = new InMemoryImageDataAccessObject();
        }
        return instance;
    }

    @Override
    public BufferedImage getImageFromID(String id) {
        return imageStorage.get(id);
    }

    @Override
    public String addImage(BufferedImage image) {
        final String id = UUID.randomUUID().toString();
        imageStorage.put(id, image);
        return id;
    }

    @Override
    public boolean deleteImage(String id) {
        boolean result = false;
        if (imageStorage.containsKey(id)) {
            imageStorage.remove(id);
            result = true;
        }
        return result;
    }

    /**
     * Clears all stored images from the image storage.
     *
     * @return true, indicating the operation was successful
     */
    public boolean deleteAll() {
        imageStorage.clear();
        return true;
    }
}
