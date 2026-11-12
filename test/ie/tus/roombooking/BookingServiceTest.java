package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BookingServiceTest {

    @Test
    void bookingIsSavedUnderUsersAccount() {
        // Arrange
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);

        // Act
        service.createBooking(user, room, new TimeSlot("2026-11-10", 9, 11));

        // Assert
        List<Booking> myBookings = service.getBookingsFor(user);
        assertEquals(1, myBookings.size());
        assertEquals("J1007", myBookings.get(0).getRoom().getRoomName());
    }

    @Test
    void bookedRoomIsNoLongerAvailable() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);
        TimeSlot slot = new TimeSlot("2026-11-10", 9, 11);

        service.createBooking(user, room, slot);

        assertFalse(room.isAvailable(slot));
    }

    @Test
    void eachBookingGetsANewId() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");

        Booking first = service.createBooking(user, new Room("J1007", 30), new TimeSlot("2026-11-10", 9, 11));
        Booking second = service.createBooking(user, new Room("J1010", 12), new TimeSlot("2026-11-10", 9, 11));

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void usersOnlySeeTheirOwnBookings() {
        BookingService service = new BookingService();
        User student1 = new User("student1", "pass123");
        User student2 = new User("student2", "hello456");

        service.createBooking(student1, new Room("J1007", 30), new TimeSlot("2026-11-10", 9, 11));

        assertEquals(1, service.getBookingsFor(student1).size());
        assertEquals(0, service.getBookingsFor(student2).size());
    }
    
    @Test
    void bookingWithEndBeforeStartIsRejected() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);

        assertThrows(IllegalArgumentException.class,
                () -> service.createBooking(user, room, new TimeSlot("2026-11-10", 11, 9)));
        assertEquals(0, service.getBookingsFor(user).size());
    }

    @Test
    void bookingWithSameStartAndEndIsRejected() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);

        assertThrows(IllegalArgumentException.class,
                () -> service.createBooking(user, room, new TimeSlot("2026-11-10", 9, 9)));
    }
    
    // LSJABC-11 Prevent double booking

    @Test
    void sameRoomSameTimeIsRejected() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room roomA = new Room("J1007", 30);
        service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 11));

        assertThrows(IllegalStateException.class,
                () -> service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 11)));
    }

    @Test
    void sameRoomOverlappingTimeIsRejected() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room roomA = new Room("J1007", 30);
        service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 11));

        assertThrows(IllegalStateException.class,
                () -> service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 12)));
    }

    @Test
    void sameRoomBackToBackSucceeds() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room roomA = new Room("J1007", 30);
        service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 11));

        service.createBooking(user, roomA, new TimeSlot("2026-11-10", 11, 12));

        assertEquals(2, service.getBookingsFor(user).size());
    }

    @Test
    void differentRoomSameTimeSucceeds() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room roomA = new Room("J1007", 30);
        Room roomB = new Room("J1010", 12);
        service.createBooking(user, roomA, new TimeSlot("2026-11-10", 10, 11));

        service.createBooking(user, roomB, new TimeSlot("2026-11-10", 10, 11));

        assertEquals(2, service.getBookingsFor(user).size());
    }
    
    // LSJABC-12 Cancel a booking

    @Test
    void cancelledBookingIsRemovedFromMyBookings() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Booking booking = service.createBooking(user, new Room("J1007", 30), new TimeSlot("2026-11-10", 9, 11));

        boolean cancelled = service.cancelBooking(user, booking.getId());

        assertTrue(cancelled);
        assertEquals(0, service.getBookingsFor(user).size());
    }

    @Test
    void cancelledRoomIsAvailableAgain() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");
        Room room = new Room("J1007", 30);
        TimeSlot slot = new TimeSlot("2026-11-10", 9, 11);
        Booking booking = service.createBooking(user, room, slot);

        service.cancelBooking(user, booking.getId());

        assertTrue(room.isAvailable(slot));
    }

    @Test
    void cannotCancelSomeoneElsesBooking() {
        BookingService service = new BookingService();
        User student1 = new User("student1", "pass123");
        User student2 = new User("student2", "hello456");
        Booking booking = service.createBooking(student1, new Room("J1007", 30), new TimeSlot("2026-11-10", 9, 11));

        boolean cancelled = service.cancelBooking(student2, booking.getId());

        assertFalse(cancelled);
        assertEquals(1, service.getBookingsFor(student1).size());
    }

    @Test
    void cancellingUnknownBookingReturnsFalse() {
        BookingService service = new BookingService();
        User user = new User("student1", "pass123");

        assertFalse(service.cancelBooking(user, 99));
    }
}