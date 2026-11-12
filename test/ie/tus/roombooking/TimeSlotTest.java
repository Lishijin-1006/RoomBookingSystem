package ie.tus.roombooking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TimeSlotTest {

    @Test
    void overlappingSlotsOverlap() {
        TimeSlot a = new TimeSlot("2026-10-23", 9, 11);
        TimeSlot b = new TimeSlot("2026-10-23", 10, 12);
        assertTrue(a.overlaps(b));
    }

    @Test
    void backToBackSlotsDoNotOverlap() {
        TimeSlot a = new TimeSlot("2026-10-23", 9, 11);
        TimeSlot b = new TimeSlot("2026-10-23", 11, 13);
        assertFalse(a.overlaps(b));
    }

    @Test
    void differentDaysDoNotOverlap() {
        TimeSlot a = new TimeSlot("2026-10-23", 9, 11);
        TimeSlot b = new TimeSlot("2026-10-24", 9, 11);
        assertFalse(a.overlaps(b));
    }

    @Test
    void slotInsideAnotherOverlaps() {
        TimeSlot a = new TimeSlot("2026-10-23", 9, 13);
        TimeSlot b = new TimeSlot("2026-10-23", 10, 11);
        assertTrue(a.overlaps(b));
    }
    
    @Test
    void validAndInvalidSlots() {
        assertTrue(new TimeSlot("2026-11-10", 9, 11).isValid());
        assertFalse(new TimeSlot("2026-11-10", 11, 9).isValid());
        assertFalse(new TimeSlot("2026-11-10", 9, 9).isValid());
    }
}