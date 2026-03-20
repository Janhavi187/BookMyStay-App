import java.util.*;

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public void increase(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailable(String type) {
        return availability.get(type);
    }
}

// Cancellation Service (Rollback using Stack)
class CancellationService {

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    // Simulate stored reservations
    private Map<String, String> reservationMap = new HashMap<>();

    public CancellationService() {
        // Example existing booking
        reservationMap.put("Single-1", "Single");
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request!");
            return;
        }

        String roomType = reservationMap.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increase(roomType);

        // Remove reservation
        reservationMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        while (!rollbackStack.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackStack.pop());
        }
    }
}

// MAIN
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation\n");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String reservationId = "Single-1";

        service.cancelBooking(reservationId, inventory);

        service.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailable("Single"));
    }
}