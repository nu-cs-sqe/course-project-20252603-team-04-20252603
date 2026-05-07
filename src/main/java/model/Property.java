package model;

public class Property {

    private String name;
    private double price;
    private double rent;
    private Player owner;

    public Property(String name, double price, double rent, Player owner) {

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (rent < 0) {
            throw new IllegalArgumentException("Rent cannot be negative");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }

        this.name = name;
        this.price = price;
        this.rent = rent;
        this.owner = owner;
    }
    
    public double getPrice() {
        return this.price;
    }
    public double getRent() {
        return this.rent;
    }
    public Player getOwner() {
        return this.owner;
    }

}
