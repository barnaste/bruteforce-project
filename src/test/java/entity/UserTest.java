package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void setCommentsTest() {
        User user = new User();
        user.setUsername("arz");
        assertEquals("arz", user.getUsername());
    }
}
