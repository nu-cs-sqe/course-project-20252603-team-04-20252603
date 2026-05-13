package model;

import java.util.Optional;
import model.Tile;
import util.OwnershipStatus;
import util.Constants;

public class Property implements Tile {

    private String name;
    private double price;
    private double rent;
    private OwnershipStatus ownershipStatus;
    private Optional<Player> owner;


    public Property(String name, double price, double rent) {

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
        this.owner = Optional.empty();
        this.ownershipStatus = OwnershipStatus.UNOWNED;
    }

    @Override
    public TileType getName() {
        return TileType.PROPERTY;
    }

    @Override
    public void landOn(Player player, GameEngine game) {

    }

    public double getPrice() {
        return this.price;
    }

    public double getRent() {
        return this.rent;
    }

    public boolean isOwned() {
        return this.ownershipStatus.equals(OwnershipStatus.OWNED);
    }

    public boolean isOwnedBy(Player player) {
        if (player == null) {
            return false;
        }
        return this.owner.isPresent() && this.owner.get().equals(player);
    }

    public void resetOwner() {
        if (this.owner.isPresent()) {
            Player currentOwner = this.owner.get();
            currentOwner.removeProperty(this);
        }
        this.owner = Optional.empty();
        this.ownershipStatus = OwnershipStatus.UNOWNED;
    }

    public boolean purchase(Player player) {
        if (player == null) {
            return false;
        }
        if (this.isOwned()) {
            return false;
        }
        if (!player.canAfford(this.price)) {
            return false;
        }
        if (player.buy(this.price)) {
            this.owner = Optional.of(player);
            this.ownershipStatus = OwnershipStatus.OWNED;
            player.addProperty(this);
            return true;
        }
        return false;
    }
    public boolean chargeRent(Player player) {
        if (player == null || !this.isOwned() || !this.owner.isPresent()) {
            return false;
        }
        Player ownerPlayer = this.owner.get();
        if (player.equals(ownerPlayer)) {
            return false; // Owner does not pay rent to themselves
        }
        if (!player.canAfford(this.rent)) {
            return false; // Player cannot afford rent
        }
        if (player.buy(this.rent)) {
            ownerPlayer.sell(this.rent / Constants.SELL_MULTIPLIER); // Owner receives rent
            return true;
        }
        return false;
    }

    Optional<Player> getOwner() {
        return this.owner;
    }

}
