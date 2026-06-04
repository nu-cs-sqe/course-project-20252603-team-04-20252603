package controller;

import model.Player;
import model.Property;

public class PropertyController {

    public boolean promptPurchase(Player player, Property property) {
        if (player == null || property == null) {
            throw new IllegalArgumentException("Player and property cannot be null");
        }
        if (property.isOwned()) {
            return false;
        }
        if (!player.canAfford(property.getPrice())) {
            return false;
        }
        return true;
    }

    public boolean buyProperty(Player player, Property property) {
        if (player == null || property == null) {
            throw new IllegalArgumentException("Player and property cannot be null");
        }
        return property.purchase(player);
    }

    public void declineProperty(Player player, Property property) {
        if (player == null || property == null) {
            throw new IllegalArgumentException("Player and property cannot be null");
        }
    }

    public boolean handleRentPayment(Player renter, Property property) {
        if (renter == null || property == null) {
            throw new IllegalArgumentException("Renter and property cannot be null");
        }
        return property.chargeRent(renter);
    }
}
