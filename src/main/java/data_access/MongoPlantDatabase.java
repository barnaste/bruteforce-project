package data_access;

import com.mongodb.client.*;
import entity.Plant;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
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
            MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Plant> collection = database.getCollection("plantinfo", Plant.class);
            FindIterable<Plant> iterable =  collection.find(eq("owner", username)).sort(sort).skip(skip)
                    .limit(limit);
            for (Plant plant : iterable) {
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
            MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Plant> collection = database.getCollection("plantinfo", Plant.class);
            FindIterable<Plant> iterable =  collection.find(eq("isPublic", true)).sort(sort).skip(skip)
                    .limit(limit);
            for (Plant plant : iterable) {
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
            MongoDatabase database = mongoClient.getDatabase("appDB").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Plant> collection = database.getCollection("plantinfo", Plant.class);
            collection.insertOne(plant);
        }
    }

    @Override
    public Boolean editPlant(String id) {
        //TODO: Implement this.
        return null;
    }
}
