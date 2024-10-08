import com.catering.*;
import com.dutyfree.*;
import com.exceptions.*;
import com.flightmanagement.*;
import com.users.*;
import java.util.Scanner;

@SuppressWarnings("resource")
class AirlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightSchedule flightSchedule = new FlightSchedule();
        DutyFreeManagement dutyFreeManagement = new DutyFreeManagement();
        CateringMenuManagement cateringMenuManagement = new CateringMenuManagement();
        FlightReport flightReport = new FlightReport(flightSchedule);

        while (true) {
            System.out.println("1. Traveler Login");
            System.out.println("2. Manager Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Traveler Username: ");
                    String travelerUsername = scanner.nextLine();
                    System.out.print("Enter Traveler Password: ");
                    String travelerPassword = scanner.nextLine();
                    Traveler traveler = new Traveler(travelerUsername, travelerPassword, flightSchedule);
                    try {
                        traveler.t.join();
                    } catch (InterruptedException e) {}
                    break;
                
                case 2:
                    System.out.print("Enter Manager Username: ");
                    String managerUsername = scanner.nextLine();
                    System.out.print("Enter Manager Password: ");
                    String managerPassword = scanner.nextLine();
                    // Simple check for manager credentials (can be expanded to check from a list or database)
                    if (managerUsername.equals("admin") && managerPassword.equals("Admin$123")) {
                        Manager manager = new Manager(managerUsername, managerPassword, flightSchedule,  cateringMenuManagement, dutyFreeManagement, flightReport);
                        manager.displayInfo();
                        System.out.println();
                        manager.menu();
                    } else {
                        System.out.println("Invalid Manager Username or Password. Try again.");
                    }
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
