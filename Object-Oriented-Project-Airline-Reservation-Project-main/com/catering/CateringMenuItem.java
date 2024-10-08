package com.catering;

public class CateringMenuItem {
    public String name;
    public String type;
    public String allergens;
    public String classType;

    public CateringMenuItem(String name, String type, String allergens, String classType) {
        this.name = name;
        this.type = type;
        this.allergens = allergens;
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "Dish name: "+name+"\n Type: "+type+"\nAlergens: "+allergens;
    }
}