package ie.tus.roombooking;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        RoomService roomService = new RoomService();

        System.out.println("Room Booking System");

        User user = null;
        while (user == null) {
            System.out.println("=== Login ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            user = userService.login(username, password);
            if (user == null) {
                System.out.println("Error: invalid username or password. Please try again.");
                System.out.println();
            }
        }

        System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
        showMainMenu(scanner, roomService);

        scanner.close();
    }

    private static void showMainMenu(Scanner scanner, RoomService roomService) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1. Search available rooms");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                searchRooms(scanner, roomService);
            } else if (choice.equals("2")) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void searchRooms(Scanner scanner, RoomService roomService) {
        System.out.println("=== Search Rooms ===");
        System.out.print("Date (e.g. 2026-10-23): ");
        String date = scanner.nextLine();
        System.out.print("Start hour (0-23): ");
        int start = Integer.parseInt(scanner.nextLine());
        System.out.print("End hour (1-24): ");
        int end = Integer.parseInt(scanner.nextLine());

        TimeSlot requested = new TimeSlot(date, start, end);
        List<Room> results = roomService.searchAvailableRooms(requested);

        System.out.println("Available rooms for " + requested + ":");
        for (Room room : results) {
            System.out.println("- " + room.getRoomName() + " (capacity " + room.getCapacity() + ")");
        }
    }
}