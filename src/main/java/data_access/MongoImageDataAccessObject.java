package data_access;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import use_case.ImageDataAccessInterface;

/**
 * Data Access Object (DAO) for interacting with image data in MongoDB.
 * Provides methods for adding, retrieving, and deleting images from a MongoDB GridFS bucket.
 */
public final class MongoImageDataAccessObject implements ImageDataAccessInterface {
    private static MongoImageDataAccessObject instance;
    private static final int CHUNK_SIZE_BYTES = 1024 * 1024;
    private static final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/"
            + "?retryWrites=true&w=majority&appName=Cluster0";

    private final MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING);
    private final MongoDatabase database = mongoClient.getDatabase("appDB");
    private final GridFSBucket gridfsBucket = GridFSBuckets.create(database, "plantImages");

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private MongoImageDataAccessObject() {

    }

    /**
     * Retrieves the singleton instance of the MongoImageDataAccessObject.
     * Ensures only one instance of the DAO exists throughout the application.
     *
     * @return the singleton instance of MongoImageDataAccessObject
     */
    public static MongoImageDataAccessObject getInstance() {
        if (instance == null) {
            instance = new MongoImageDataAccessObject();
        }
        return instance;
    }

    /**
     * A method that returns the image with the given id.
     * @param id is the unique id of the desired image
     * @return the image with the given id
     */
    public BufferedImage getImageFromID(String id) {
        BufferedImage result;
        try {
            final ObjectId objectId = new ObjectId(id);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridfsBucket.downloadToStream(objectId, outputStream);
            final byte[] imageData = outputStream.toByteArray();

            // Convert byte array back to BufferedImage
            final InputStream inputStream = new ByteArrayInputStream(imageData);
            result = ImageIO.read(inputStream);
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            result = null;
        }
        return result;
    }

    /**
     * A method that inserts a new image into the database.
     * @param image is the image to insert
     * @return the id of the inserted image
     */
    public String addImage(BufferedImage image) {
        String result;
        try {
            // Write BufferedImage to ByteArrayOutputStream
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            final InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Set upload options
            final GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(CHUNK_SIZE_BYTES)
                    .metadata(new org.bson.Document("type", "image").append("contentType", "image/jpeg"));

            // Upload image to GridFS
            final ObjectId id = gridfsBucket.uploadFromStream("image.jpg", inputStream, options);
            result = id.toHexString();
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            result = null;
        }
        return result;
    }

    /**
     * A method that deletes the image with the given id.
     *
     * @param id is the id of the image to delete
     * @return whether the image was successfully deleted
     */
    public boolean deleteImage(String id) {
        final boolean result;
        final ObjectId objectId = new ObjectId(id);
        final GridFSFile file = gridfsBucket.find(Filters.eq("_id", objectId)).first();

        if (file != null) {
            gridfsBucket.delete(objectId);
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Deletes all images stored in the GridFSBucket.
     * Iterates through all files in the bucket and deletes each one.
     */
    public void deleteAll() {
        final GridFSFindIterable files = gridfsBucket.find();
        for (GridFSFile file : files) {
            gridfsBucket.delete(file.getObjectId());
        }
    }
}
