package com.flightmanagement;
import java.util.Scanner;
import com.catering.*;
import com.dutyfree.DutyFreeManagement;
import com.exceptions.InvalidChoiceException;

public class Flight {
    public String flightId;
    public String origin;
    public String destination;
    public DATE date = new DATE();
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
    DutyFreeManagement DutyFree = new DutyFreeManagement();

    public void manageCateringMenu() {
        try {
            cateringMenu.manageCatering();
        } catch (InvalidChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manageDutyFree() {
        try {
            DutyFree.manageDutyFree();
        } catch (InvalidChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public String displayPrice(String classType) {
        switch(classType) {
            case "Economy":
                return "$" + String.format("%.2f", economySeatPrice);
            case "Business":
                return "$" + String.format("%.2f", businessSeatPrice);
            case "First":
                return "$" + String.format("%.2f", firstSeatPrice);
            case "Residence":
                return "$" + String.format("%.2f", residenceSeatPrice);
            default:
                return "";
        }
    }

    public double getPrice(String classType) {
        switch (classType) {
            case "Economy":
                return economySeatPrice;
            case "Business":
                return businessSeatPrice;
            case "First":
                return firstSeatPrice;
            case "Residence":
                return residenceSeatPrice;
            default:
                return -1;
        }
    }

    public int getVacantSeats(String classType) {
        switch (classType) {
            case "Economy":
                return vacantEconomySeats;
            case "Business":
                return vacantBusinessSeats;
            case "First":
                return vacantFirstSeats;
            case "Residence":
                return vacantResidenceSeats;
            default:
                return -1;
        }
    }

    public int getVacantSeats() {
        return vacantEconomySeats  + vacantBusinessSeats + vacantFirstSeats + vacantResidenceSeats;

    }

    public void updateVacantSeats(String classType, int change) {
        switch (classType) {
            case "Economy":
                vacantEconomySeats += change;
                economySeatPrice = economySeatPrice / (1 + (change / (double)economySeats)*0.1);
                break;
            case "Business":
                vacantBusinessSeats += change;
                businessSeatPrice = businessSeatPrice / (1 + (change / (double)businessSeats)*0.1);
                break;
            case "First":
                vacantFirstSeats += change;
                firstSeatPrice = firstSeatPrice / (1 + (change / (double)firstSeats)*0.1);
                break;
            case "Residence":
                vacantResidenceSeats += change;
                residenceSeatPrice = residenceSeatPrice / (1 + (change / (double)residenceSeats)*0.1);
                break;
        }
    }

    public int getTotalSeats(String classType) {
        switch (classType) {
            case "Economy":
                return economySeats;
            case "Business":
                return businessSeats;
            case "First":
                return firstSeats;
            case "Residence":
                return residenceSeats;
            default:
                return -1;
        }
    }

    public void setDate() {
        @SuppressWarnings("resource")
        Scanner sc =  new Scanner(System.in);
        System.out.print("Enter day: ");
        date.day = sc.nextInt();
        System.out.print("Enter month: ");
        date.month = sc.nextInt();
        System.out.print("Enter year: ");
        date.year  = sc.nextInt();
    }

    public String getDate() {
        return date.toString();
    }

    public void printCateringMenu() {
        cateringMenu.printCateringMenu();
    }

    public void printDutyFreeItems() {
        DutyFree.printDutyFreeItems();
    }
}