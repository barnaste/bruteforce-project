package entity;
import org.junit.Test;
import org.bson.types.ObjectId;

import java.util.Date;

import static org.junit.Assert.*;

public class PlantTest {
    @Test
    public void testConstructorWithParameters() {
        // Arrange
        String imageID = "image123";
        String species = "Rose";
        String family = "Rosaceae";
        String scientificName = "Rosa";
        String owner = "user123";
        String comments = "Beautiful rose";
        Boolean isPublic = true;

        // Act
        Plant plant = new Plant(imageID, species, family, scientificName, owner, comments, isPublic);

        // Assert
        assertEquals(imageID, plant.getImageID());
        assertEquals(species, plant.getSpecies());
        assertEquals(family, plant.getFamily());
        assertEquals(scientificName, plant.getScientificName());
        assertEquals(owner, plant.getOwner());
        assertEquals(comments, plant.getComments());
        assertEquals(isPublic, plant.getIsPublic());
        assertNotNull(plant.getFileID());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        Plant plant = new Plant();

        // Assert
        assertNull(plant.getImageID());
        assertNull(plant.getSpecies());
        assertNull(plant.getFamily());
        assertNull(plant.getScientificName());
        assertNull(plant.getOwner());
        assertNull(plant.getComments());
        assertNull(plant.getIsPublic());
        assertNull(plant.getLastChanged());
        assertEquals(0, plant.getNumOfLikes());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        Plant plant = new Plant();
        ObjectId fileID = new ObjectId();
        String imageID = "image456";
        String species = "Tulip";
        String family = "Liliaceae";
        String scientificName = "Tulipa";
        String owner = "user456";
        String comments = "Lovely tulip";
        Boolean isPublic = false;
        Date lastChanged = new Date();
        int numOfLikes = 10;

        // Act
        plant.setFileID(fileID);
        plant.setImageID(imageID);
        plant.setSpecies(species);
        plant.setFamily(family);
        plant.setScientificName(scientificName);
        plant.setOwner(owner);
        plant.setComments(comments);
        plant.setIsPublic(isPublic);
        plant.setLastChanged(lastChanged);
        plant.setNumOfLikes(numOfLikes);

        // Assert
        assertEquals(fileID, plant.getFileID());
        assertEquals(imageID, plant.getImageID());
        assertEquals(species, plant.getSpecies());
        assertEquals(family, plant.getFamily());
        assertEquals(scientificName, plant.getScientificName());
        assertEquals(owner, plant.getOwner());
        assertEquals(comments, plant.getComments());
        assertEquals(isPublic, plant.getIsPublic());
        assertEquals(lastChanged, plant.getLastChanged());
        assertEquals(numOfLikes, plant.getNumOfLikes());
    }
}
