package data_access;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import entity.User;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.upload.UploadUserDataAccessInterface;

/**
 * UserDB class implemented using MongoDB.
 */
public class MongoUserDataAccessObject implements LoginUserDataAccessInterface,
                                                  SignupUserDataAccessInterface,
                                                  LogoutUserDataAccessInterface,
                                                  UploadUserDataAccessInterface {
    final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    private String currentUsername;


    @Override
    public User getUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            return getUsersCollection(mongoClient).find(eq("username", username)).first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            return getUsersCollection(mongoClient).find(eq("username", username)).first() != null;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Replace with logging
            return false;
        }
    }

    @Override
    public void addUser(User user) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoCollection<User> collection = getUsersCollection(mongoClient);
            if (getUsersCollection(mongoClient).find(eq("username", user.getUsername())).first() == null) {
                collection.insertOne(user);
            }
        }
    }

    @Override
    public boolean deleteUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            DeleteResult result = getUsersCollection(mongoClient).deleteOne(eq("username", username));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
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
        MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("users", User.class);
    }
}
