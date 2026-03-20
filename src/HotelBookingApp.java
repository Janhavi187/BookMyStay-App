import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }
}

// Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }
}

// Validator
class ReservationValidator {

    public void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected");
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation\n");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue queue = new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Validation (case sensitive)
            validator.validate(type, inventory);

            Reservation r = new Reservation(name, type);
            queue.addRequest(r);

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}