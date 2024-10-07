package com.flightmanagement;
import com.catering.*;
import com.exceptions.InvalidChoiceException;

public class Flight {
    public String flightId;
    public String origin;
    public String destination;
    public FlightType type;
    public int totalSeats;
    public boolean cateringAvailable;
    public boolean dutyFreeAvailable;
    public String status;
    public int economySeats;
    public int vacantEconomySeats;
    public double economySeatPrice;
    public int businessSeats;
    public int vacantBusinessSeats;
    public double businessSeatPrice;
    public int firstSeats;
    public int vacantFirstSeats;
    public double firstSeatPrice;
    public int residenceSeats;
    public int vacantResidenceSeats;
    public double residenceSeatPrice;
    CateringMenuManagement cateringMenu = new CateringMenuManagement();

    public void manageCateringMenu() {
        try {
            cateringMenu.manageCatering();
        } catch (InvalidChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public String displayPrice(String classType) {
        switch(classType) {
            case "Economy":
                return "$"+economySeatPrice;
            case "Business":
                return "$"+businessSeatPrice;
            case "First":
                return "$"+firstSeatPrice;
            case "Residence":
                return "$"+residenceSeatPrice;
            default:
                return "";
        }
    }
}