package ie.tus.roombooking;

public class Main {
    public static void main(String[] args) {
        System.out.println("Room Booking System");
        Room room = new Room("J1007", 30);
        System.out.println("Example room: " + room.getRoomName());
        System.out.println("Capacity: " + room.getCapacity());

        UserService userService = new UserService();
        User valid = userService.login("student1", "pass123");
        System.out.println("Valid login test: " + (valid != null));
        User invalid = userService.login("student1", "wrong");
        System.out.println("Invalid login test: " + (invalid != null));
    }
}