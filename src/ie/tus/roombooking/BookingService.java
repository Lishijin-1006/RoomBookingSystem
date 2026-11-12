package ie.tus.roombooking;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private int nextId = 1;

    public Booking createBooking(User user, Room room, TimeSlot slot) {
        if (!slot.isValid()) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        Booking booking = new Booking(nextId, user, room, slot);
        nextId++;
        bookings.add(booking);
        room.addBookedSlot(slot);
        return booking;
    }

    public List<Booking> getBookingsFor(User user) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().getUsername().equals(user.getUsername())) {
                result.add(booking);
            }
        }
        return result;
    }
}