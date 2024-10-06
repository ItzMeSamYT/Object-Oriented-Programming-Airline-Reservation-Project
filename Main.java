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

@SuppressWarnings("resource")
class Manager extends User {
    public Manager(String loginID, String password) {
        super(loginID, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome, Manager " + loginID + "!");
        System.out.println("You have administrator permissions.");
    }

    // Add flight method
    public void addFlight(FlightSchedule flightSchedule) throws ExceededMaxSizeException {
        if (flightSchedule.flightList.length==flightSchedule.maxSize-1) {
            throw new ExceededMaxSizeException("Too many flights for system. Please create new schedule object to continue adding flights.");
        }
        Scanner sc = new Scanner(System.in);
        Flight flight = new Flight();

        System.out.print("Flight ID: ");
        flight.flightId = sc.nextLine();

        System.out.print("Flight type (DOMESTIC/INTERNATIONAL): ");
        String strFlightType = sc.nextLine();
        flight.type = FlightType.valueOf(strFlightType);

        System.out.print("Origin: ");
        flight.origin = sc.nextLine();

        System.out.print("Destination: ");
        flight.destination = sc.nextLine();

        System.out.print("Total Seats: ");
        flight.totalSeats = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Catering Availability (true/false): ");
        flight.cateringAvailable = sc.nextBoolean();
        sc.nextLine(); // Consume newline

        System.out.print("Duty-Free Availability (true/false): ");
        flight.dutyFreeAvailable = sc.nextBoolean();
        sc.nextLine(); // Consume newline

        System.out.print("Status: ");
        flight.status = sc.nextLine();

        System.out.print("Number of Economy Seats: ");
        flight.economySeats = sc.nextInt();
        sc.nextLine(); // Consumer newline

        System.out.print("Seat Price for Economy: ");
        flight.economySeatPrice = sc.nextDouble();
        sc.nextLine(); // Consume newline

        System.out.print("Number of Business Seats: ");
        flight.businessSeats = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Seat Price for Business: ");
        flight.businessSeatPrice = sc.nextDouble();
        sc.nextLine(); //Consume newline

        System.out.print("Number of First Seats: ");
        flight.firstSeats = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Seat Price for First: ");
        flight.firstSeatPrice = sc.nextDouble();
        sc.nextLine(); // Consume newline

        // Setting Residence seat to 1 by default
        flight.residenceSeats = 1;

        // Get price for Residence class ticket
        System.out.print("Seat Price for Residence: ");
        flight.residenceSeatPrice = sc.nextDouble();
        sc.nextLine(); // Consume newline

        // Add the flight to the schedule
        flightSchedule.addFlight(flight);
        
    }

    // Update flight method
    public void updateFlight(FlightSchedule sch) throws InvalidChoiceException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Flight ID of flight to update: ");
        String identifier = sc.nextLine();
        Flight flight = null;
        for (Flight fl : sch.flightList) {
            if (identifier.equals(fl.flightId)) {
                flight = fl;
            }
        }

        if (flight == null) {
            throw new InvalidChoiceException("Flight not found.");
        }

        System.out.println("\n1. Flight ID \n2. Flight type \n3. Origin \n4. Destination \n5. Total Seats \n6. Ticket Price \n7. Catering Availability \n8. Status");
        System.out.print("What do you want to update: ");
        sc.nextLine();
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Current Flight ID: " + flight.flightId);
                System.out.print("New Flight ID: ");
                flight.flightId = sc.nextLine();
                System.out.println("Flight ID updated successfully!");
                break;
            case 2:
                System.out.println("Current flight type: "+flight.type);
                System.out.print("New flight type: ");
                String strFlightType = sc.nextLine();
                flight.type = FlightType.valueOf(strFlightType);
                System.out.println("Flight type updated successfully!");
                break;
            case 3:
                System.out.println("Current Origin: " + flight.origin);
                System.out.print("New Origin: ");
                flight.origin = sc.nextLine();
                System.out.println("Origin updated successfully!");
                break;
            case 4:
                System.out.println("Current Destination: " + flight.destination);
                System.out.print("New Destination: ");
                flight.destination = sc.nextLine();
                System.out.println("Destination updated successfully!");
                break;
            case 5:
                System.out.println("Current Total Seats: " + flight.totalSeats);
                System.out.print("New Total Seats: ");
                flight.totalSeats = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.println("Total seats updated successfully!");
                break;
            case 6:
                System.out.println("Current Economy Seat Price: " + flight.economySeatPrice);
                System.out.print("New Economy Seat Price: ");
                flight.economySeatPrice = sc.nextDouble();
                sc.nextLine(); //Consume newLine
                System.out.println("Economy seat price updated successfully!");
                break;
            case 7:
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
            case 8:
                System.out.println("Current Status: " + flight.status);
                System.out.print("New Status: ");
                flight.status = sc.nextLine();
                System.out.println("Status updated successfully!");
                break;
            default:
                
                throw new InvalidChoiceException("Invalid choice!");
        }
        
    }

    // Method to delete flight
    public void deleteFlight(FlightSchedule flightSchedule) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Flight ID to cancel: ");
        String flightId = sc.nextLine();
        
        for (Flight flight : flightSchedule.flightList) {
            if (flight != null && flight.flightId.equals(flightId)) {
                flight.status = "Canceled"; // Set status to Canceled
                System.out.println("Flight " + flightId + " has been canceled.");              
                return;
            }
        }
        System.out.println("Flight not found.");
        
    }
}

// Flight Type Enum
enum FlightType {
    DOMESTIC, INTERNATIONAL
}

// Flight class
@SuppressWarnings("resource")
class Flight {
    String flightId;
    String origin;
    String destination;
    FlightType type;
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

    private void printCateringMenu(String classType) {
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Dish Name", "Type", "Allergens", "Class");
        System.out.println("--------------------------------------------------");
        for (CateringMenuItem item : cateringMenu) {
            if (item.classType.equals(classType)) {
                System.out.println(item+"\n");
            }
        }
    }

    public void displayCateringMenu(FlightSchedule flightSchedule) throws InvalidChoiceException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Flight ID to view catering menu: ");
        String flightId = sc.nextLine();

        Flight flightToViewMenu = null;
        for (Flight flight : flightSchedule.flightList) {
            if (flight != null && flight.flightId.equals(flightId)) {
                flightToViewMenu = flight;
                break;
            }
        }

        if (flightToViewMenu != null) {
            System.out.println("Select Class Type:");
            System.out.println("1. Economy");
            System.out.println("2. Business");
            System.out.println("3. First");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            String classType = "";
            switch (choice) {
                case 1:
                    classType = "Economy";
                    break;
                case 2:
                    classType = "Business";
                    break;
                case 3:
                    classType = "First";
                    break;
                default:
                    
                    throw new InvalidChoiceException("Invalid class choice. No menu will be displayed.");
            }

            // Print the catering menu for the selected class
            System.out.println("\nCatering Menu for " + classType + ":");
            flightToViewMenu.printCateringMenu(classType);
        } else {
            System.out.println("Flight not found.");
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

    @Override
    public String toString() {
        return "Dish name: "+name+"\n Type: "+type+"\nAlergens: "+allergens;
    }
}

class FlightSchedule {
    public int maxSize = 100; // Moved maxSize inside the class
    Flight[] flightList;
    private int flightCount = 0;

    public FlightSchedule() {
        flightList = new Flight[maxSize];
    }

    public void addFlight(Flight f) {
        flightList[flightCount++] = f;
        System.out.println("Successfully added flight " + f.flightId + " to the schedule.");
    }

    public void viewFlights() {
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
                        "Flight ID", "Origin", "Destination", "Total Seats", "Status", 
                        "Economy Price", "Business Price", "First Price", "Residence Price");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Flight flight : flightList) {
            if (flight != null) {
                System.out.printf("%-15s %-15s %-15s %-15d %-15s %-15.2f %-15.2f %-15.2f %-15.2f\n", 
                                flight.flightId, flight.origin, flight.destination, 
                                flight.totalSeats, flight.status, 
                                flight.economySeatPrice, flight.businessSeatPrice, 
                                flight.firstSeatPrice, flight.residenceSeatPrice);
            }
        }
    }

}

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

@SuppressWarnings("resource")
class AirlineReservationSystem {
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
                        System.out.println();

                        boolean managerMenu = true;
                        while (managerMenu) {
                            System.out.println("\n1. Manage Flights");
                            System.out.println("2. Manage Duty-Free");
                            System.out.println("3. Manage Catering");
                            System.out.println("4. Logout");
                            System.out.print("Choose an option: ");
                            int managerChoice = scanner.nextInt();
                            System.out.println();

                            switch (managerChoice) {
                                case 1:
                                    boolean flightManagement = true;
                                    while (flightManagement) {
                                        System.out.println("\n1. Add Flight");
                                        System.out.println("2. Update Flight");
                                        System.out.println("3. View Flights");
                                        System.out.println("4. Delete Flight");
                                        System.out.println("5. Back to Manager Menu");
                                        System.out.print("Choose an option: ");
                                        int flightManagementChoice = scanner.nextInt();
                                        System.out.println();

                                        switch (flightManagementChoice) {
                                            case 1:
                                                try {
                                                    manager.addFlight(flightSchedule);
                                                } catch (ExceededMaxSizeException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                                break;
                                            case 2:
                                                try {
                                                    manager.updateFlight(flightSchedule);
                                                } catch (InvalidChoiceException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                                break;
                                            case 3:
                                                flightSchedule.viewFlights();
                                                break;
                                            case 4:
                                                manager.deleteFlight(flightSchedule);
                                                break;
                                            case 5:
                                                flightManagement = false;
                                                break;
                                            default:
                                                try {
                                                    throw new InvalidChoiceException("Invalid choice. Please try again.");
                                                } catch (InvalidChoiceException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                        }
                                    }
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
                                    try {
                                        throw new InvalidChoiceException("Invalid choice. Please try again.");
                                    } catch (InvalidChoiceException e) {
                                        System.out.println(e.getMessage());
                                    }
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
                    Traveler traveler = new Traveler(travelerUsername, travelerPassword);
                    traveler.displayInfo();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    try {
                        throw new InvalidChoiceException("Invalid Menu Option. Please choose again.");
                    } catch (InvalidChoiceException e) {
                        System.out.println(e.getMessage());
                    }
            }
        }
    }
}