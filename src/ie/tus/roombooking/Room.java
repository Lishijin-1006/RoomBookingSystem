package ie.tus.roombooking;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private int capacity;
    private List<TimeSlot> bookedSlots = new ArrayList<>();

    public Room(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addBookedSlot(TimeSlot slot) {
        bookedSlots.add(slot);
    }

    public boolean isAvailable(TimeSlot requested) {
        for (TimeSlot booked : bookedSlots) {
            if (booked.overlaps(requested)) {
                return false;
            }
        }
        return true;
    }
}