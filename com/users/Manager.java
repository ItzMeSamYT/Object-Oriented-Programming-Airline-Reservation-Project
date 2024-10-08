package com.users;
import com.exceptions.*;
import com.flightmanagement.*;
import java.util.Scanner;

@SuppressWarnings("resource")
public class Manager extends User {
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

        System.out.println("Date: ");
        flight.setDate();

        System.out.print("Total Seats: ");
        flight.totalSeats = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Catering Availability (true/false): ");
        flight.cateringAvailable = sc.nextBoolean();
        sc.nextLine(); // Consume newline
        if (flight.cateringAvailable) {
            flight.manageCateringMenu();
        }

        System.out.print("Duty-Free Availability (true/false): ");
        flight.dutyFreeAvailable = sc.nextBoolean();
        sc.nextLine(); // Consume newline
        if (flight.dutyFreeAvailable) {
            flight.manageDutyFree();
        }

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
        System.out.println("Successfully added flight " + flight.flightId + " to the schedule.");
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
                break;
            }
        }

        if (flight == null) {
            throw new InvalidChoiceException("Flight not found.");
        }

        System.out.println("\n1. Flight ID \n2. Flight type \n3. Origin \n4. Destination \n5. Date \n6. Total Seats \n7. Ticket Price \n8. Catering Availability \n9. Duty-Free Availability\n10. Status \n11. Exit");
        System.out.print("What do you want to update: ");
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
                System.out.println("Current Date: "+flight.getDate());
                System.out.println("Set new date: ");
                flight.setDate();
                System.out.println("Date updated scuccessfully.");
                break;
            case 6:
                System.out.println("Current Total Seats: " + flight.totalSeats);
                System.out.print("New Total Seats: ");
                flight.totalSeats = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.println("Total seats updated successfully!");
                break;
            case 7:
                System.out.println("Current Economy Seat Price: " + flight.economySeatPrice);
                System.out.print("New Economy Seat Price: ");
                flight.economySeatPrice = sc.nextDouble();
                sc.nextLine(); //Consume newLine
                System.out.println("Economy seat price updated successfully!");
                break;
            case 8:
                System.out.println("Current Catering Availability: " + flight.cateringAvailable);
                System.out.print("New Catering Availability: ");
                flight.cateringAvailable = sc.nextBoolean();
                sc.nextLine(); // Consume newline
                System.out.println("Catering availability updated successfully!");
                if (flight.cateringAvailable) {
                    flight.manageCateringMenu();
                }
                break;
            case 9:
                System.out.println("Current Duty-Free Availability: "+flight.dutyFreeAvailable);
                System.out.print("New Duty-Free Availability: ");
                flight.dutyFreeAvailable = sc.nextBoolean();
                sc.nextLine();
                if (flight.dutyFreeAvailable) {
                    flight.manageDutyFree();
                }
                break;
            case 10:
                System.out.println("Current Status: " + flight.status);
                System.out.print("New Status: ");
                flight.status = sc.nextLine();
                System.out.println("Status updated successfully!");
                break;
            case 11:
                System.out.println("Exiting...");
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
