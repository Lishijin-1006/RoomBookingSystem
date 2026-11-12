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
        BookingService bookingService = new BookingService();

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
        showMainMenu(scanner, user, roomService, bookingService);

        scanner.close();
    }

    private static void showMainMenu(Scanner scanner, User user,
            RoomService roomService, BookingService bookingService) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1. Search available rooms");
            System.out.println("2. Book a room");
            System.out.println("3. View my booking history");
            System.out.println("4. Cancel a booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                searchRooms(scanner, roomService);
            } else if (choice.equals("2")) {
                bookRoom(scanner, user, roomService, bookingService);
            } else if (choice.equals("3")) {
                viewMyBookings(user, bookingService);
            } else if (choice.equals("4")) {
                cancelBooking(scanner, user, bookingService);
            } else if (choice.equals("5")) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void searchRooms(Scanner scanner, RoomService roomService) {
        System.out.println("=== Search Rooms ===");
        TimeSlot requested = readTimeSlot(scanner);
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

    private static void bookRoom(Scanner scanner, User user,
            RoomService roomService, BookingService bookingService) {
        System.out.println("=== Book a Room ===");
        TimeSlot slot = readTimeSlot(scanner);
        List<Room> results = roomService.searchAvailableRooms(slot);

        if (results.isEmpty()) {
            System.out.println("No rooms available for this time.");
            return;
        }

        System.out.println("Available rooms for " + slot + ":");
        for (int i = 0; i < results.size(); i++) {
            Room room = results.get(i);
            System.out.println((i + 1) + ". " + room.getRoomName() + " (capacity " + room.getCapacity() + ")");
        }

        int choice = readNumber(scanner, "Choose a room number: ", 1, results.size());
        Booking booking = bookingService.createBooking(user, results.get(choice - 1), slot);
        System.out.println("Booking confirmed: " + booking);
    }

    private static void viewMyBookings(User user, BookingService bookingService) {
        System.out.println("=== My Booking History ===");
        List<Booking> myBookings = bookingService.getBookingsFor(user);

        if (myBookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            for (Booking booking : myBookings) {
                System.out.println("- " + booking);
            }
        }
    }
    
    private static void cancelBooking(Scanner scanner, User user, BookingService bookingService) {
        System.out.println("=== Cancel a Booking ===");
        List<Booking> myBookings = bookingService.getBookingsFor(user);

        if (myBookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        for (int i = 0; i < myBookings.size(); i++) {
            System.out.println((i + 1) + ". " + myBookings.get(i));
        }

        int choice = readNumber(scanner, "Choose a booking to cancel: ", 1, myBookings.size());
        Booking selected = myBookings.get(choice - 1);
        bookingService.cancelBooking(user, selected.getId());
        System.out.println("Booking #" + selected.getId() + " cancelled.");
    }

    private static TimeSlot readTimeSlot(Scanner scanner) {
        String date = readDate(scanner);
        while (true) {
            int start = readNumber(scanner, "Start hour (0-23): ", 0, 23);
            int end = readNumber(scanner, "End hour (1-24): ", 1, 24);
            if (end > start) {
                return new TimeSlot(date, start, end);
            }
            System.out.println("Error: end hour must be after start hour. Please try again.");
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

    private static int readNumber(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // not a number, fall through to the error message
            }
            System.out.println("Error: please enter a whole number from " + min + " to " + max + ".");
        }
    }
}