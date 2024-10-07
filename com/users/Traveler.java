package com.users;
import java.util.Scanner;

import com.flightmanagement.FlightSchedule;

public class Traveler extends User implements Runnable {
    Thread t;
    boolean travelerMenu = true;
    Scanner scanner = new Scanner(System.in);

    String email;
    String contactNo;

    FlightSchedule flightSchedule;

    public Traveler(String loginID, String password, FlightSchedule flightSchedule) {
        super(loginID, password);
        this.flightSchedule = flightSchedule;
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

    @Override
    public void run() {
        while (travelerMenu) {
            System.out.println("\n1. Input Personal Details");
            System.out.println("2. View Flight Schedule");
            System.out.println("3. Book a Flight");
            System.out.println("4. View Catering Menu");
            System.out.println("5. View and Purchase Duty-Free Items");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int travelerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (travelerChoice) {
                case 1:
                    inputDetails();
                    break;
                case 2:
                    flightSchedule.viewFlights();
                    break;
                case 3:
                    bookFlight(flightSchedule, scanner);
                    break;
                case 4:
                    viewCateringMenu(traveler, cateringMenuManagement, scanner);
                    break;
                case 5:
                    viewAndPurchaseDutyFree(traveler, dutyFreeManagement, scanner);
                    break;
                case 6:
                    travelerMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}