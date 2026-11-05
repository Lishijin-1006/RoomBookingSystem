package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    void validLoginReturnsUser() {
        UserService service = new UserService();
        User user = service.login("student1", "pass123");
        assertNotNull(user);
        assertEquals("student1", user.getUsername());
    }

    @Test
    void secondUserCanLogIn() {
        UserService service = new UserService();
        User user = service.login("student2", "hello456");
        assertNotNull(user);
    }

    @Test
    void wrongPasswordReturnsNull() {
        UserService service = new UserService();
        User user = service.login("student1", "wrong");
        assertNull(user);
    }

    @Test
    void unknownUserReturnsNull() {
        UserService service = new UserService();
        User user = service.login("nobody", "pass123");
        assertNull(user);
    }
}