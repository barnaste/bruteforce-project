package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Plant;
import entity.User;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoPlantDatabase implements PlantDataBase {
    final String CONNECTIONSTRING = "mongodb+srv://brute_force:CSC207-F24@cluster0.upye6.mongodb.net/" +
            "?retryWrites=true&w=majority&appName=Cluster0";

    @Override
    public List<Plant> getPlants(String username) {
        List<Plant> result = new ArrayList<>();

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Plant> collection = database.getCollection("plantinfo", Plant.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public void addPlant(Plant plant) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        try (MongoClient mongoClient = MongoClients.create(CONNECTIONSTRING)) {
            MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Plant> collection = database.getCollection("plant", Plant.class);
            collection.insertOne(plant);
        }
    }
}
