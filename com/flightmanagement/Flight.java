package com.flightmanagement;
import com.exceptions.*;
import com.catering.*;
import java.util.Scanner;

@SuppressWarnings("resource")
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
    public double economySeatPrice;
    public int businessSeats;
    public double businessSeatPrice;
    public int firstSeats;
    public double firstSeatPrice;
    public int residenceSeats;
    public double residenceSeatPrice; // Add residence seat price

    CateringMenuItem[] cateringMenu = new CateringMenuItem[10]; // Assuming a max of 10 menu items

    public void addCateringMenu(String classType) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Dish Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Dish Type (Starter, Main, Dessert, Beverage): ");
        String type = sc.nextLine();
        System.out.print("Enter Allergens: ");
        String allergens = sc.nextLine();

        for (int i = 0; i < cateringMenu.length; i++) {
            if (cateringMenu[i] == null) {
                cateringMenu[i] = new CateringMenuItem(name, type, allergens, classType);
                System.out.println(name + " added to the catering menu for " + classType + ".");
                
                break;
            }
        }
        
    }

    private void printCateringMenu(String classType) {
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Dish Name", "Type", "Allergens", "Class");
        System.out.println("--------------------------------------------------");
        for (CateringMenuItem item : cateringMenu) {
            if (item.classType.equals(classType)) {
                System.out.println(item+"\n");
            }
        }
    }

    public void displayCateringMenu(FlightSchedule flightSchedule) throws InvalidChoiceException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Flight ID to view catering menu: ");
        String flightId = sc.nextLine();

        Flight flightToViewMenu = null;
        for (Flight flight : flightSchedule.flightList) {
            if (flight != null && flight.flightId.equals(flightId)) {
                flightToViewMenu = flight;
                break;
            }
        }

        if (flightToViewMenu != null) {
            System.out.println("Select Class Type:");
            System.out.println("1. Economy");
            System.out.println("2. Business");
            System.out.println("3. First");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            String classType = "";
            switch (choice) {
                case 1:
                    classType = "Economy";
                    break;
                case 2:
                    classType = "Business";
                    break;
                case 3:
                    classType = "First";
                    break;
                default:
                    
                    throw new InvalidChoiceException("Invalid class choice. No menu will be displayed.");
            }

            // Print the catering menu for the selected class
            System.out.println("\nCatering Menu for " + classType + ":");
            flightToViewMenu.printCateringMenu(classType);
        } else {
            System.out.println("Flight not found.");
        }
        
    }
}