package data_access;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import entity.User;

/**
 * UserDB class implemented using MongoDB.
 */
public final class MongoUserDataAccessObject implements use_case.UserDataAccessInterface {
    private static MongoUserDataAccessObject instance;

    private static final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/"
            + "?retryWrites=true&w=majority&appName=Cluster0";
    private final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));
    private String currentUsername;

    /**
     * The private constructor -- if a new instance of this class is to be requested, it should be done
     * by calling the getInstance() public method.
     */
    private MongoUserDataAccessObject() {

    }

    /**
     * Retrieves the singleton instance of the MongoUserDataAccessObject.
     *
     * <p>
     * This method ensures that only one instance of MongoUserDataAccessObject is created and used
     * throughout the application, following the singleton design pattern.
     *
     * @return the singleton instance of MongoUserDataAccessObject
     */
    public static MongoUserDataAccessObject getInstance() {
        if (instance == null) {
            instance = new MongoUserDataAccessObject();
        }
        return instance;
    }

    @Override
    public User getUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            return getUsersCollection(mongoClient).find(Filters.eq("username", username)).first();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            return getUsersCollection(mongoClient).find(Filters.eq("username", username)).first() != null;
        }
    }

    @Override
    public void addUser(User user) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final MongoCollection<User> collection = getUsersCollection(mongoClient);
            if (getUsersCollection(mongoClient).find(Filters.eq("username", user.getUsername())).first() == null) {
                collection.insertOne(user);
            }
        }
    }

    @Override
    public boolean deleteUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            final DeleteResult result = getUsersCollection(mongoClient).deleteOne(Filters.eq("username", username));
            return result.getDeletedCount() > 0;
        }
    }

    /**
     * Retrieves the current username.
     *
     * @return The current username.
     */
    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * Sets the current username.
     *
     * @param username The username to set as the current username.
     */
    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    private MongoCollection<User> getUsersCollection(MongoClient mongoClient) {
        final MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("users", User.class);
    }
}
