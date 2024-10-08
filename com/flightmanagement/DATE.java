package com.flightmanagement;

public class DATE {
    public int day;
    public int month;
    public int year;

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;

    }

    public boolean equals(DATE d) {
        return (this.day == d.day && this.month == d.month && this.year == d.year);
    }
}