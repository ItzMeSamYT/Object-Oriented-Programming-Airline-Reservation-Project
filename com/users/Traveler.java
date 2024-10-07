package com.users;
import java.util.Scanner;
import com.flightmanagement.*;
import com.exceptions.InvalidChoiceException;


public class Traveler extends User implements Runnable {
    Thread t;
    boolean travelerMenu = true;
    Scanner scanner = new Scanner(System.in);

    String email;
    String contactNo;

    FlightSchedule mainFlightSchedule;
    FlightSchedule filtered;

    public Traveler(String loginID, String password, FlightSchedule flightSchedule) {
        super(loginID, password);
        mainFlightSchedule = flightSchedule;
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome " + loginID + "!\n");
    }

    public void inputDetails() {
        System.out.print("Enter Email ID: ");
        email = scanner.nextLine();
        System.out.print("Enter contact number: ");
        contactNo = scanner.nextLine();
    }

    public void bookFlight() throws InvalidChoiceException {
        System.out.println("FLIGHT BOOKING");
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        System.out.println("Enter destination: ");
        String destination = scanner.nextLine();
        for (Flight f : mainFlightSchedule.flightList) {
            if (f != null && f.origin.equals(origin) && f.destination.equals(destination)) {
                filtered.addFlight(f);
            }
        }

        if (filtered.flightList[0]==null) {
            throw new InvalidChoiceException("No flights found.");
        }

        System.out.println("Available flights:\n");
        filtered.viewFlights();

        System.out.print("Select flight (Flight ID): ");
        String flightChoice = scanner.nextLine();
        boolean exists = false;
        int index;
        for (index=0; index<mainFlightSchedule.flightList.length; index++) {
            Flight f = mainFlightSchedule.flightList[index];
            if (f != null && f.flightId.equals(flightChoice)) {
                exists=true;
                break;
            }
        }

        if (!exists) {
            throw new InvalidChoiceException("Flight does not exist.");
        }

        Flight selected = filtered.flightList[index];
        System.out.println("Enter travel class (Economy/Business/First/Residence): ");
        String classChoice = scanner.nextLine();
        if (!classChoice.equals("Economy") && !classChoice.equals("Business") && !classChoice.equals("First") && !classChoice.equals("Residence")) {
            throw new InvalidChoiceException("Invalid choice of traveling class.");
        }
        System.out.println("Price of each seat: "+selected.displayPrice(classChoice));

        System.out.print("Enter number of seats to book: ");
        int noOfSeats = scanner.nextInt();
        scanner.nextLine();
    }

    

    @Override
    public void run() {
        while (travelerMenu) {
            System.out.println("\n1. Input Personal Details");
            System.out.println("2. View Flight Schedule");
            System.out.println("3. Book a Flight");
            //System.out.println("4. View Catering Menu");
            //System.out.println("5. View and Purchase Duty-Free Items");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int travelerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (travelerChoice) {
                case 1:
                    inputDetails();
                    break;
                case 2:
                    mainFlightSchedule.viewFlights();
                    break;
                case 3:
                    try {
                        bookFlight();
                    } catch (InvalidChoiceException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                /*case 4:
                    viewCateringMenu();
                    break;
                case 5:
                    viewAndPurchaseDutyFree();
                    break;*/
                case 4:
                    travelerMenu = false;
                    break;
                default:
                    try {
                        throw new InvalidChoiceException("Invalid option. Please choose again.");
                    } catch (InvalidChoiceException e) {
                        System.out.println(e.getMessage());
                    }
            }
        }
    }
}