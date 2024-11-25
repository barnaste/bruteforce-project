package entity;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlantTest {
    @Test
    public void setCommentsTest() {
        Plant plant = new Plant();
        plant.setComments("My comments");
        assertEquals("My comments", plant.getComments());
    }
}
