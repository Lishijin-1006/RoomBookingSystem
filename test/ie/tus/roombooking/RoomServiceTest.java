package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RoomServiceTest {

    @Test
    void searchReturnsOnlyFreeRooms() {
        RoomService service = new RoomService();
        List<Room> results = service.searchAvailableRooms(new TimeSlot("2026-10-23", 9, 10));
        assertEquals(1, results.size());
        assertEquals("J1010", results.get(0).getRoomName());
    }

    @Test
    void searchReturnsEmptyListWhenNoRoomsFree() {
        RoomService service = new RoomService();
        List<Room> results = service.searchAvailableRooms(new TimeSlot("2026-10-23", 10, 11));
        assertTrue(results.isEmpty());
    }

    @Test
    void searchReturnsAllRoomsWhenNothingBooked() {
        RoomService service = new RoomService();
        List<Room> results = service.searchAvailableRooms(new TimeSlot("2026-10-23", 14, 15));
        assertEquals(3, results.size());
    }
}
