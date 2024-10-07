package com.users;
import java.util.Scanner;
import com.flightmanagement.*;
import com.exceptions.InvalidChoiceException;


public class Traveler extends User implements Runnable {
    public Thread t;
    boolean travelerMenu = true;
    Scanner scanner = new Scanner(System.in);
    int noOfSeats;
    String classChoice;
    String email;
    String contactNo;

    FlightSchedule mainFlightSchedule;
    FlightSchedule filtered = new FlightSchedule();

    public Traveler(String loginID, String password, FlightSchedule flightSchedule) {
        super(loginID, password);
        mainFlightSchedule = flightSchedule;
        t = new Thread(this, loginID);
        t.start();
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

    synchronized public void bookFlight() throws InvalidChoiceException {
        System.out.println("FLIGHT BOOKING");
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination: ");
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
        classChoice = scanner.nextLine();
        if (!classChoice.equals("Economy") && !classChoice.equals("Business") && !classChoice.equals("First") && !classChoice.equals("Residence")) {
            throw new InvalidChoiceException("Invalid choice of traveling class.");
        }
        System.out.println("Price of each seat: "+selected.displayPrice(classChoice));

        System.out.print("Enter number of seats to book: ");
        noOfSeats = scanner.nextInt();
        scanner.nextLine();
        switch(classChoice) {
            case "Economy":
                if (noOfSeats<selected.vacantEconomySeats) {
                    System.out.println("Not enough vacant seats on flight.");
                } else {
                    selected.vacantEconomySeats-=noOfSeats;
                    System.out.println("Successfully booked "+noOfSeats+" economy class seats!");
                }
                break;
            case "Business":
                if (noOfSeats<selected.vacantBusinessSeats) {
                    System.out.println("Not enough vacant seats on flight.");
                } else {
                    selected.vacantBusinessSeats-=noOfSeats;
                    System.out.println("Successfulyl booked "+noOfSeats+" business class seats!");
                }
                break;
            case "First":
                if (noOfSeats<selected.vacantFirstSeats) {
                    System.out.println("Not enough vacant seats on flight.");
                } else {
                    selected.vacantFirstSeats-=noOfSeats;
                    System.out.println("Successfully booked "+noOfSeats+" seats!");
                }
                break;
            case "Residence":
                if (noOfSeats<selected.vacantResidenceSeats) {
                    System.out.println("Not enough vacant seats on flight.");
                } else {
                    selected.vacantResidenceSeats-=noOfSeats;
                    System.out.println("Successfully booked "+noOfSeats+" seats!");
                }
                break;
        }

        if (selected.cateringAvailable) {
            System.out.println("\nCatering is available on your flight!");
            System.out.println("The catering menu is as follows, please ask our staff to purchase any item(s)!");
            selected.printCateringMenu();
        }

        if (selected.dutyFreeAvailable) {
            System.out.println("\nDuty-Free items are available for your flight!");
            System.out.println("Duty-Free items are as follows, please ask our staff to purchase any item(s)!");
            selected.printDutyFreeItems();
        }
    }

    

    @Override
    public void run() {
        displayInfo();
        while (travelerMenu) {
            System.out.println("\n1. Input Personal Details");
            System.out.println("2. View Flight Schedule");
            System.out.println("3. Book a Flight");
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