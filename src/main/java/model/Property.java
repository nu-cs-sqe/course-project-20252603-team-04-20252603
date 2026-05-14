package model;


import java.util.Optional;
import model.Tile;
import util.OwnershipStatus;
import util.Constants;

public class Property implements Tile {

    private String propertyName;
    private double price;
    private double rent;
    private OwnershipStatus ownershipStatus;
    private Optional<Player> owner;

    public Property(String propertyName, double price, double rent) {

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (rent < 0) {
            throw new IllegalArgumentException("Rent cannot be negative");
        }
        if (propertyName == null || propertyName.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }

        this.propertyName = propertyName;
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
        if (player.remove(this.price)) {
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
            return false;
        }
        if (!player.canAfford(this.rent)) {
            return false;
        }
        if (player.remove(this.rent)) {
            ownerPlayer.receive(this.getResaleValue());
            return true;
        }
        return false;
    }
     public double getResaleValue() {
        return this.price * Constants.SELL_MULTIPLIER;
    }







    
}