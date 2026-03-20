import java.io.*;
import java.util.*;

// Inventory class
class RoomInventory implements Serializable {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, Integer> availability) {
        this.availability = availability;
    }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
    }
}

// Persistence Service
class PersistenceService {

    // SAVE
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {

            out.writeObject(inventory.getAvailability());
            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // LOAD
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(filePath))) {

            Map<String, Integer> data =
                    (Map<String, Integer>) in.readObject();

            inventory.setAvailability(data);
            System.out.println("Inventory restored successfully.");

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

// MAIN
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.dat";

        RoomInventory inventory = new RoomInventory();
        PersistenceService service = new PersistenceService();

        // LOAD (Recovery)
        service.loadInventory(inventory, filePath);

        // Show inventory
        inventory.display();

        // SAVE (Persistence)
        service.saveInventory(inventory, filePath);
    }
}