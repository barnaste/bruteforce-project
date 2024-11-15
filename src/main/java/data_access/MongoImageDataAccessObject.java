package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.*;
import com.mongodb.client.gridfs.model.*;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class MongoImageDataAccessObject implements ImageDataAccessObject {
    static final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";
    private final MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING);
    private final MongoDatabase database = mongoClient.getDatabase("appDB"); // Replace with your actual database name
    private final GridFSBucket gridFSBucket = GridFSBuckets.create(database, "plantImages");

    /**
     * A method that returns the image with the given id.
     * @param id is the unique id of the desired image
     * @return the image with the given id
     */
    public BufferedImage getImageFromID(String id){
        try {
            ObjectId objectId = new ObjectId(id);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridFSBucket.downloadToStream(objectId, outputStream);
            byte[] imageData = outputStream.toByteArray();

            // Convert byte array back to BufferedImage
            InputStream inputStream = new ByteArrayInputStream(imageData);
            return ImageIO.read(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * A method that inserts a new image into the database.
     * @param image is the image to insert
     * @return the id of the inserted image
     */
    public String addImage(BufferedImage image){
        try {
            // Write BufferedImage to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Set upload options
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024 * 1024)
                    .metadata(new org.bson.Document("type", "image").append("contentType", "image/jpeg"));

            // Upload image to GridFS
            ObjectId id = gridFSBucket.uploadFromStream("image.jpg", inputStream, options);
            return id.toHexString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * A method that deletes the image with the given id.
     * @param id is the id of the image to delete
     * @return whether the image was successfully deleted
     */
    public boolean deleteImage(String id){
        try {
            ObjectId objectId = new ObjectId(id);
            gridFSBucket.delete(objectId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * A method that deletes all images.
     */
    public boolean deleteAll() {
        try {
            // Get all files from the GridFSBucket
            GridFSFindIterable files = gridFSBucket.find();

            // Loop through the files and delete each one
            for (GridFSFile file : files) {
                gridFSBucket.delete(file.getObjectId());
            }

            return true; // Successfully deleted all files
        } catch (Exception e) {
            // Log the exception and return false in case of any error
            System.out.println("Error deleting all files: " + e.getMessage());
            return false;
        }
    }
}
