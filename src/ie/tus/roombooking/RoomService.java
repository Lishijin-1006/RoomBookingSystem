package ie.tus.roombooking;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public RoomService() {
        Room j1007 = new Room("J1007", 30);
        Room j1010 = new Room("J1010", 12);
        Room library1 = new Room("Library Study Room 1", 6);

        // sample existing bookings
        j1007.addBookedSlot(new TimeSlot("2026-10-23", 9, 11));
        j1010.addBookedSlot(new TimeSlot("2026-10-23", 10, 12));

        rooms.add(j1007);
        rooms.add(j1010);
        rooms.add(library1);
    }

    public List<Room> searchAvailableRooms(TimeSlot requested) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable(requested)) {
                available.add(room);
            }
        }
        return available;
    }
}