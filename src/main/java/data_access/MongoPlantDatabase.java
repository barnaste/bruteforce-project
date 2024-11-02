package data_access;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entity.Plant;
import entity.User;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoPlantDatabase implements PlantDataBase {
    final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

    @Override
    public List<Plant> getPlants(String username, int skip, int limit) {
        List<Plant> result = new ArrayList<>();
        Bson sort = descending("lastChanged");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);
            FindIterable<Plant> iterable =  collection.find(eq("owner", username)).sort(sort).skip(skip)
                    .limit(limit);
            for (Plant plant : iterable) {
                plant.makeImage();
                result.add(plant);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Plant> getPublicPlants(int skip, int limit) {
        List<Plant> result = new ArrayList<>();
        Bson sort = descending("lastChanged");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);
            FindIterable<Plant> iterable =  collection.find(eq("isPublic", true)).sort(sort).skip(skip)
                    .limit(limit);
            for (Plant plant : iterable) {
                plant.makeImage();
                result.add(plant);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void addPlant(Plant plant) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);
            plant.prepareForUpload();
            collection.insertOne(plant);
        }
    }

    @Override
    public boolean editPlant(ObjectId fileID, Plant newPlant) {
        // TODO: Complete this method.
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Create a filter to find the plant by its ID
            Bson filter = eq("_id", fileID);

            // Find the existing plant
            Plant existingPlant = collection.find(filter).first();

            if (existingPlant != null) {
                // Check if the image has changed
                if (!existingPlant.getImageID().equals(newPlant.getImageID())) {
                    // Delete the old image from the image database
                    MongoImageDataBase imageDataBase = new MongoImageDataBase();
                    imageDataBase.deleteImage(existingPlant.getImageID());
                }

                // Create an update document to specify the fields to update
                Bson updateOperation = new Document("$set", new Document("owner", newPlant.getOwner())
                        .append("comments", newPlant.getComments())
                        .append("isPublic", newPlant.getIsPublic())
                        .append("lastChanged", new Date())
                        .append("imageID", newPlant.getImageID())); // Update the imageID if changed

                // Use updateOne to update the document
                UpdateResult result = collection.updateOne(filter, updateOperation);

                // Return true if the plant was modified, false otherwise
                return result.getModifiedCount() > 0;
            } else {
                return false; // Plant not found
            }
        } catch (Exception e) {
            System.out.println("Error editing plant: " + e.getMessage()); // Consider using a logging framework
            return false;
        }
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find the plant to get its imageID before deleting
            Plant plant = collection.find(eq("_id", fileID)).first();

            if (plant != null) {
                // Delete the image from the image database
                MongoImageDataBase imageDataBase = new MongoImageDataBase();
                imageDataBase.deleteImage(plant.getImageID());

                // Create a filter to find the plant by its ID
                Bson filter = eq("_id", fileID);

                // Perform the delete operation
                DeleteResult result = collection.deleteOne(filter);

                // Return true if a document was deleted
                return result.getDeletedCount() > 0;
            } else {
                return false; // Plant not found
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private MongoCollection<Plant> getPlantsCollection(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("plants", Plant.class);
    }
}
