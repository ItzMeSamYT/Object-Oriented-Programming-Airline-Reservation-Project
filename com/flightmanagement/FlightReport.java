package com.flightmanagement;

import java.util.Scanner;
import com.exceptions.InvalidChoiceException;

public class FlightReport {
    private FlightSchedule flightSchedule;
    private Scanner scanner;

    public FlightReport(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        this.scanner = new Scanner(System.in);
    }

    public void generateReport() {
        if (flightSchedule.flightCount==0) {
            System.out.println("No flights available in the schedule.");
            return;
        }

        System.out.println("==========================================================================================");
        System.out.println("                                      FLIGHT REPORT                                       ");
        System.out.println("==========================================================================================");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Flight ID", "Flight Type", "Status", "Total Seats", "Available Seats");
        System.out.println("==========================================================================================");

        for (Flight flight : flightSchedule.flightList) {
            if (flight != null) {
                System.out.printf("%-15s %-15s %-15s %-15d %-15d\n", 
                    flight.flightId, 
                    flight.type, 
                    flight.status, 
                    flight.totalSeats, 
                    flight.getVacantSeats());
            }
        }

        System.out.println("==========================================================================================");
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
    
    private void addDate(UNIQUEDATE[] dateList, DATE date) {
        for (int i = 0; i < dateList.length; i++) {
            if (dateList[i] == null) {
                dateList[i] = new UNIQUEDATE(date);
                break;
            }
        }
    }

    // Method to find periods of frequent bookings
    public void reportFrequentBookingPeriods() {
        DATE[] bookingDates = new DATE[flightSchedule.flightCount];
        int i;

        for (i=0; i<flightSchedule.flightCount; i++) {
            bookingDates[i] = flightSchedule.flightList[i].date;
        }

        UNIQUEDATE[] uniqueDates = new UNIQUEDATE[i+1];
        for (DATE d1 : bookingDates) {
            boolean exists = false;
            for (int j=0; j<uniqueDates.length; j++) {
                if  (uniqueDates[j] !=null && d1.equals(uniqueDates[j].date)) {
                    uniqueDates[j].count++;
                    exists = true;
                }
            }

            if (!exists) {
                addDate(uniqueDates, d1);
            }
        }

        System.out.println("========================================");
        System.out.println("   FREQUENT BOOKING PERIODS            ");
        System.out.println("========================================");
        for (int j=0; j<uniqueDates.length; j++) {
            if (uniqueDates[j]!=null) {
                System.out.printf("Date: %s, Bookings: %d\n", uniqueDates[j].date, uniqueDates[j].count);
            }
        }
        System.out.println("========================================");
    }

    public void menu() throws InvalidChoiceException {
        boolean flightreport = true;
        while (flightreport) {
            System.out.println("\n1. Flight Report");
            System.out.println("2. Full Capacity Flights");
            System.out.println("3. Frequent Booking Periods");
            System.out.println("4. Frequent Destinations");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int flightReportChoice = scanner.nextInt();
            System.out.println();
            switch (flightReportChoice) {
                case 1:
                    generateReport();
                    break;
                case 2:
                    reportFlightsWithCompleteCapacity();
                    break;
                case 3:
                    reportFrequentBookingPeriods();
                    break;
                case 4:
                    //reportMostFrequentedDestinations();
                    break;
                case 5:
                    flightreport = false;
                    break;
                default:
                    throw new InvalidChoiceException("Invalid Menu Choice");
            }
        }
    }
}
