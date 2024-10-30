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

public class MongoImageDataBase implements ImageDataBase {
    static final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";
    private final MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING);
    private final MongoDatabase database = mongoClient.getDatabase("appDB"); // Replace with your actual database name
    private final GridFSBucket gridFSBucket = GridFSBuckets.create(database, "plantImages");

    @Override
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

    @Override
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

    @Override
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

}
