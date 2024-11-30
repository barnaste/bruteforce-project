package data_access;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entity.Plant;
import use_case.PlantDataAccessInterface;

/**
 * Singleton class that provides data access methods for plant objects in a MongoDB database.
 * Implements the PlantDataAccessInterface to interact with plant data, including retrieving,
 * inserting, updating, and deleting plant records. It also supports sorting, pagination, and
 * liking plants, as well as fetching public plants and user-specific plants.
 *
 * <p>
 * This class uses MongoDB's Java driver to connect to a remote MongoDB instance and
 * performs operations on a "plants" collection within the "appDB" database. It ensures
 * a single instance of this class is used across the application through the singleton pattern.
 *
 * <p>
 * Note: This class uses MongoDB's default codec registry for serializing and deserializing POJOs.
 */
public final class MongoPlantDataAccessObject implements PlantDataAccessInterface {
    private static MongoPlantDataAccessObject instance;

    private static final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/"
            + "?retryWrites=true&w=majority&appName=Cluster0";
    private final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(pojoCodecProvider)
    );

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private MongoPlantDataAccessObject() {
    }

    /**
     * Retrieves the singleton instance of the MongoPlantDataAccessObject.
     *
     * <p>
     * This method ensures that only one instance of the MongoPlantDataAccessObject exists at any time,
     * adhering to the singleton design pattern.
     *
     * @return the singleton instance of MongoPlantDataAccessObject
     */
    public static MongoPlantDataAccessObject getInstance() {
        if (instance == null) {
            instance = new MongoPlantDataAccessObject();
        }
        return instance;
    }

    @Override
    public List<Plant> getUserPlants(String username, int skip, int limit) {
        final List<Plant> result = new ArrayList<>();
        final Bson sort = Sorts.descending("lastChanged");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find user's plants, sorted and paginated
            final FindIterable<Plant> iterable = collection.find(Filters.eq("owner", username))
                    .sort(sort)
                    .skip(skip)
                    .limit(limit);

            for (Plant plant : iterable) {
                result.add(plant);
            }
            return result;
        }
    }

    @Override
    public List<Plant> getUserPlants(String username) {
        final List<Plant> res = new ArrayList<>();
        final Bson sort = Sorts.descending("lastChanged");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find user's plants, sorted by lastChanged in descending order
            final FindIterable<Plant> iterable = collection.find(Filters.eq("owner", username))
                    .sort(sort);

            // Add each plant to the result list
            for (Plant plant : iterable) {
                res.add(plant);
            }
        }
        return res;
    }

    @Override
    public List<Plant> getPublicPlants(int skip, int limit) {
        final List<Plant> result = new ArrayList<>();
        final Bson sort = Sorts.descending("numOfLikes");
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find public plants, apply sorting, skipping, and limiting results
            final FindIterable<Plant> iterable = collection.find(Filters.eq("isPublic", true))
                    .sort(sort)
                    .skip(skip)
                    .limit(limit);

            // Add each found plant to the result list
            for (Plant plant : iterable) {
                result.add(plant);
            }

            return result;
        }
    }

    @Override
    public void addPlant(Plant plant) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);
            plant.setLastChanged(new Date());
            plant.setNumOfLikes(0);
            // Insert the provided plant object into the collection
            collection.insertOne(plant);
        }
    }

    @Override
    public boolean editPlant(ObjectId fileID, boolean isPublic, String comments) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Update the plant's isPublic and comments fields
            final Bson filter = Filters.eq("fileID", fileID);
            final Bson update = new Document("$set", new Document("isPublic", isPublic)
                    .append("comments", comments).append("lastChanged", new Date()));

            // Perform the update operation
            final UpdateResult result = collection.updateOne(filter, update);

            return result.getModifiedCount() > 0;
        }
    }

    @Override
    public Plant fetchPlantByID(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Find the plant by its ID
            return collection.find(Filters.eq("fileID", fileID)).first();
        }
    }

    @Override
    public boolean likePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Update the numOfLikes field by incrementing it
            final Bson filter = Filters.eq("fileID", fileID);
            final Bson update = new Document("$inc", new Document("numOfLikes", 1));

            // Perform the update operation
            final UpdateResult result = collection.updateOne(filter, update);

            return result.getModifiedCount() > 0;
        }
    }

    @Override
    public boolean deletePlant(ObjectId fileID) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Create a filter to find the plant by its ID
            final Bson filter = Filters.eq("fileID", fileID);

            // Perform the delete operation
            final DeleteResult result = collection.deleteOne(filter);

            // Return true if a document was deleted
            return result.getDeletedCount() > 0;
        }
    }

    /**
     * Retrieves the MongoDB collection for plants.
     *
     * @param mongoClient the MongoDB client used to connect to the database
     * @return the MongoCollection of Plant objects
     */
    private MongoCollection<Plant> getPlantsCollection(MongoClient mongoClient) {
        final MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("plants", Plant.class);
    }

    @Override
    public int getNumberOfPublicPlants() {
        int result = 0;
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Count the number of public plants in the collection
            final long count = collection.countDocuments(Filters.eq("isPublic", true));

            // MongoDB countDocuments returns a long, so we cast it to int and return
            result = (int) count;
        }
        return result;
    }

    @Override
    public int getNumberOfUserPlants(String username) {
        int result = 0;
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);

            // Count the number of plants owned by the user
            final long count = collection.countDocuments(Filters.eq("owner", username));

            // MongoDB countDocuments returns a long, so we cast it to int and return
            result = (int) count;
        }
        return result;
    }

    /**
     * Deletes all plants from the MongoDB collection.
     * This method removes all documents from the "plants" collection. TESTING ONLY.
     *
     * @throws RuntimeException if an error occurs while deleting plants.
     */
    public void deleteAll() {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<Plant> collection = getPlantsCollection(mongoClient);
            collection.deleteMany(Filters.empty());
        }
    }
}
