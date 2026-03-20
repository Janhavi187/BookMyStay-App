import java.util.*;

// Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());

        serviceMap.get(reservationId).add(service);
    }

    // Calculate total cost
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        // Add services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("WiFi", 300));
        manager.addService(reservationId, new Service("Spa", 700));

        // Calculate total
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}