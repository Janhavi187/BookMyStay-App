import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
    }

    void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    void displayInventory() {
        System.out.println("Room Availability:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App - UC3");
        System.out.println("Version: 3.0\n");

        RoomInventory inventory = new RoomInventory();

        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 3);
        inventory.addRoom("Suite Room", 2);

        inventory.displayInventory();
    }
}