package ie.tus.roombooking;

public class TimeSlot {
    private String date;     // format: 2026-10-23
    private int startHour;   // 24-hour clock, e.g. 9
    private int endHour;     // e.g. 11

    public TimeSlot(String date, int startHour, int endHour) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getDate() {
        return date;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public boolean overlaps(TimeSlot other) {
        if (!date.equals(other.date)) {
            return false;
        }
        return startHour < other.endHour && other.startHour < endHour;
    }
    
    public boolean isValid() {
        return startHour >= 0 && endHour <= 24 && endHour > startHour;
    }

    @Override
    public String toString() {
        return date + " " + startHour + ":00-" + endHour + ":00";
    }
}