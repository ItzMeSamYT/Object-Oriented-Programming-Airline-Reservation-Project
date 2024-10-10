package com.flightmanagement;

public class FlightSchedule {
    public int maxSize = 100;
    public Flight[] flightList;
    public int flightCount = 0;

    public FlightSchedule() {
        flightList = new Flight[maxSize];
    }

    public void addFlight(Flight f) {
        boolean replaced = false;
        for (int i=0; i<flightCount; i++) {
            if (flightList[i] != null && flightList[i].status.equals("Cancelled")) {
                flightList[i] = f;
                replaced = true;
                return;
            }
        }

        if (!replaced) {
            flightList[flightCount++] = f;
        }
    }

    public void viewFlights() {
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
                        "Flight ID", "Origin", "Destination", "Date", "Total Seats", "Status", 
                        "Economy Price", "Business Price", "First Price", "Residence Price");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Flight flight : flightList) {
            if (flight != null) {
                System.out.printf("%-15s %-15s %-15s %-15s %-15d %-15s %-15.2f %-15.2f %-15.2f %-15.2f\n", 
                                flight.flightId, flight.origin, flight.destination, flight.date,
                                flight.totalSeats, flight.status, 
                                flight.economySeatPrice, flight.businessSeatPrice, 
                                flight.firstSeatPrice, flight.residenceSeatPrice);
            }
        }
    }

}