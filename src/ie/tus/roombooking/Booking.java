package ie.tus.roombooking;

public class Booking {
    private int id;
    private User user;
    private Room room;
    private TimeSlot slot;

    public Booking(int id, User user, Room room, TimeSlot slot) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.slot = slot;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "Booking #" + id + ": " + room.getRoomName() + ", " + slot
                + " (" + user.getUsername() + ")";
    }
}