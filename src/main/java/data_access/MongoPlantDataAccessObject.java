package data_access;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import entity.Plant;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import use_case.load_user_gallery.UserGalleryPlantDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoPlantDataAccessObject implements UserGalleryPlantDataAccessInterface, PlantDataBase {
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

            // Find user's plants, sorted and paginated
            FindIterable<Plant> iterable = collection.find(eq("owner", username))
                    .sort(sort)
                    .skip(skip)
                    .limit(limit);

            for (Plant plant : iterable) {
                result.add(plant); // Add each plant to the result
            }
            return result; // Return the list of plants
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null; // Handle errors
        }
    }

    @Override
    public int getNumOfUserImages(String username) {
        return 0;
    }

    public List<Plant> getPublicPlants(int skip, int limit) {
        List<Plant> result = new ArrayList<>();
        Bson sort = descending("lastChanged");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find public plants, apply sorting, skipping, and limiting results
            FindIterable<Plant> iterable = collection.find(eq("isPublic", true))
                    .sort(sort)
                    .skip(skip)
                    .limit(limit);

            // Add each found plant to the result list
            for (Plant plant : iterable) {
                result.add(plant);
            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null; // Return null in case of an error
        }
    }

    @Override
    public void addPlant(Plant plant) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Insert the provided plant object into the collection
            collection.insertOne(plant);
        }
        // No need to catch exceptions here since the method returns void
    }

    @Override
    public boolean editPlant(ObjectId fileID, Plant newPlant) {
        return true;
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Create a filter to find the plant by its ID
            Bson filter = eq("_id", fileID);

            // Perform the delete operation
            DeleteResult result = collection.deleteOne(filter);

            // Return true if a document was deleted
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the MongoDB collection for plants.
     *
     * @param mongoClient the MongoDB client used to connect to the database
     * @return the MongoCollection of Plant objects
     */
    private MongoCollection<Plant> getPlantsCollection(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("plants", Plant.class);
    }
}
