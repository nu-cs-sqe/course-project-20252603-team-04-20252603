package model;

import java.util.Set;

import javax.swing.ImageIcon;
import model.Properties;

public class Player {

    private String name;
    private double balance;
    private Set<Properties> ownedProperties;
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
    public boolean buy(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        if (this.balance >= price) {
            this.balance -= price;
            return true;
        }
        return false;
    }
    public boolean sell(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        this.balance += price * 0.8;
        return true;
    }
    public boolean canAfford(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        return this.balance >= price;
    }

    public Set<Properties> getOwnedProperties() {
        return this.ownedProperties;
    }

    public boolean addProperty(Properties proptery){
        if (proptery == null){ return false; }

        return this.ownedProperties.add(proptery);
    }
    public boolean removeProperty(Properties property){
        if (property == null){ return false; }

        return this.ownedProperties.remove(property);
    }
    public boolean sellProperty(Properties property)
    {
        if (property == null){ return false; }
        if (!this.ownedProperties.contains(property)){ return false; }

        double price = property.getPrice();
        if (this.sell(price)){
            return this.removeProperty(property);
        }
        return false;
    }
    public int getPosition() {
        return this.position;
    }
    public boolean inJail() {
        return this.inJail;
    }
    public boolean goToJail(int position)
    {

        // this is bad practice we should not be using checking with magic numbers th emax bound should be the length of the baord class tiles 
        // just for testing purposes we will assume there are 32 tiles on the board and the jail tile is at position 10
        if (position < 0 || position > 31) {
            return false;
        }
        this.inJail = true;
        this.jailTurnCount = 1;
        this.position = 10;
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
    




}