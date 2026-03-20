import java.util.*;

// Reservation
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// Inventory (Thread Safe)
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 3);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public synchronized boolean allocate(String type) {
        if (availability.get(type) > 0) {
            availability.put(type, availability.get(type) - 1);
            return true;
        }
        return false;
    }

    public void printRemaining() {
        System.out.println("\nRemaining Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
    }
}

// Allocation Service
class RoomAllocationService {

    private Map<String, Integer> counter = new HashMap<>();

    public synchronized String allocateRoom(String type) {
        counter.put(type, counter.getOrDefault(type, 0) + 1);
        return type + "-" + counter.get(type);
    }
}

// Thread Worker
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private RoomInventory inventory;
    private RoomAllocationService service;

    public ConcurrentBookingProcessor(
            BookingRequestQueue queue,
            RoomInventory inventory,
            RoomAllocationService service) {

        this.queue = queue;
        this.inventory = inventory;
        this.service = service;
    }

    public void run() {
        while (true) {

            Reservation r;

            synchronized (queue) {
                if (!queue.hasRequests()) break;
                r = queue.getNextRequest();
            }

            if (r != null) {
                synchronized (inventory) {

                    if (inventory.allocate(r.roomType)) {

                        String roomId = service.allocateRoom(r.roomType);

                        System.out.println("Booking confirmed for Guest: "
                                + r.guestName
                                + ", Room ID: "
                                + roomId);

                    } else {
                        System.out.println("No rooms available for " + r.guestName);
                    }
                }
            }
        }
    }
}

// MAIN
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vannathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        // Threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(queue, inventory, service));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        inventory.printRemaining();
    }
}