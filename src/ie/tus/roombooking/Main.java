package ie.tus.roombooking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("Room Booking System");
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
        } else {
            System.out.println("Login failed.");
        }

        scanner.close();
    }
}