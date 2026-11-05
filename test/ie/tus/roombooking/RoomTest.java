package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    void roomWithNoBookingsIsAvailable() {
        Room room = new Room("J1007", 30);
        assertTrue(room.isAvailable(new TimeSlot("2026-10-23", 9, 10)));
    }

    @Test
    void roomIsNotAvailableDuringABooking() {
        Room room = new Room("J1007", 30);
        room.addBookedSlot(new TimeSlot("2026-10-23", 9, 11));
        assertFalse(room.isAvailable(new TimeSlot("2026-10-23", 10, 12)));
    }

    @Test
    void roomIsAvailableAfterABookingEnds() {
        Room room = new Room("J1007", 30);
        room.addBookedSlot(new TimeSlot("2026-10-23", 9, 11));
        assertTrue(room.isAvailable(new TimeSlot("2026-10-23", 11, 12)));
    }
}