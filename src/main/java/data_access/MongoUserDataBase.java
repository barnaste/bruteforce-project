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
/**
 * UserDB class implemented using MongoDB.
 */
public class MongoUserDataBase implements UserDataBase{
    final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";

    @Override
    public User getUser(String username) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoDatabase database = mongoClient.getDatabase("userDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> collection = database.getCollection("users", User.class);
            return collection.find(eq("username", username)).first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addUser(User user) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoDatabase database = mongoClient.getDatabase("userDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> collection = database.getCollection("users", User.class);
            if (collection.find(eq("username", user.getUsername())).first() == null) {
                collection.insertOne(user);
                return true;
            } else {
                return false;
            }
        }
    }

    private MongoCollection<User> getUsersCollection() throws Exception {
        return null;
    }
}
