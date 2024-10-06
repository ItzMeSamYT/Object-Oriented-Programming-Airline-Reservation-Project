import java.util.Scanner;

// Custom exceptions
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

// User classes
abstract class User {
    String loginID, password;

    public User(String loginID, String password) {
        this.loginID = loginID;
        this.password = password;
    }

    abstract void displayInfo();
}

class Traveler extends User {
    public Traveler(String loginID, String password) {
        super(loginID, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome " + loginID + "!\n");
    }
}

class Manager extends User {
    public Manager(String loginID, String password) {
        super(loginID, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome, Manager " + loginID + "!");
        System.out.println("You have administrator permissions.");
    }

    public void addFlight(FlightSchedule flightSchedule) {
        Scanner sc = new Scanner(System.in);
        Flight flight = new Flight();

        System.out.print("Flight ID: ");
        flight.flightId = sc.nextLine();

        System.out.print("Origin: ");
        flight.origin = sc.nextLine();

        System.out.print("Destination: ");
        flight.destination = sc.nextLine();

        System.out.print("Total Seats: ");
        flight.totalSeats = sc.nextInt();

        System.out.print("\n");
        System.out.print("Catering Availability (true/false): ");
        flight.cateringAvailable = sc.nextBoolean();
        System.out.print("Duty-Free Availability (true/false): ");
        flight.dutyFreeAvailable = sc.nextBoolean();
        sc.nextLine();

        System.out.print("Status: ");
        flight.status = sc.nextLine();

        System.out.print("\n");
        System.out.print("Number of Economy Seats: ");
        flight.economySeats = sc.nextInt();
        System.out.print("Seat Price for Economy: ");
        flight.economySeatPrice = sc.nextDouble();

        System.out.print("\n");
        System.out.print("Number of Business Seats: ");
        flight.businessSeats = sc.nextInt();
        System.out.print("Seat Price for Business: ");
        flight.businessSeatPrice = sc.nextDouble();

        System.out.print("\n");
        System.out.print("Number of First Seats: ");
        flight.firstSeats = sc.nextInt();
        System.out.print("Seat Price for First: ");
        flight.firstSeatPrice = sc.nextDouble();

        // Setting Residence seat to 1 by default
        flight.residenceSeats = 1;

        // Get price for Residence class ticket
        System.out.print("\n");
        System.out.print("Seat Price for Residence: ");
        flight.residenceSeatPrice = sc.nextDouble();

        // Add the flight to the schedule
        try {
            flightSchedule.addFlight(flight);
        } catch (ExceededMaxSizeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateFlight(FlightSchedule sch, Flight flight) throws InvalidChoiceException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n 1. Origin \n 2. Destination\n 3. Total Seats \n 4. Ticket Price \n 5. Catering Availability \n 6. Status");
        System.out.print("What do you want to update: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Current Origin: " + flight.origin);
                System.out.print("New Origin: ");
                flight.origin = sc.nextLine();
                System.out.println("Origin updated successfully!");
                break;
            case 2:
                System.out.println("Current Destination: " + flight.destination);
                System.out.print("New Destination: ");
                flight.destination = sc.nextLine();
                System.out.println("Destination updated successfully!");
                break;
            case 3:
                System.out.println("Current Total Seats: " + flight.totalSeats);
                System.out.print("New Total Seats: ");
                flight.totalSeats = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.println("Total seats updated successfully!");
                break;
            case 4:
                System.out.println("Current Economy Seat Price: " + flight.economySeatPrice);
                System.out.print("New Economy Seat Price: ");
                flight.economySeatPrice = sc.nextDouble();
                System.out.println("Economy seat price updated successfully!");
                break;
            case 5:
                System.out.println("Current Catering Availability: " + flight.cateringAvailable);
                System.out.print("New Catering Availability: ");
                flight.cateringAvailable = sc.nextBoolean();
                sc.nextLine(); // Consume newline
                System.out.println("Catering availability updated successfully!");
                if (flight.cateringAvailable) {
                    flight.addCateringMenu("Economy");
                    flight.addCateringMenu("Business");
                    flight.addCateringMenu("First");
                }
                break;
            case 6:
                System.out.println("Current Status: " + flight.status);
                System.out.print("New Status: ");
                flight.status = sc.nextLine();
                System.out.println("Status updated successfully!");
                break;
            default:
                throw new InvalidChoiceException("Invalid choice!");
        }
    }
}

// Flight Type Enum
enum FlightType {
    DOMESTIC, INTERNATIONAL
}

// Flight class
class Flight {
    String flightId;
    String origin;
    String destination;
    int totalSeats;
    boolean cateringAvailable;
    boolean dutyFreeAvailable;
    String status;
    int economySeats;
    double economySeatPrice;
    int businessSeats;
    double businessSeatPrice;
    int firstSeats;
    double firstSeatPrice;
    int residenceSeats;
    double residenceSeatPrice; // Add residence seat price

    CateringMenuItem[] cateringMenu = new CateringMenuItem[10]; // Assuming a max of 10 menu items

    public void addCateringMenu(String classType) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Dish Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Dish Type (Starter, Main, Dessert, Beverage): ");
        String type = sc.nextLine();
        System.out.print("Enter Allergens: ");
        String allergens = sc.nextLine();

        for (int i = 0; i < cateringMenu.length; i++) {
            if (cateringMenu[i] == null) {
                cateringMenu[i] = new CateringMenuItem(name, type, allergens, classType);
                System.out.println(name + " added to the catering menu for " + classType + ".");
                break;
            }
        }
    }

    public void printCateringMenu() {
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Dish Name", "Type", "Allergens", "Class");
        System.out.println("--------------------------------------------------");
        for (CateringMenuItem item : cateringMenu) {
            if (item != null) {
                System.out.printf("%-15s %-15s %-15s %-15s\n", item.name, item.type, item.allergens, item.classType);
            }
        }
    }
}

class CateringMenuItem {
    String name;
    String type;
    String allergens;
    String classType;

    public CateringMenuItem(String name, String type, String allergens, String classType) {
        this.name = name;
        this.type = type;
        this.allergens = allergens;
        this.classType = classType;
    }
}

// FlightSchedule class
class FlightSchedule {
    public int maxSize = 100; // Moved maxSize inside the class
    Flight[] flightList;
    private int flightCount = 0;

    public FlightSchedule() {
        flightList = new Flight[maxSize];
    }

    public void addFlight(Flight f) throws ExceededMaxSizeException {
        if (flightCount >= maxSize) {
            throw new ExceededMaxSizeException("Too many flights. Use a different schedule object.");
        } else {
            flightList[flightCount++] = f;
            System.out.println("Successfully added flight " + f.flightId + " to the schedule.");
        }
    }

    public void viewFlights() {
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s  %-15s\n", "Flight ID", "Origin", "Destination", "Total Seats", "Status", "Economy Class Price", "Business Class Price", "First Class Price", "Residence Class Price");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        for (Flight flight : flightList) {
            if (flight != null) {
                System.out.printf("%-15s %-15s %-15s %-15d %-15s %.2f %.2f %.2f %.2f\n", flight.flightId, flight.origin, flight.destination, flight.totalSeats, flight.status, flight.economySeatPrice, flight.businessSeatPrice, flight.firstSeatPrice, flight.residenceySeatPrice);
            }
        }
    }
}

// Duty-Free Management Class
class DutyFreeManagement {
    DutyFreeItem[] dutyFreeItems;
    private int itemCount;
    private final int maxItems;

    public DutyFreeManagement(int maxItems) {
        this.maxItems = maxItems;
        dutyFreeItems = new DutyFreeItem[maxItems];
        itemCount = 0;
    }

    public void manageDutyFree() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nDuty-Free Management:");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            System.out.print("\n");

            switch (choice) {
                case 1:
                    addDutyFreeItem(scanner);
                    break;
                case 2:
                    printDutyFreeItems();
                    break;
                case 3:
                    updateDutyFreeItem(scanner);
                    break;
                case 4:
                    deleteDutyFreeItem(scanner);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addDutyFreeItem(Scanner sc) {
        if (itemCount >= maxItems) {
            System.out.println("Cannot add more items. Duty-free menu is full.");
            return;
        }

        System.out.print("Enter Item Name: ");
        String name = sc.next();

        System.out.print("Enter Item Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Item Section (Beverages, Toys, Chocolates, Perfumes, Watches, Jewellery, Beauty Products): ");
        String section = sc.next();

        dutyFreeItems[itemCount++] = new DutyFreeItem(name, price, section);
        System.out.println(name + " added to the duty-free menu.");
    }

    public void printDutyFreeItems() {
        System.out.printf("%-15s %-15s %-15s\n", "Item Name", "Price", "Section");
        System.out.println("--------------------------------------------------");
        for (DutyFreeItem item : dutyFreeItems) {
            if (item != null) {
                System.out.printf("%-15s $%-14.2f %-15s\n", item.name, item.price, item.section);
            }
        }
    }

    public void updateDutyFreeItem(Scanner sc) {
        System.out.print("Enter the index of the item to update (0-" + (itemCount - 1) + "): ");
        int index = sc.nextInt();
        if (index < 0 || index >= itemCount) {
            System.out.println("Invalid index.");
            return;
        }

        System.out.print("New Item Name (leave blank to keep current): ");
        String newName = sc.next();
        if (!newName.isEmpty()) {
            dutyFreeItems[index].name = newName;
        }

        System.out.print("New Item Price (leave blank to keep current): ");
        String newPrice = sc.next();
        if (!newPrice.isEmpty()) {
            dutyFreeItems[index].price = Double.parseDouble(newPrice);
        }

        System.out.print("New Item Section (leave blank to keep current): ");
        String newSection = sc.next();
        if (!newSection.isEmpty()) {
            dutyFreeItems[index].section = newSection;
        }

        System.out.println("Duty-free item updated successfully!");
    }

    public void deleteDutyFreeItem(Scanner sc) {
        System.out.print("Enter the index of the item to delete (0-" + (itemCount - 1) + "): ");
        int index = sc.nextInt();
        if (index < 0 || index >= itemCount) {
            System.out.println("Invalid index.");
            return;
        }

        // Shift items left
        for (int i = index; i < itemCount - 1; i++) {
            dutyFreeItems[i] = dutyFreeItems[i + 1];
        }
        dutyFreeItems[--itemCount] = null; // Decrease count and nullify last item
        System.out.println("Duty-free item deleted successfully!");
    }
}

class DutyFreeItem {
    String name;
    double price;
    String section;

    public DutyFreeItem(String name, double price, String section) {
        this.name = name;
        this.price = price;
        this.section = section;
    }
}

// Catering Menu Management Class
class CateringMenuManagement {
    FlightSchedule flightSchedule;
    CateringMenuItem[] cateringMenu;
    private int menuCount;
    private final int maxMenuItems = 100; // Set the max size for catering items

    public CateringMenuManagement(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        cateringMenu = new CateringMenuItem[maxMenuItems];
        menuCount = 0;
    }

    public void manageCatering() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nCatering Menu Management:");
            System.out.println("1. Add Catering Item");
            System.out.println("2. View Catering Menu");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            System.out.print("\n");

            switch (choice) {
                case 1:
                    addCateringMenu(scanner);
                    break;
                case 2:
                    printCateringMenu();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addCateringMenu(Scanner sc) {
        if (menuCount >= maxMenuItems) {
            System.out.println("Cannot add more items. Catering menu is full.");
            return;
        }

        System.out.print("Enter Class Type (Economy, Business, First): ");
        String classType = sc.next(); // Asking for class type first
        sc.nextLine(); // Consume newline

        System.out.print("Enter Dish Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Dish Type (Starter, Main, Dessert, Beverage): ");
        String type = sc.nextLine();
        System.out.print("Enter Allergens: ");
        String allergens = sc.nextLine();

        cateringMenu[menuCount++] = new CateringMenuItem(name, type, allergens, classType);
        System.out.println(name + " added to the catering menu for " + classType + ".");
    }

    public void printCateringMenu() {
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Dish Name", "Type", "Allergens", "Class");
        System.out.println("--------------------------------------------------");
        for (CateringMenuItem item : cateringMenu) {
            if (item != null) {
                System.out.printf("%-15s %-15s %-15s %-15s\n", item.name, item.type, item.allergens, item.classType);
            }
        }
    }
}

class CateringMenuItem {
    String name;
    String type;
    String allergens;
    String classType;

    public CateringMenuItem(String name, String type, String allergens, String classType) {
        this.name = name;
        this.type = type;
        this.allergens = allergens;
        this.classType = classType;
    }
}

// Main Class
public class AirlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightSchedule flightSchedule = new FlightSchedule();
        DutyFreeManagement dutyFreeManagement = new DutyFreeManagement(100);
        CateringMenuManagement cateringMenuManagement = new CateringMenuManagement(flightSchedule);

        while (true) {
            System.out.println("1. Manager Login");
            System.out.println("2. Traveler Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Manager Username: ");
                    String managerUsername = scanner.nextLine();
                    System.out.print("Enter Manager Password: ");
                    String managerPassword = scanner.nextLine();

                    // Simple check for manager credentials (can be expanded to check from a list or database)
                    if (managerUsername.equals("admin") && managerPassword.equals("admin123")) {
                        Manager manager = new Manager(managerUsername, managerPassword);
                        manager.displayInfo();
                        System.out.print("\n");

                        boolean managerMenu = true;
                        while (managerMenu) {
                            System.out.println("\n1. Manage Flights");
                            System.out.println("2. Manage Duty-Free");
                            System.out.println("3. Manage Catering");
                            System.out.println("4. Logout");
                            System.out.print("Choose an option: ");
                            int managerChoice = scanner.nextInt();
                            System.out.print("\n");

                            switch (managerChoice) {
                                case 1:
                                    manager.addFlight(flightSchedule);
                                    flightSchedule.viewFlights();
                                    break;
                                case 2:
                                    dutyFreeManagement.manageDutyFree();
                                    break;
                                case 3:
                                    cateringMenuManagement.manageCatering();
                                    break;
                                case 4:
                                    managerMenu = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid Manager Username or Password. Try again.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Traveler Username: ");
                    String travelerUsername = scanner.nextLine();
                    System.out.print("Enter Traveler Password: ");
                    String travelerPassword = scanner.nextLine();

                    // Simple check for traveler credentials
                    if (travelerUsername.equals("traveler") && travelerPassword.equals("traveler123")) {
                        Traveler traveler = new Traveler(travelerUsername, travelerPassword);
                        traveler.displayInfo();
                        // Add traveler functionalities if needed
                    } else {
                        System.out.println("Invalid Traveler Username or Password. Try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
