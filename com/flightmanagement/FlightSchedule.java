package com.flightmanagement;

public class FlightSchedule {
    public int maxSize = 100; // Moved maxSize inside the class
    public Flight[] flightList;
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