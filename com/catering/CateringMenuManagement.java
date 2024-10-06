package com.catering;

import java.util.Scanner;
import com.flightmanagement.*;

public class CateringMenuManagement {
    FlightSchedule flightSchedule;
    CateringMenuItem[] cateringMenu;
    private int menuCount;
    private final int maxMenuItems = 100; // Set the max size for catering items

    public CateringMenuManagement(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        cateringMenu = new CateringMenuItem[maxMenuItems];
        menuCount = 0;
    }

    public void manageCatering() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nCatering Menu Management:");
            System.out.println("1. Add Catering Item");
            System.out.println("2. View Catering Menu");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            System.out.print("\n");

            switch (choice) {
                case 1:
                    addCateringMenu(scanner);
                    break;
                case 2:
                    printCateringMenu();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addCateringMenu(Scanner sc) {
        if (menuCount >= maxMenuItems) {
            System.out.println("Cannot add more items. Catering menu is full.");
            return;
        }

        System.out.print("Enter Class Type (Economy, Business, First): ");
        String classType = sc.next(); // Asking for class type first
        sc.nextLine(); // Consume newline

        System.out.print("Enter Dish Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Dish Type (Starter, Main, Dessert, Beverage): ");
        String type = sc.nextLine();
        System.out.print("Enter Allergens: ");
        String allergens = sc.nextLine();

        cateringMenu[menuCount++] = new CateringMenuItem(name, type, allergens, classType);
        System.out.println(name + " added to the catering menu for " + classType + ".");
    }

    public void printCateringMenu() {
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Dish Name", "Type", "Allergens", "Class");
        System.out.println("--------------------------------------------------");
        for (CateringMenuItem item : cateringMenu) {
            if (item != null) {
                System.out.printf("%-15s %-15s %-15s %-15s\n", item.name, item.type, item.allergens, item.classType);
            }
        }
    }
}