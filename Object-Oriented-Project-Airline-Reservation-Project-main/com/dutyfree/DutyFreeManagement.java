package com.dutyfree;

import java.util.Scanner;
import com.exceptions.*;

public class DutyFreeManagement {
    DutyFreeItem[] dutyFreeItems;
    private int itemCount;
    private final int maxItems=100;

    public DutyFreeManagement() {
        dutyFreeItems = new DutyFreeItem[maxItems];
        itemCount = 0;
    }

    public void manageDutyFree() throws InvalidChoiceException {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nDuty-Free Management:");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume nextline
            System.out.print("\n");

            switch (choice) {
                case 1:
                    addDutyFreeItem();
                    break;
                case 2:
                    printDutyFreeItems();
                    break;
                case 3:
                    updateDutyFreeItem();
                    break;
                case 4:
                    deleteDutyFreeItem();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    throw new InvalidChoiceException("Invalid Menu Choice.");
            }
        }
    }

    public void addDutyFreeItem() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        if (itemCount >= maxItems) {
            System.out.println("Cannot add more items. Duty-free menu is full.");
            return;
        }

        System.out.print("Enter Item Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Item Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Item Section (Beverages, Toys, Chocolates, Perfumes, Watches, Jewellery, Beauty Products): ");
        String section = sc.next();

        dutyFreeItems[itemCount++] = new DutyFreeItem(name, price, section);
        System.out.println(name + " added to the duty-free menu.");
    }

    public void printDutyFreeItems() {
        System.out.printf("%-15s %-15s %-15s\n", "Item Name", "Price", "Section");
        System.out.println("--------------------------------------------------");
        for (DutyFreeItem item : dutyFreeItems) {
            if (item != null) {
                System.out.printf("%-15s $%-14.2f %-15s\n", item.name, item.price, item.section);
            }
        }
    }

    public void updateDutyFreeItem() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the index of the item to update (0-" + (itemCount - 1) + "): ");
        int index = sc.nextInt();
        if (index < 0 || index >= itemCount) {
            System.out.println("Invalid index.");
            return;
        }

        System.out.print("New Item Name (leave blank to keep current): ");
        String newName = sc.next();
        if (!newName.isEmpty()) {
            dutyFreeItems[index].name = newName;
        }

        System.out.print("New Item Price (leave blank to keep current): ");
        String newPrice = sc.next();
        if (!newPrice.isEmpty()) {
            dutyFreeItems[index].price = Double.parseDouble(newPrice);
        }

        System.out.print("New Item Section (leave blank to keep current): ");
        String newSection = sc.next();
        if (!newSection.isEmpty()) {
            dutyFreeItems[index].section = newSection;
        }

        System.out.println("Duty-free item updated successfully!");
    }

    public void deleteDutyFreeItem() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the index of the item to delete (0-" + (itemCount - 1) + "): ");
        int index = sc.nextInt();
        if (index < 0 || index >= itemCount) {
            System.out.println("Invalid index.");
            return;
        }

        // Shift items left
        for (int i = index; i < itemCount - 1; i++) {
            dutyFreeItems[i] = dutyFreeItems[i + 1];
        }
        dutyFreeItems[--itemCount] = null; // Decrease count and nullify last item
        System.out.println("Duty-free item deleted successfully!");
    }
}