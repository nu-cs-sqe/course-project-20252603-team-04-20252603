package Model;

import java.util.Set;

import javax.swing.ImageIcon;
import Model.Properties;

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
        this.balance += price;
        return true;
    }
    public boolean canAfford(double price) {
        if (price < 0 || price == Double.MAX_VALUE) {
            return false;
        }
        return this.balance >= price;
    }



}
