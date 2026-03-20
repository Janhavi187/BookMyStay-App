abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    abstract void display();
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App - UC2");
        System.out.println("Version: 2.0\n");

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        r1.display();
        System.out.println("Available: " + singleAvailable + "\n");

        r2.display();
        System.out.println("Available: " + doubleAvailable + "\n");

        r3.display();
        System.out.println("Available: " + suiteAvailable);
    }
}