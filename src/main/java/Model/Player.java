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
       return true;
    }



}
