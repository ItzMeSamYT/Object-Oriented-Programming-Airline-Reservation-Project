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
            case "Flight ID": 
                System.out.println("Current Flight ID: "+flight.flightId);
                System.out.print("New Flight ID: ");
                flight.flightId = sc.nextLine();
                break;
            case "Origin":
                System.out.println("Current Origin: "+flight.origin);
                System.out.print("New Origin: ");
                flight.origin = sc.nextLine();
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