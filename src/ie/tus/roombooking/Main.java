package ie.tus.roombooking;

public class Main {
    public static void main(String[] args) {
        System.out.println("Room Booking System");
        Room room = new Room("J1007");
        System.out.println("Example room: " + room.getRoomName());
    }
}