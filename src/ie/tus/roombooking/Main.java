package ie.tus.roombooking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        String date = readDate(scanner);

        int start;
        int end;
        while (true) {
            start = readHour(scanner, "Start hour (0-23): ", 0, 23);
            end = readHour(scanner, "End hour (1-24): ", 1, 24);
            if (end > start) {
                break;
            }
            System.out.println("Error: end hour must be after start hour. Please try again.");
        }

        TimeSlot requested = new TimeSlot(date, start, end);
        List<Room> results = roomService.searchAvailableRooms(requested);

        if (results.isEmpty()) {
            System.out.println("No rooms available for this time.");
        } else {
            System.out.println("Available rooms for " + requested + ":");
            for (Room room : results) {
                System.out.println("- " + room.getRoomName() + " (capacity " + room.getCapacity() + ")");
            }
        }
    }

    private static String readDate(Scanner scanner) {
        while (true) {
            System.out.print("Date (e.g. 2026-10-23): ");
            String input = scanner.nextLine().trim();
            try {
                LocalDate.parse(input);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Error: please enter the date as YYYY-MM-DD.");
            }
        }
    }

    private static int readHour(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int hour = Integer.parseInt(input);
                if (hour >= min && hour <= max) {
                    return hour;
                }
            } catch (NumberFormatException e) {
                // not a number, fall through to the error message
            }
            System.out.println("Error: please enter a whole number from " + min + " to " + max + ".");
        }
    }
}