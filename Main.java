import java.util.Scanner;
import com.users.*;
import com.catering.*;
import com.dutyfree.*;
import com.flightmanagement.*;
import com.exceptions.*;

@SuppressWarnings("resource")
class AirlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightSchedule flightSchedule = new FlightSchedule();
        DutyFreeManagement dutyFreeManagement = new DutyFreeManagement();
        CateringMenuManagement cateringMenuManagement = new CateringMenuManagement();

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
                                    try {
                                        cateringMenuManagement.manageCatering();
                                    } catch (InvalidChoiceException e) {
                                        System.out.println(e.getMessage());
                                    }
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
                    @SuppressWarnings("unused")
                    Traveler traveler = new Traveler(travelerUsername, travelerPassword, flightSchedule);
                    try {
                        traveler.t.join();
                    } catch (InterruptedException e) {}
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