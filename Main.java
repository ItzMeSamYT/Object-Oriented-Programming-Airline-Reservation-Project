import java.util.Scanner;
class ExceededMaxSizeException extends Exception {
    public ExceededMaxSizeException(String msg) {
        super(msg);
    }
}

class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String msg) {
        super(msg);
    }
}

abstract class User {
    String loginID,password;
    public User(String loginID, String password) {
        this.loginID=loginID;
        this.password=password;
    }

    abstract void displayInfo();
}

class Traveler extends User {
    public Traveler(String loginID, String password) {
        super(loginID,password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome "+loginID+"!\n");
    }
}

class Manager extends User {
    public Manager(String loginID, String password) {
        super(loginID,password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome, Manager "+loginID+"!");
        System.out.println("You have administrator permissions.");
    }

    public void addFlight(FlightSchedule sch, Flight flight) {
        try {
            sch.addFlight(flight);
        } catch (ExceededMaxSizeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateFlight(FlightSchedule sch, Flight flight) throws InvalidChoiceException {
        String choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to update?\nOptions: Flight ID, Origin, Destination, Total Seats, Seat Price, Catering Availability, Status");
        choice = sc.nextLine();
        switch (choice.toUpperCase()) {
            case "FLIGHT ID": 
                System.out.println("Current Flight ID: "+flight.flightId);
                System.out.print("New Flight ID: ");
                flight.flightId = sc.nextLine();
                System.out.println("Flight ID updated to "+flight.flightId+" successfully!");
                break;
            case "ORIGIN":
                System.out.println("Current Origin: "+flight.origin);
                System.out.print("New Origin: ");
                flight.origin = sc.nextLine();
                System.out.println("Origin updated to "+flight.origin+" successfully!");
                break;
            case "DESTINATION":
                System.out.println("Current destination: "+flight.destination);
                System.out.print("New destination: ");
                flight.destination=sc.nextLine();
                System.out.println("Destination updated to "+flight.destination+" successfully!");
                break;
            case "TOTAL SEATS":
                System.out.println("Current total seats: "+flight.totalSeats);
                System.out.print("New total seats: ");
                flight.totalSeats = sc.nextInt();
                System.out.println("Total seats updated to "+flight.totalSeats+" successfully!");
                break;
            case "SEAT PRICE":
                System.out.println("Current seat price: "+flight.seatPrice);
                System.out.print("New seat price: ");
                flight.seatPrice = sc.nextDouble();
                System.out.println("Seat price updated to "+flight.seatPrice+" successfully.");
                break;
            case "CATERING AVAILABILITY":
                System.out.println("Current catering availability: "+flight.cateringAvailable);
                System.out.print("New catering availability: ");
                flight.cateringAvailable = sc.nextBoolean();
                System.out.println("Catering availability updated to "+flight.cateringAvailable+" successfully!");
                break;
            case "STATUS":
                System.out.println("Current status: "+flight.status);
                System.out.print("New status: ");
                flight.status = sc.nextLine();
                System.out.println("Status successfully updated to "+flight.status+" successfully!");
                break;
            default:
                sc.close();
                throw new InvalidChoiceException("Invalid choice. Are you sure you spelled it correctly?");
        }
        sc.close();
    }
}

enum FlightType {
    DOMESTIC, INTERNATIONAL
}

class FlightSchedule {
    Flight[] flightList;
    private int flightCount=0;
    private final static int maxSize = 100;
    public FlightSchedule() {
        flightList = new Flight[maxSize];
    }

    public void addFlight(Flight f) throws ExceededMaxSizeException {
        if (flightCount>=maxSize-1) {
            throw new ExceededMaxSizeException("Too many flights. Use different schedule object");
        } else {
            flightList[flightCount++]=f;
            System.out.println("Successfully added flight "+f.flightId+" to the schedule.");
        }
    }

}

class Flight {
    String flightId;
    String origin;
    String destination;
    int totalSeats;
    int bookedSeats = 0;
    FlightType type;
    double seatPrice;
    boolean cateringAvailable;
    String status;
}

/*class aircraftDemo {
    public static void main(String[] args) {
        Manager m = new Manager("admin","password");
        m.displayInfo();
        Flight f = new Flight("abc123","manipal","hyderabad",2,1,FlightType.DOMESTIC,1.1f,true,"On Time");
        FlightSchedule sch = new FlightSchedule();
        m.addFlight(sch, f);
        m.addFlight(sch, f);
        m.addFlight(sch, f);
        try {
            m.updateFlight(sch, f);
        } catch (InvalidChoiceException e) {
            System.out.println(e.getMessage());
        }
    }
}*/