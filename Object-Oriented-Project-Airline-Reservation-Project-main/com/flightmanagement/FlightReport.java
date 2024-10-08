package com.flightmanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class FlightReport {
    private FlightSchedule flightSchedule;
    private Scanner scanner;

    public FlightReport(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        this.scanner = new Scanner(System.in);
    }

    public void generateReport() {
        // Check if the flight schedule is empty
        if (flightSchedule.isEmpty()) {
            System.out.println("No flights available in the schedule.");
            return; // Exit if no flights are available
        }

        System.out.println("========================================");
        System.out.println("              FLIGHT REPORT            ");
        System.out.println("========================================");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Flight ID", "Flight Type", "Status", "Total Seats", "Available Seats");
        System.out.println("========================================");

        for (Flight flight : flightSchedule.flightList) {
            if (flight != null) {
                System.out.printf("%-15s %-15s %-15s %-15d %-15d\n", 
                    flight.flightId, 
                    flight.type, 
                    flight.status, 
                    flight.totalSeats, 
                    flight.getVacantSeats("Economy") + 
                    flight.getVacantSeats("Business") + 
                    flight.getVacantSeats("First") + 
                    flight.getVacantSeats("Residence"));
            }
        }

        System.out.println("========================================");
    }

    // Method to find flights with complete capacity
    public void reportFlightsWithCompleteCapacity() {
        System.out.print("Enter Origin City: ");
        String source = scanner.nextLine();
        
        System.out.print("Enter Destination City: ");
        String destination = scanner.nextLine();
    
        System.out.println("========================================");
        System.out.println("   FLIGHTS WITH COMPLETE CAPACITY      ");
        System.out.println("========================================");
    
        boolean foundFlight = false;
    
        for (Flight flight : flightSchedule.flightList) {
            if (flight != null && flight.origin.equalsIgnoreCase(source) && flight.destination.equalsIgnoreCase(destination)) {
                if (flight.getVacantSeats("Economy") == 0 &&
                    flight.getVacantSeats("Business") == 0 &&
                    flight.getVacantSeats("First") == 0 &&
                    flight.getVacantSeats("Residence") == 0) {
                    System.out.printf("Flight ID: %s, Flight Type: %s\n", flight.flightId, flight.type);
                    foundFlight = true;
                }
            }
        }
    
        if (!foundFlight) {
            System.out.println("No flights found with complete capacity for the specified route.");
        }
    
        System.out.println("========================================");
    }
    
    // Method to find periods of frequent bookings
    public void reportFrequentBookingPeriods(ArrayList<DATE> bookingDates) {
        ArrayList<DATE> uniqueDates = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (DATE date : bookingDates) {
            boolean exists = false;

            // Check if the date is already in uniqueDates
            for (int i = 0; i < uniqueDates.size(); i++) {
                DATE uniqueDate = uniqueDates.get(i);
                if (uniqueDate.day == date.day && uniqueDate.month == date.month && uniqueDate.year == date.year) {
                    counts.set(i, counts.get(i) + 1); // Increment count
                    exists = true;
                    break;
                }
            }

            // If the date is not found, add it
            if (!exists) {
                uniqueDates.add(date); // Add unique date
                counts.add(1); // Initialize count to 1
            }
        }

        // Print results
        System.out.println("========================================");
        System.out.println("   FREQUENT BOOKING PERIODS            ");
        System.out.println("========================================");
        for (int i = 0; i < uniqueDates.size(); i++) {
            System.out.printf("Date: %s, Bookings: %d\n", uniqueDates.get(i), counts.get(i));
        }
        System.out.println("========================================");
    }
}
