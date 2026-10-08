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
            showMainMenu(scanner);
        } else {
            System.out.println("Login failed.");
        }

        scanner.close();
    }

    private static void showMainMenu(Scanner scanner) {
        Room room = new Room("J1007", 30);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1. View example room");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Room: " + room.getRoomName() + ", Capacity: " + room.getCapacity());
            } else if (choice.equals("2")) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}