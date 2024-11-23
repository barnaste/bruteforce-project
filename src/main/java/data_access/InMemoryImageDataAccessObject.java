package data_access;

import use_case.ImageDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * In-memory implementation of the DAO for storing image data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryImageDataAccessObject implements ImageDataAccessInterface {


    private final Map<String, BufferedImage> imageStorage = new HashMap<>();
    private static InMemoryImageDataAccessObject instance;

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private InMemoryImageDataAccessObject() {}

    /**
     * The method used to retrieve an instance of this class. This way, the DAO is maintained as a singleton.
     */
    public static InMemoryImageDataAccessObject getInstance() {
        if (instance == null) {
            instance = new InMemoryImageDataAccessObject();
        }
        return instance;
    }


    @Override
    public BufferedImage getImageFromID(String id) {return imageStorage.get(id); }


    @Override
    public String addImage(BufferedImage image) {
        String id = UUID.randomUUID().toString();
        imageStorage.put(id, image);
        return id;
    }

    @Override
    public boolean deleteImage(String id) {

        if (imageStorage.containsKey(id)) {
            imageStorage.remove(id);
            return true;
        }
        return false;

    }

    public boolean deleteAll() {
        imageStorage.clear();
        return true;
    }

    }
