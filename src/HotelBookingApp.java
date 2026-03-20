import java.util.*;

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

// Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Allocation Service
class RoomAllocationService {

    // Track assigned rooms
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + r.getGuestName());
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRooms.putIfAbsent(type, new HashSet<>());

        // Ensure uniqueness
        while (allocatedRooms.get(type).contains(roomId)) {
            roomId = generateRoomId(type);
        }

        allocatedRooms.get(type).add(roomId);

        inventory.decrease(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: "
                + roomId);
    }

    private String generateRoomId(String type) {
        int num = (int)(Math.random() * 100);
        return type + "-" + num;
    }
}

// MAIN
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Requests (FIFO)
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vannathi", "Suite"));

        // Process queue
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}