package ie.tus.roombooking;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("student1", "pass123"));
        users.add(new User("student2", "hello456"));
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }
}