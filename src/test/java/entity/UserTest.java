package entity;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void testConstructorWithParameters() {
        // Arrange
        String username = "testUser";
        String password = "securePassword";

        // Act
        User user = new User(username, password);

        // Assert
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        User user = new User();
        String username = "newUser";
        String password = "newPassword";

        // Act
        user.setUsername(username);
        user.setPassword(password);

        // Assert
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }
}
