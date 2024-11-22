package data_access;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entity.Plant;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import use_case.PlantDataAccessInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoPlantDataAccessInterface implements PlantDataAccessInterface {
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

    public List<Plant> getPublicPlants(int skip, int limit) {
        List<Plant> result = new ArrayList<>();
        Bson sort = descending("numOfLikes");
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
            plant.setLastChanged(new Date());
            plant.setNumOfLikes(0);
            // Insert the provided plant object into the collection
            collection.insertOne(plant);
        }
        // No need to catch exceptions here since the method returns void
    }

    @Override
    public boolean editPlant(ObjectId fileID, boolean isPublic, String comments) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Update the plant's isPublic and comments fields
            Bson filter = eq("fileID", fileID);
            Bson update = new Document("$set", new Document("isPublic", isPublic)
                    .append("comments", comments).append("lastChanged", new Date()));

            // Perform the update operation
            UpdateResult result = collection.updateOne(filter, update);

            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Plant fetchPlantByID(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find the plant by its ID
            return collection.find(eq("fileID", fileID)).first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean likePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Update the numOfLikes field by incrementing it
            Bson filter = eq("fileID", fileID);
            Bson update = new Document("$inc", new Document("numOfLikes", 1));

            // Perform the update operation
            UpdateResult result = collection.updateOne(filter, update);

            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Create a filter to find the plant by its ID
            Bson filter = eq("fileID", fileID);

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

    @Override
    public int getNumberOfPublicPlants() {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Count the number of public plants in the collection
            long count = collection.countDocuments(eq("isPublic", true));

            // Return the count as an int (casting from long)
            return (int) count; // MongoDB countDocuments returns a long, so we cast it to int
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Return 0 in case of an error
        }
    }

    @Override
    public int getNumberOfUserPlants(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Count the number of plants owned by the user
            long count = collection.countDocuments(eq("owner", username));

            // Return the count as an int
            return (int) count; // MongoDB countDocuments returns a long, so we cast it to int
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Return 0 in case of an error
        }
    }



    /**
     * Deletes all plants from the MongoDB collection.
     * This method removes all documents from the "plants" collection. This is for testing purposes and testing purposes only.
     *
     * @throws RuntimeException if an error occurs while deleting plants.
     */
    public void deleteAll() {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Delete all plants in the collection by using an empty filter
            DeleteResult result = collection.deleteMany(Filters.empty());

            // Optionally, you could check result.getDeletedCount(), but this is not needed if you don't care about the result
        } catch (Exception e) {
            // Wrap and throw the exception as a RuntimeException
            throw new RuntimeException("Error deleting all plants: " + e.getMessage(), e);
        }
    }
}
