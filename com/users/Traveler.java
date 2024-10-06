package com.users;

public class Traveler extends User {
    public Traveler(String loginID, String password) {
        super(loginID, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Welcome " + loginID + "!\n");
    }
}