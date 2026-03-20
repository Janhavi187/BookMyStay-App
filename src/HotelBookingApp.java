import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    int beds;
    int size;
    double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails(String type, int available) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + available + "\n");
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000);
    }
}

// Inventory Class
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}

// Search Service
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        if (availability.get("Single") > 0) {
            singleRoom.displayDetails("Single", availability.get("Single"));
        }

        if (availability.get("Double") > 0) {
            doubleRoom.displayDetails("Double", availability.get("Double"));
        }

        if (availability.get("Suite") > 0) {
            suiteRoom.displayDetails("Suite", availability.get("Suite"));
        }
    }
}

// MAIN CLASS (ONLY THIS WILL RUN)
public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}