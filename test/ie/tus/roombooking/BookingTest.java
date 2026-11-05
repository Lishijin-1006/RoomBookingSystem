package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BookingTest {

    @Test
    void bookingStoresUserRoomAndSlot() {
        // Arrange
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);
        TimeSlot slot = new TimeSlot("2026-11-10", 9, 11);

        // Act
        Booking booking = new Booking(1, user, room, slot);

        // Assert
        assertEquals(1, booking.getId());
        assertEquals("student1", booking.getUser().getUsername());
        assertEquals("J1007", booking.getRoom().getRoomName());
        assertEquals(slot, booking.getSlot());
    }

    @Test
    void toStringShowsBookingDetails() {
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);
        TimeSlot slot = new TimeSlot("2026-11-10", 9, 11);

        Booking booking = new Booking(1, user, room, slot);

        assertEquals("Booking #1: J1007, 2026-11-10 9:00-11:00 (student1)", booking.toString());
    }
}