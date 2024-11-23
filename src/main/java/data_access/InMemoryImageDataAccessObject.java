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


    private final Map<String, byte[]> imageStorage = new HashMap<>();
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
    public BufferedImage getImageFromID(String id) {
        try {
            byte[] imageData = imageStorage.get(id);
            if (imageData == null) {
                return null;
            }
            InputStream inputStream = new ByteArrayInputStream(imageData);
            return ImageIO.read(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


    @Override
    public String addImage(BufferedImage image) {
        try {
            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            byte[] imageData = outputStream.toByteArray();

            // Generate a unique ID for the image
            String id = UUID.randomUUID().toString();

            // Store the image byte array in the map using the generated ID
            imageStorage.put(id, imageData);
            return id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
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
