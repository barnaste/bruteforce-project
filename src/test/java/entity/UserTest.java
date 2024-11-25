package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void setCommentsTest() {
        // test the functionality of setUsername and getUsername
        User user = new User();
        user.setUsername("arz");
        assertEquals("arz", user.getUsername());
    }
}
