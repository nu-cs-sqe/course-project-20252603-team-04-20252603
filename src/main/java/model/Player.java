package model;


import java.util.Collections;
import java.util.Set;

import javax.swing.ImageIcon;
import model.Property;

public class Player {

    private String name;
    private double balance;
    private Set<Property> ownedProperties;
    private boolean inJail;
    private int jailTurnCount;
    private int position;
    private boolean active;

    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.inJail = false;
        this.jailTurnCount = 0;
        this.position = 0;
        this.active = true;
        this.ownedProperties = new java.util.HashSet<>();
    }

    public double getBalance() {
        return this.balance;
    }
    public boolean remove(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        if (this.balance >= price) {
            this.balance -= price;
            return true;
        }
        return false;
    }
    public boolean receive(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        this.balance += price;
        return true;
    }
    public boolean canAfford(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        return this.balance >= price;
    }

    public Set<Property> getOwnedProperties() {
        return Collections.unmodifiableSet(this.ownedProperties);
    }

    public boolean addProperty(Property property){
        if (property == null){ return false; }

        return this.ownedProperties.add(property);
    }
    public boolean removeProperty(Property property){
        if (property == null){ return false; }

        return this.ownedProperties.remove(property);
    }
    public boolean sellProperty(Property property)
    {
        if (property == null){ return false; }
        if (!this.ownedProperties.contains(property)){ return false; }

        double price = property.getPrice();
        if (this.receive(price)){
            return this.removeProperty(property);
        }
        return false;
    }
    public String getName() {
        return this.name;
    }
    public int getJailTurnCount() {
        return this.jailTurnCount;
    }
    public int getPosition() {
        return this.position;
    }
    public boolean inJail() {
        return this.inJail;
    }
    public boolean goToJail(int position)
    {
        if (position < 0 || position > 31) {
            return false;
        }
        this.inJail = true;
        this.jailTurnCount = 1;
        this.position = position;
        return true;
    }
    public boolean leaveJail() {
        if(!this.inJail) {
            return false;
        }
        this.inJail = false;
        this.jailTurnCount = 0;
        this.position += 1;
        return true;

    } 
    public boolean isBankrupt() {
        if (this.balance <= 0.0) {
            this.active = false;
            return true;
        }        
        return false;
    }
    public boolean getActive() {
        return this.active;
    }
    




}
